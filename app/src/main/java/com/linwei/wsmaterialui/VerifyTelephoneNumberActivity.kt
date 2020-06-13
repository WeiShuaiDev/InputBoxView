package com.linwei.wsmaterialui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.linwei.inputboxview.listener.OnInputDataStateListener
import com.linwei.inputboxview.widget.TelephoneNumberView
import kotlinx.android.synthetic.main.activity_verify_telephone_number.*

class VerifyTelephoneNumberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_telephone_number)


        mTNumberView.setTelephoneNumber("01234567890")
        mTNumberView.setOnInputDataStateListener(object : OnInputDataStateListener {
            override fun onInputState(isProper: Boolean) {
            }
        })

        mTNumberView2.setTelephoneNumber("01234567890")
        mTNumberView2.setOnInputDataStateListener(object : OnInputDataStateListener {
            override fun onInputState(isProper: Boolean) {
            }
        })
    }


    private fun testTelephoneNumberView() {
        val telephoneNumberView = TelephoneNumberView.Builder(this)
            .setInputBoxNumber(50)
            .setInputBoxTextColorId(R.color.colorInputBoxTextColor)
            .setTelephoneNumberTextColorId(R.color.colorInputBoxTextColor)
            .setInputBoxTextSize(6f)
            .setTelephoneNumberTextSize(6f)
            .setInputBoxBackground(R.drawable.select_input_box_line_bg)
            .setInputBoxCursorType(R.drawable.shape_input_box_cursor)
            .setInputBoxCursorVisible(true)
            .setInputBoxSpacing(5)
            .setInputBoxStartIndex(3)
            .setInputBoxEndIndex(7)
            .setOnInputDataStateListener(object : OnInputDataStateListener{
                override fun onInputState(isProper: Boolean) {

                }
            }).build()

        mLlRootView.addView(
            telephoneNumberView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }
}
