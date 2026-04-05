package dam.a51692.coolweatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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

        //images/icons
        val weatherIcon = findViewById<ImageView>(R.id.weatherIcon)
        val main = findViewById<ConstraintLayout>(R.id.main);

        //inputs
        val latInput = findViewById<EditText>(R.id.latitudeInput)
        val lonInput = findViewById<EditText>(R.id.longitudeInput)

        val updateBtn = findViewById<Button>(R.id.updateBtn)

        //info
        val tempText = findViewById<TextView>(R.id.temperature)
        val windSpeedVal = findViewById<TextView>(R.id.windSpeedValue)
        val windDirVal = findViewById<TextView>(R.id.windDirValue)
        val humidityVal = findViewById<TextView>(R.id.humidityValue)
        val seaLvlPressureVal = findViewById<TextView>(R.id.seaLvlValue)
    }
}