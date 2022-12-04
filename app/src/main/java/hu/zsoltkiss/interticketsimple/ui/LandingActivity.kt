package hu.zsoltkiss.interticketsimple.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import hu.zsoltkiss.interticketsimple.R
import hu.zsoltkiss.interticketsimple.ui.countries.CountryListActivity
import hu.zsoltkiss.interticketsimple.ui.task1.Task1Activity
import hu.zsoltkiss.interticketsimple.ui.task2.Task2Activity
import hu.zsoltkiss.interticketsimple.ui.task3.Task3Activity

class LandingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        listOf(
            R.id.btnTask1, R.id.btnTask2, R.id.btnTask3, R.id.btnTask4
        ).forEach {
            findViewById<Button>(it).setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        v?.id?.let { buttonId ->
            when(buttonId) {
                R.id.btnTask1 -> Task1Activity::class.java
                R.id.btnTask2 -> Task2Activity::class.java
                R.id.btnTask3 -> Task3Activity::class.java
                R.id.btnTask4 -> CountryListActivity::class.java
                else -> null
            }?.let { clazz ->
                startActivity(Intent(this, clazz))
            }
        }
    }
}