package dam.a51692.system_info

import android.os.Build
import android.os.Bundle
import android.widget.TextClock
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sysInfoView = findViewById<TextView>(R.id.sysInfo)

        val info =
            """
                Manufacturer: ${Build.MANUFACTURER}
                Model: ${Build.MODEL}
                Brand: ${Build.BRAND}
                Type: ${Build.TYPE}
                User: ${Build.USER}
                Base: ${Build.VERSION_CODES.BASE}
                SDK: ${Build.VERSION.SDK_INT}
                Version Code: ${Build.VERSION_CODES.R}
                Display: ${Build.DISPLAY}
            """.trimIndent()

        sysInfoView.text = info
    }
}