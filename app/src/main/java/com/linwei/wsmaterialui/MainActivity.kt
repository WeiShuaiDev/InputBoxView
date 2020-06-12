package com.linwei.wsmaterialui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.linwei.inputboxview.enum.InputDataType
import com.linwei.inputboxview.listener.OnInputDataStateListener
import com.linwei.inputboxview.widget.InputBoxView
import com.linwei.inputboxview.widget.TelephoneNumberView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTNumberView.setTelephoneNumber("01234567890")
        mTNumberView.setOnInputDataStateListener(object : OnInputDataStateListener {
            override fun onInputState(isProper: Boolean) {
                System.out.println("isProper" + isProper)
            }
        })

    }

    private fun testTelephoneNumberView() {
        val telephoneNumberView = TelephoneNumberView.Builder(this).build()

        mLlRootView.addView(
            telephoneNumberView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }

    private fun testInputBoxView() {
        val inputBoxView: InputBoxView = InputBoxView.Builder(this)
            .setInputBoxType(InputDataType.NUMBER)
            .setInputBoxCursorVisible(true)
            .setInputBoxBackground(R.drawable.select_input_box_line_bg)
            .setInputBoxTextColorId(R.color.colorInputBoxTextColor)
            .setInputBoxSpacing(30)
            .setInputBoxNumber(4)
            .setInputBoxWidth(120)
            .setInputBoxTextSize(6f)
            .build()

        mLlRootView.addView(
            inputBoxView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }
}
