package de.rki.coronawarnapp.ui.submission

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import de.rki.coronawarnapp.R
import kotlinx.android.synthetic.main.view_tan_input.view.tan_input_edittext
import kotlinx.android.synthetic.main.view_tan_input.view.tan_input_textview_1
import kotlinx.android.synthetic.main.view_tan_input.view.tan_input_textview_2
import kotlinx.android.synthetic.main.view_tan_input.view.tan_input_textview_3
import kotlinx.android.synthetic.main.view_tan_input.view.tan_input_textview_4
import kotlinx.android.synthetic.main.view_tan_input.view.tan_input_textview_5
import kotlinx.android.synthetic.main.view_tan_input.view.tan_input_textview_6
import kotlinx.android.synthetic.main.view_tan_input.view.tan_input_textview_7

class TanInput(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    companion object {
        private const val KEYBOARD_TRIGGER_DELAY = 100L
    }

    var listener: ((String?) -> Unit)? = null

    private var tan: String? = null

    init {
        inflate(context, R.layout.view_tan_input, this)

        // register listener
        tan_input_edittext.doOnTextChanged { text, _, _, _ -> updateTan(text) }
        setOnClickListener { showKeyboard() }

        // initially show the keyboard
        Handler().postDelayed({ showKeyboard() }, KEYBOARD_TRIGGER_DELAY)
    }

    private fun showKeyboard() {
        if (tan_input_edittext.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(tan_input_edittext, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun updateTan(text: CharSequence?) {
        this.tan = text?.toString()
        updateDigits()
        notifyListener()
    }

    private fun notifyListener() = listener?.invoke(tan)

    private fun updateDigits() = listOf(
        tan_input_textview_1,
        tan_input_textview_2,
        tan_input_textview_3,
        tan_input_textview_4,
        tan_input_textview_5,
        tan_input_textview_6,
        tan_input_textview_7
    ).forEachIndexed { i, tanDigit ->
        tanDigit.text = digitAtIndex(i)
    }

    private fun digitAtIndex(index: Int): String = tan?.getOrNull(index)?.toString() ?: ""
}
