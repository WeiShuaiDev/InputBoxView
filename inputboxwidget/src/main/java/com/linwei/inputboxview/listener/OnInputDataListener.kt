package com.linwei.inputboxview.listener

import android.text.Editable
import android.view.View
import com.linwei.inputboxview.widget.InputBoxView

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/8
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:[InputBoxView] 事件回调
 *-----------------------------------------------------------------------
 */
interface OnInputDataListener {
    /**
     * 输入文本中
     * @param view [View] AppCompatEditText
     * @param content [String] 数据
     */
    fun onTextChange(view: Editable?, content: String?)

    /**
     *  输入完成
     * @param view [View] AppCompatEditText
     * @param content [String] 数据
     */
    fun onComplete(view: Editable?, content: String?)
}

interface OnInputDataStateListener {

    /**
     * 输入数据状态
     * @param isProper [Boolean] 数据是否正确
     */
    fun onInputState(isProper: Boolean)

}