package app

import org.example.MyClass
import org.example.MyClassWrapper

fun main () {
    val myClass = MyClass ()
    val wrappedMyClass = MyClassWrapper(myClass) // Use the wrapper class
    wrappedMyClass.sayHello ()
    wrappedMyClass.compute ()
}
