package com.linwei.wsmaterialui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.linwei.inputboxview.enum.InputDataType
import com.linwei.inputboxview.widget.InputBoxView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_verify_code.*

class VerifyCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_code)
    }



    private fun testInputBoxView() {
        val inputBoxView: InputBoxView = InputBoxView.Builder(this)
            .setInputBoxType(InputDataType.NUMBER)
            .setInputBoxCursorVisible(true)
            .setInputBoxBackground(R.drawable.select_input_box_line_bg)
            .setInputBoxTextColorId(R.color.colorInputBoxTextColor)
            .setInputBoxCursorType(R.drawable.shape_input_box_cursor)
            .setInputBoxSpacing(5)
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
