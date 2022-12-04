package hu.zsoltkiss.interticketsimple.ui.task3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.jakewharton.rxbinding4.view.clicks
import hu.zsoltkiss.interticketsimple.R
import hu.zsoltkiss.interticketsimple.appendLine
import hu.zsoltkiss.interticketsimple.debugPrint
import hu.zsoltkiss.interticketsimple.printOnEach
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import java.util.concurrent.TimeUnit

class Task3Activity : AppCompatActivity() {
    private lateinit var output1: TextView
    private lateinit var output2: TextView
    private lateinit var button: Button

    private val disposables = CompositeDisposable()

    private val emissions: Subject<String> = BehaviorSubject.create()

    private val items = mutableListOf(
        "1st",
        "2nd",
        "3rd",
        "4th",
        "5th"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task2)

        output1 = findViewById(R.id.output1)
        output2 = findViewById(R.id.output2)
        button = findViewById(R.id.btnStart)

        button.clicks()
            .take(1)
            .doOnComplete {
                button.isEnabled = false
            }
            .subscribe {
                start()
            }
            .addTo(disposables)

        earlySubscribe()
    }


    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun start() {
        Observable.interval(2, TimeUnit.SECONDS)
            .take(items.size.toLong())
            .printOnEach(prefix = "INTERVAL", step = 7777) { "$it" }
            .doOnNext {
                debugPrint(step = 7777, message = "items count: ${items.size}")
                if (items.isNotEmpty()) {
                    emissions.onNext(items.removeFirst() )
                }
            }
            .doOnComplete {
                lateSubscribe()
            }
            .subscribe()
            .addTo(disposables)
    }

    private fun earlySubscribe() {
        emissions
            .doOnSubscribe {
                debugPrint(prefix = "onSubscribe", step = 7777, message = "EARLY")
            }
            .filter { !it.contains("4") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                output1.appendLine(it)
            }
            .addTo(disposables)
    }

    private fun lateSubscribe() {
        emissions
            .doOnSubscribe {
                debugPrint(prefix = "onSubscribe", step = 7777, message = "LATE")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                output2.appendLine(it)
            }
            .addTo(disposables)
    }
}