package hu.zsoltkiss.interticketsimple.ui.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding4.view.clicks
import hu.zsoltkiss.interticketsimple.R
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.lang.NumberFormatException
import java.util.concurrent.TimeUnit

class Task1Activity : AppCompatActivity() {

    private lateinit var userInput: EditText
    private lateinit var output: TextView
    private lateinit var button: Button

    private val rxToken = "Rx "
    private val errorToken = "Not a number"
    private val negativeNumberToken = "Negative number"

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task1)

        userInput = findViewById(R.id.editTextNumberSigned)
        output = findViewById(R.id.tvOutput)
        button = findViewById(R.id.btnSend)

        button.clicks().throttleLast(1, TimeUnit.SECONDS).map {
            userInput.text.split(",").map { input ->
                try {
                    val number = input.trim().toInt()
                    when {
                        number < 0 -> negativeNumberToken
                        number > 0 -> rxToken.repeat(number)
                        else -> ""
                    }
                } catch (nfe: NumberFormatException) {
                    errorToken
                }
            }
        }.subscribe(
            { newContent ->
                output.text = newContent.joinToString("\n")
            },
            {
                it.printStackTrace()
            }
        ).addTo(disposables)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}