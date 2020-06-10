package com.linwei.wsmaterialui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.linwei.inputboxview.enum.InputDataType
import com.linwei.inputboxview.widget.InputBoxView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputBoxView: InputBoxView = InputBoxView.Builder(this)
            .setInputBoxCursorType(InputDataType.NUMBER)
            .setInputBoxCursorVisible(true)
            .setInputBoxNumber(4)
            .setInputBoxWidth(100)
            .setInputBoxTextSize(10f)
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
