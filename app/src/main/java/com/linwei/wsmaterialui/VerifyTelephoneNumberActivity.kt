package com.linwei.wsmaterialui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.linwei.inputboxview.listener.OnInputDataStateListener
import com.linwei.inputboxview.widget.TelephoneNumberView
import kotlinx.android.synthetic.main.activity_verify_code.*
import kotlinx.android.synthetic.main.activity_verify_telephone_number.*
import kotlinx.android.synthetic.main.activity_verify_telephone_number.mLlRootView

class VerifyTelephoneNumberActivity : AppCompatActivity() {

    private var mTelephoneNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_telephone_number)
        initData()
        initListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.verify_telephone_number_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.line -> {
                mTNumberView1.visibility = View.VISIBLE
                mTNumberView2.visibility = View.GONE
            }
            R.id.rim -> {
                mTNumberView1.visibility = View.GONE
                mTNumberView2.visibility = View.VISIBLE
            }

        }

        return true
    }

    /**
     * 获取数据
     */
    private fun initData() {
        mTelephoneNumber = intent.getStringExtra(MainActivity.TELEPHONE_NUMBER)
    }

    /**
     * 初始化事件
     */
    private fun initListener() {
        mTNumberView1.setTelephoneNumber(mTelephoneNumber ?: "01234567890")
        mTNumberView1.setOnInputDataStateListener(object : OnInputDataStateListener {
            override fun onInputState(isProper: Boolean) {
                Toast.makeText(
                    this@VerifyTelephoneNumberActivity,
                    "数据合法性:isProper= $isProper",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        mTNumberView2.setTelephoneNumber(mTelephoneNumber ?: "01234567890")
        mTNumberView2.setOnInputDataStateListener(object : OnInputDataStateListener {
            override fun onInputState(isProper: Boolean) {
                Toast.makeText(
                    this@VerifyTelephoneNumberActivity,
                    "数据合法性:isProper= $isProper",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

    /**
     * 通过代码方式，创建TelephoneNumberView控件
     */
    private fun initTelephoneNumberView() {
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
            .setOnInputDataStateListener(object : OnInputDataStateListener {
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
