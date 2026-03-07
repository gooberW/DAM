package dam.a51692.helloworld

import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

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

        println(getString(R.string.activity_oncreate_msg,
            this@MainActivity.localClassName))

        // A CLASSE 'R' são os resources!!

        val nameInput = findViewById<TextInputEditText>(R.id.nameInput)
        val helloView = findViewById<TextView>(R.id.helloView)
        val resetTextBtn = findViewById<Button>(R.id.resetBtn)

        nameInput.setOnEditorActionListener { input, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) { // se for a acaoo done do teclado
                val userInput = input.text.toString()

                if (userInput.isNotBlank()) {
                    helloView.text = "Hello ${userInput}"
                    nameInput.clearFocus()
                    nameInput.text?.clear()
                }

                // tem de retornar true para dizer que o evento foi handled
                true
            } else {
                false
            }
        }

        resetTextBtn.setOnClickListener {
            helloView.text = getString(R.string.hello_world)
            nameInput.text?.clear()
        }
    }


}