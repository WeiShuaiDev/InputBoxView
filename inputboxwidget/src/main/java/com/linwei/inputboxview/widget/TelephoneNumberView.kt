package com.linwei.inputboxview.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.linwei.inputboxview.R
import com.linwei.inputboxview.enum.InputDataType
import com.linwei.inputboxview.ext.color
import com.linwei.inputboxview.utils.UIUtils

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/4
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:[TelephoneNumberView] 电话号输入框
 *-----------------------------------------------------------------------
 */
class TelephoneNumberView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    //手机号的宽度
    private var mTelephoneNumberWidth: Int = 0

    //输入框文字颜色
    private var mInputBoxTextColor: Int = 0

    //手机号文字颜色
    private var mTelephoneNumberTextColor: Int = 0

    //输入框文字大小
    private var mInputBoxTextSize: Float = 0f

    //手机号文字大小
    private var mTelephoneNumberTextSize: Float = 0f

    //输入框背景
    private var mInputBoxBackground: Int = 0

    //光标样式
    private var mInputBoxCursorType: Int = 0

    //是否隐藏光标
    private var mInputBoxCursorVisible: Boolean = false

    //输入框间距
    private var mInputBoxSpacing: Int = 0

    //输入框起始位置
    private var mInputBoxStartIndex: Int = 0

    //输入框结束位置
    private var mInputBoxEndIndex: Int = 0

    private var mMeasuredWidth: Int = 0
    private var mTelephoneNumber: String? = ""
    private var mTelephoneNumberSize: Int = 0
    private var mInputBoxSize: Int = 0

    init {
        val typeArray =
            context.obtainStyledAttributes(attrs, R.styleable.TelephoneNumberView)

        mTelephoneNumberWidth = typeArray.getDimensionPixelSize(
            R.styleable.TelephoneNumberView_telephone_number_width,
            UIUtils.dp2px(context, 50f)
        )

        mInputBoxTextColor = typeArray.getColor(
            R.styleable.TelephoneNumberView_telephone_number_input_box_text_color,
            context.color(R.color.colorInputBoxTextColor)
        )

        mTelephoneNumberTextColor = typeArray.getColor(
            R.styleable.TelephoneNumberView_telephone_number_text_color,
            context.color(R.color.colorInputBoxTextColor)
        )

        mInputBoxTextSize = typeArray.getDimensionPixelSize(
            R.styleable.TelephoneNumberView_telephone_number_input_box_text_size,
            UIUtils.sp2px(context, 18f)
        ).toFloat()

        mTelephoneNumberTextSize = typeArray.getDimensionPixelSize(
            R.styleable.TelephoneNumberView_telephone_number_text_size,
            UIUtils.sp2px(context, 18f)
        ).toFloat()

        mInputBoxBackground = typeArray.getResourceId(
            R.styleable.TelephoneNumberView_telephone_number_input_box_backgroud,
            R.drawable.select_input_box_rim_bg
        )
        mInputBoxCursorType = typeArray.getResourceId(
            R.styleable.TelephoneNumberView_telephone_number_input_box_cursor_type,
            R.drawable.shape_input_box_cursor
        )

        mInputBoxCursorVisible =
            typeArray.getBoolean(
                R.styleable.TelephoneNumberView_telephone_number_input_box_cursor_visible,
                false
            )

        mInputBoxSpacing = typeArray.getDimensionPixelSize(
            R.styleable.TelephoneNumberView_telephone_number_spacing,
            UIUtils.dp2px(context, 10f)
        )

        mInputBoxStartIndex =
            typeArray.getDimension(
                R.styleable.TelephoneNumberView_telephone_number_input_box_start_index,
                4f
            ).toInt()

        mInputBoxEndIndex =
            typeArray.getDimension(
                R.styleable.TelephoneNumberView_telephone_number_input_box_end_index,
                7f
            ).toInt()

        typeArray.recycle()

        addChildViewToContainer()

    }

    /**
     *
     */
    private fun addChildViewToContainer() {
        this.mInputBoxSize = fetchInputBoxNumber()

        for (index: Int in 1..mTelephoneNumberSize) {
            if (index in mInputBoxStartIndex..mInputBoxEndIndex) {
                initChildInputBoxView()
            } else {
                initChildTextView(index)
            }
        }

    }

    /**
     * 根据 `index` 位置坐标，创建 [TextView] 控件
     * @param index [Int]
     * @return textView [TextView]
     */
    private fun initChildTextView(index: Int): TextView {
        val textView = TextView(context)
        return textView
    }

    /**
     * 创建 [InputBoxView] 输入框控件
     * @return intpuBoxView [InputBoxView]
     */
    private fun initChildInputBoxView(): InputBoxView {
        val inputBoxView: InputBoxView = InputBoxView.Builder(context)
            .setInputBoxNumber(mInputBoxSize)
            .setInputBoxBackground(mInputBoxBackground)
            .setInputBoxSpacing(mInputBoxSpacing)
            .setInputBoxTextSize(mInputBoxTextSize)
            .setInputBoxCursorVisible(mInputBoxCursorVisible)
            .setInputBoxWidth(mTelephoneNumberWidth)
            .setInputBoxType(InputDataType.NUMBER)
            .setInputBoxTextColor(mInputBoxTextColor)
            .setInputBoxCursorType(mInputBoxCursorType)
            .build()
        return inputBoxView
    }

    /**
     * 根据输入框起始位置 [mInputBoxStartIndex]和输入框结束位置 [mInputBoxEndIndex]，
     * 来确定输入框长度
     */
    private fun fetchInputBoxNumber(): Int {
        if (mInputBoxEndIndex <= mInputBoxStartIndex) {
            throw ArithmeticException("The start position of the input box must be greater than the end position")
        }
        return mInputBoxStartIndex - mInputBoxEndIndex
    }

    /**
     * 获取 `ChildView` 子View [LayoutParams]
     */
    private fun fetchChildViewLayoutParams(index: Int, width: Int, height: Int): LayoutParams? {
//        if (mInputBoxSpacing <= 0)
//            mUseSpace = false

//        val totalChildWidth: Int =
//            (mInputBoxSpacing + mTelephoneNumberWidth) * mTelephoneNumberSize
//        if (mMeasuredWidth > 0 && totalChildWidth > mMeasuredWidth)
//            mUseSpace = false

//        val layoutParams = LayoutParams(
//            width, height
////        )
//        if (mUseSpace) {
//            layoutParams.setMargins(
//                mInputBoxSpacing / (if (index == 0) 1 else 2),
//                mInputBoxSpacing / 2,
//                mInputBoxSpacing / (if (index == mTelephoneNumberSize - 1) 1 else 2),
//                mInputBoxSpacing / 2
//            )

//        } else {
//            val totalChildSpace: Int =
//                mMeasuredWidth - (mTelephoneNumberWidth * mTelephoneNumberSize)
//            val childSpace: Int = totalChildSpace / mTelephoneNumberSize
//
//            layoutParams.setMargins(
//                childSpace / (if (index == 0) 1 else 2),
//                childSpace / 2,
//                childSpace / (if (index == mTelephoneNumberSize - 1) 1 else 2),
//                childSpace / 2
//            )
//        }
        return null
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        this.mMeasuredWidth = measuredWidth
    }


    /**
     * 设置电话号码 [telephoneNumber] 数据，并刷新界面
     * @param telephoneNumber [String] 手机号
     */
    fun setTelephoneNumber(telephoneNumber: String?) {
        if (telephoneNumber.isNullOrEmpty()) {
            this.mTelephoneNumber = telephoneNumber
            this.mTelephoneNumberSize = telephoneNumber?.length ?: 0
        }
    }

}