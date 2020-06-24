package com.linwei.inputboxview.enum

import com.linwei.inputboxview.widget.VerifyCodeView

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/8
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:[VerifyCodeView] 输入数据类型,[NUMBER]:明文数字;[NUMBER_PASSWORD]:密文数字;[TEXT]:明文文字;[TEXT_PASSWORD]:密文文字
 *-----------------------------------------------------------------------
 */
enum class InputDataType(val type: Int) {
    NUMBER(0), NUMBER_PASSWORD(1), TEXT(2), TEXT_PASSWORD(3)
}