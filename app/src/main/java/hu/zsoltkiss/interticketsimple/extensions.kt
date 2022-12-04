package hu.zsoltkiss.interticketsimple

import android.view.View
import android.widget.TextView

fun TextView.appendLine(line: String) {
    text = StringBuilder(text).append("\n").append(line).toString()
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun TextView.showWithText(s: String) {
    text = s
    show()
}