package com.linwei.wsmaterialui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListener()
    }

    companion object {
        const val TELEPHONE_NUMBER: String = "TELEPHONE_NUMBER"
    }

    /**
     * 初始化事
     */
    private fun initListener() {
        mTvRetrieveNumber.setOnClickListener {

            if (checkTelephoneNumber()) {
                val intent = Intent(this, VerifyTelephoneNumberActivity::class.java)
                intent.putExtra(TELEPHONE_NUMBER, mEtTelNumber.text.toString())
                startActivity(intent)
            }
        }

        mBtComplete.setOnClickListener {
            if (checkTelephoneNumber()) {
                val intent = Intent(this, VerifyCodeActivity::class.java)
                intent.putExtra(TELEPHONE_NUMBER, mEtTelNumber.text.toString())
                startActivity(intent)
            }
        }
    }

    /**
     * 校验手机有效性
     * @return [Boolean] true:有效  false：无效
     */
    private fun checkTelephoneNumber(): Boolean {
        if (mEtTelNumber.text != null && mEtTelNumber.text!!.isNotEmpty() && mEtTelNumber.text!!.length == 11) {
            return true
        }
        Toast.makeText(this, "输入手机号码无效！", Toast.LENGTH_SHORT).show()
        return false
    }
}
