package com.linwei.inputboxview.widget

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.annotation.IdRes
import com.linwei.inputboxview.R
import com.linwei.inputboxview.enum.InputDataType
import com.linwei.inputboxview.ext.color
import com.linwei.inputboxview.listener.OnInputDataListener
import com.linwei.inputboxview.listener.OnInputDataStateListener
import com.linwei.inputboxview.utils.UIUtils
import java.lang.NullPointerException

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
) : LinearLayout(context, attrs, defStyleAttr), OnInputDataListener {
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

    companion object {
        @JvmStatic
        fun Builder(context: Context): Builder {
            return TelephoneNumberView.Builder(context)
        }
    }

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
     *  首先判断容器中有没有子类 View，如果存在子类View,则进行清空，根据输入框起始位置 [mInputBoxStartIndex]
     *  结束位置 [mInputBoxEndIndex] 范围创建控件,并增加容器中。
     */
    private fun addChildViewToContainer() {
        this.mInputBoxSize = fetchInputBoxNumber()

        for (index: Int in 1..mInputBoxStartIndex) {
            val childTextView = initChildTextView(index)
            addView(childTextView)
        }

        val childInputBoxView = initChildInputBoxView()
        addView(childInputBoxView)

        for (index: Int in mInputBoxEndIndex..mTelephoneNumberSize) {
            val childTextView = initChildInputBoxView()
            addView(childTextView)
        }

    }

    /**
     * 根据 `index` 位置坐标，创建 [TextView] 控件
     * @param index [Int]
     * @return textView [TextView]
     */
    private fun initChildTextView(index: Int): TextView {
        return TextView(context).apply {
            layoutParams = fetchTextViewLayoutParams(index)
            textSize = mTelephoneNumberTextSize
            setTextColor(mTelephoneNumberTextColor)
        }
    }

    /**
     * 创建 [InputBoxView] 输入框控件
     * @return intpuBoxView [InputBoxView]
     */
    private fun initChildInputBoxView(): InputBoxView {
        return InputBoxView.Builder(context)
            .setInputBoxNumber(mInputBoxSize)
            .setInputBoxBackground(mInputBoxBackground)
            .setInputBoxSpacing(mInputBoxSpacing)
            .setInputBoxTextSize(mInputBoxTextSize)
            .setInputBoxCursorVisible(mInputBoxCursorVisible)
            .setInputBoxWidth(mTelephoneNumberWidth)
            .setInputBoxType(InputDataType.NUMBER)
            .setInputBoxTextColor(mInputBoxTextColor)
            .setInputBoxCursorType(mInputBoxCursorType)
            .setOnInputDataListener(TelephoneNumberView@ this)
            .build()
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
     * 获取 [TextView] 控件 `LayoutParams`
     * @param index [Int] 角标
     * @return layoutParams [LayoutParams]
     */
    private fun fetchTextViewLayoutParams(index: Int): LayoutParams? {

        val layoutParams = LayoutParams(mTelephoneNumberWidth, mTelephoneNumberWidth)

        if (mInputBoxSpacing > 0) {
            layoutParams.setMargins(
                mInputBoxSpacing / (if (index == 0) 1 else 2),
                mInputBoxSpacing / 2,
                mInputBoxSpacing / (if (index == mTelephoneNumberSize - 1) 1 else 2),
                mInputBoxSpacing / 2
            )
        }

        return layoutParams
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

    private var mListener: OnInputDataStateListener? = null
    fun setOnInputDataStateListener(listener: OnInputDataStateListener) {
        this.mListener = listener
    }

    override fun onTextChange(view: Editable?, content: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onComplete(view: Editable?, content: String?) {
        checkStateFromTelephone(content).let {
            mListener?.onInputState(it)
        }
    }

    /**
     * 根据用户输入数据 [inputData]，跟原来手机号号码 [mTelephoneNumber] 进行比较，来确定数据正确性。
     * 根据 [mInputBoxStartIndex]、[mInputBoxEndIndex] 截取手机号信息
     * 注意，这里角标是从 `1` 开始。
     * @param inputData [String]
     */
    private fun checkStateFromTelephone(inputData: String?): Boolean {
        if (mTelephoneNumber.isNullOrEmpty()) {
            throw NullPointerException("mTelephoneNumber Field is empty, Please enter the phone number！")
        }
        val checkData = mTelephoneNumber?.substring(mInputBoxStartIndex - 1, mInputBoxEndIndex + 1)

        if (checkData.isNullOrEmpty() && checkData == inputData) {
            return true
        }
        return false
    }

    class Builder constructor(private var context: Context) {
        private val mTelephoneNumberView: TelephoneNumberView = TelephoneNumberView(context)

        /**
         * 输入框的宽度
         */
        fun setInputBoxNumber(width: Int): Builder {
            mTelephoneNumberView.mTelephoneNumberWidth = width
            return this
        }

        /**
         * 输入框文字颜色
         */
        fun setInputBoxTextColor(@IdRes color: Int): Builder {
            mTelephoneNumberView.mInputBoxTextColor = context.color(color)
            return this
        }

        /**
         * 文字颜色
         */
        fun setTelephoneNumberTextColor(@IdRes color: Int): Builder {
            mTelephoneNumberView.mTelephoneNumberTextColor = context.color(color)
            return this
        }


        /**
         * 输入框文字大小
         */
        fun setInputBoxTextSize(size: Float): Builder {
            mTelephoneNumberView.mInputBoxTextSize = size
            return this
        }


        /**
         * 文字大小
         */
        fun setTelephoneNumberTextSize(size: Float): Builder {
            mTelephoneNumberView.mTelephoneNumberTextSize = size
            return this
        }


        /**
         * 输入框背景
         */
        fun setInputBoxBackground(background: Int): Builder {
            mTelephoneNumberView.mInputBoxBackground = background
            return this
        }


        /**
         * 光标样式
         */
        fun setInputBoxCursorType(@IdRes type: Int): Builder {
            mTelephoneNumberView.mInputBoxCursorType = type
            return this
        }


        /**
         * 是否隐藏光标
         */
        fun setInputBoxCursorVisible(visible: Boolean): Builder {
            mTelephoneNumberView.mInputBoxCursorVisible = visible
            return this
        }

        /**
         * 输入框间距
         */
        fun setInputBoxSpacing(spacing: Int): Builder {
            mTelephoneNumberView.mInputBoxSpacing = spacing
            return this
        }

        /**
         * 输入框起始角标
         */
        fun setInputBoxStartIndex(index: Int): Builder {
            mTelephoneNumberView.mInputBoxStartIndex = index
            return this
        }

        /**
         * 输入框结束角标
         */
        fun setInputBoxEndIndex(index: Int): Builder {
            mTelephoneNumberView.mInputBoxEndIndex = index
            return this
        }

        /**
         * 事件监听
         */
        fun setOnInputDataStateListener(listener: OnInputDataStateListener): Builder {
            mTelephoneNumberView.mListener = listener
            return this
        }

        fun build(): TelephoneNumberView {
            if (mTelephoneNumberView.childCount > 0)
                mTelephoneNumberView.removeAllViews()

            mTelephoneNumberView.addChildViewToContainer()
            return mTelephoneNumberView
        }
    }
}