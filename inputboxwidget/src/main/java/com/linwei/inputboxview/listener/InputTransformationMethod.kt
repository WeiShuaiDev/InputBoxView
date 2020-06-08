package com.linwei.inputboxview.listener

import android.text.method.PasswordTransformationMethod
import android.view.View
import com.linwei.inputboxview.widget.InputBoxView

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/8
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:[InputBoxView] 数据转换
 *-----------------------------------------------------------------------
 */
class InputTransformationMethod : PasswordTransformationMethod() {

    override fun getTransformation(source: CharSequence?, view: View?): CharSequence {
        return object : CharSequence {
            override val length: Int
                get() = source?.length ?: 0

            override fun get(index: Int): Char {
                return '•'

            }

            override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
                return source!!.subSequence(startIndex, endIndex)
            }

        }
    }
}