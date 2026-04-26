package processor

import annotations.Extract
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SupportedAnnotationTypes("annotations.Extract")
class ExtractProcessor : AbstractProcessor() {
    override fun process(
        annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment
    ): Boolean {
        val classMethodMap = mutableMapOf<TypeElement, MutableList<ExecutableElement>>()

        for (element in roundEnv.getElementsAnnotatedWith(Extract::class.java)) {
            if (element is ExecutableElement) {
                val enclosingClass = element.enclosingElement as TypeElement
                classMethodMap.computeIfAbsent(enclosingClass) { mutableListOf() }.add(element)
            }
        }

        for ((classElement, methods) in classMethodMap) {
            generateKotlinWrapperClass(classElement, methods)
        }
        return true
    }

    private fun generateKotlinWrapperClass(
        classElement: TypeElement,
        methods: List<ExecutableElement>
    ) {
        val packageName = processingEnv.elementUtils.getPackageOf(classElement).toString()
        val originalClassName = classElement.simpleName.toString()
        val generatedClassName = "${originalClassName}Extractor"

        val originalClass = ClassName(packageName, originalClassName)

        val constructor = FunSpec.constructorBuilder()
            .addParameter("input", STRING)
            .build()

        val classBuilder = TypeSpec.classBuilder(generatedClassName)
            .superclass(originalClass)
            .primaryConstructor(constructor)
            .addSuperclassConstructorParameter("input")

        for (method in methods) {
            val methodName = method.simpleName.toString()

            // nullable string -> String?
            val returnType = STRING.copy(nullable = true)

            val regex = method.getAnnotation(Extract::class.java)?.regex ?: ""

            val methodBuilder = FunSpec.builder(methodName)
                .addModifiers(KModifier.OVERRIDE)
                .returns(returnType)
                .addStatement("val match = Regex(%S).find(input)", regex)
                .addStatement("return match?.groupValues?.get(1)")

            classBuilder.addFunction(methodBuilder.build())
        }

        val file = FileSpec.builder(packageName, generatedClassName)
            .addType(classBuilder.build())
            .build()

        val kaptDir = processingEnv.options["kapt.kotlin.generated"]
        if (kaptDir != null) {
            file.writeTo(File(kaptDir))
        } else {
            processingEnv.messager.printMessage(
                Diagnostic.Kind.ERROR,
                "kapt.kotlin.generated not found"
            )
        }
    }
}