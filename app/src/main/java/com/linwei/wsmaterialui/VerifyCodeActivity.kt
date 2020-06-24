package com.linwei.wsmaterialui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.linwei.inputboxview.enum.InputDataType
import com.linwei.inputboxview.listener.OnInputDataListener
import com.linwei.inputboxview.widget.VerifyCodeView
import kotlinx.android.synthetic.main.activity_verify_code.*

class VerifyCodeActivity : AppCompatActivity() {

    private var mTelephoneNumber: String? = null


    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_code)

        initData()
        initListener()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.verify_code_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.line -> {
                mInputBoxView1.visibility = View.VISIBLE
                mInputBoxView2.visibility = View.GONE
                mInputBoxView3.visibility = View.GONE
            }
            R.id.rim -> {
                mInputBoxView1.visibility = View.GONE
                mInputBoxView2.visibility = View.VISIBLE
                mInputBoxView3.visibility = View.GONE
            }
            R.id.table -> {
                mInputBoxView1.visibility = View.GONE
                mInputBoxView2.visibility = View.GONE
                mInputBoxView3.visibility = View.VISIBLE
            }
        }

        return true
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        mTelephoneNumber = intent.getStringExtra(MainActivity.TELEPHONE_NUMBER)
        mTvTitle.text = getString(R.string.activity_verify_code_title, mTelephoneNumber)
    }

    /**
     * 初始化事件
     */
    private fun initListener() {
        mInputBoxView1.setOnInputDataListener(object : OnInputDataListener {
            override fun onTextChange(view: Editable?, content: String?) {

            }

            override fun onComplete(view: Editable?, content: String?) {
                Toast.makeText(
                    this@VerifyCodeActivity,
                    "验证码数据: $content",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        mInputBoxView2.setOnInputDataListener(object : OnInputDataListener {
            override fun onTextChange(view: Editable?, content: String?) {

            }

            override fun onComplete(view: Editable?, content: String?) {
                Toast.makeText(
                    this@VerifyCodeActivity,
                    "验证码数据: $content",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        mInputBoxView3.setOnInputDataListener(object : OnInputDataListener {
            override fun onTextChange(view: Editable?, content: String?) {

            }

            override fun onComplete(view: Editable?, content: String?) {
                Toast.makeText(
                    this@VerifyCodeActivity,
                    "验证码数据: $content",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    /**
     * 通过代码方式，创建InputBoxView控件
     */
    private fun initInputBoxView() {
        val verifyCodeView: VerifyCodeView = VerifyCodeView.Builder(this)
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
            verifyCodeView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }
}
