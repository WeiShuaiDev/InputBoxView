package com.linwei.inputboxview.widget

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import com.linwei.inputboxview.R
import com.linwei.inputboxview.ext.color
import com.linwei.inputboxview.utils.UIUtils

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/4
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:[InputBoxView] 输入框@JvmOverloads
 *-----------------------------------------------------------------------
 */
class InputBoxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnKeyListener, TextWatcher,
    View.OnFocusChangeListener {
    //输入框的数量
    private var mInputBoxNumber: Int = 0
        set(value) {
            field = value
        }

    //输入类型
    private var mInputBoxType: Int = 0
        set(value) {
            field = value
        }

    //输入框的宽度
    private var mInputBoxWidth: Int = 0
        set(value) {
            field = value
        }

    //输入框文字颜色
    private var mInputBoxTextColor: Int = 0
        set(value) {
            field = value
        }

    //输入框文字大小
    private var mInputBoxTextSize: Float = 0f
        set(value) {
            field = value
        }

    //输入框背景
    private var mInputBoxBackground: Int = 0
        set(value) {
            field = value
        }

    //光标样式
    private var mInputBoxCursorType: Int = 0
        set(value) {
            field = value
        }

    //是否隐藏光标
    private var mInputBoxCursorVisible: Boolean = false
        set(value) {
            field = value
        }

    //输入框间距
    private var mInputBoxSpacing: Int = 0
        set(value) {
            field = value
        }

    private var mMeasuredWidth: Int = 0
    private var mUseSpace: Boolean = false


    init {
        val typeArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.InputBoxView)
        mInputBoxNumber =
            typeArray.getInteger(R.styleable.InputBoxView_input_box_number, 4)

        mInputBoxType = typeArray.getInt(R.styleable.InputBoxView_input_box_type, 1)

        mInputBoxWidth =
            typeArray.getDimensionPixelSize(
                R.styleable.InputBoxView_input_box_width,
                UIUtils.dp2px(context, 50f)
            )
        mInputBoxTextColor = typeArray.getColor(
            R.styleable.InputBoxView_input_boxt_text_color,
            context.color(R.color.colorInputBoxTextColor)
        )

        mInputBoxTextSize = typeArray.getDimensionPixelSize(
            R.styleable.InputBoxView_input_box_text_size,
            UIUtils.sp2px(context, 18f)
        ).toFloat()

        mInputBoxBackground = typeArray.getResourceId(
            R.styleable.InputBoxView_input_box_backgroud,
            R.drawable.input_box_bg
        )
        mInputBoxCursorType = typeArray.getResourceId(
            R.styleable.InputBoxView_input_box_cursor_type,
            R.drawable.input_box_cursor
        )

        mInputBoxCursorVisible =
            typeArray.getBoolean(R.styleable.InputBoxView_input_box_cursor_visible, false)

        mUseSpace = typeArray.hasValue(R.styleable.InputBoxView_input_box_spacing)
        if (mUseSpace) {
            mInputBoxSpacing = typeArray.getDimensionPixelSize(
                R.styleable.InputBoxView_input_box_spacing,
                UIUtils.dp2px(context, 10f)
            )
        }
        typeArray.recycle()

        addChildViewToContainer()
    }

    /**
     * 增加子 [AppCompatEditText] 到父容器 `Container`,同时第一个 [AppCompatEditText] 获取焦点
     */
    private fun addChildViewToContainer() {
        for (index: Int in 0 until mInputBoxNumber) {
            val child: AppCompatEditText = initChildView(index)
            if (index == 0)
                child.isFocusable = true
            addView(child)
        }
    }

    /**
     * @return AppCompatEditText
     */
    private fun initChildView(index: Int): AppCompatEditText {
        return AppCompatEditText(context).apply {
            layoutParams = fetchChildViewLayoutParams()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textAlignment = TEXT_ALIGNMENT_CENTER
            }
            gravity = Gravity.CENTER
            id = index
            maxEms = 1
            textSize = mInputBoxTextSize
            maxLines = 1
            filters = arrayOf<InputFilter>(LengthFilter(1))
            isCursorVisible = mInputBoxCursorVisible
            inputType = InputType.TYPE_CLASS_NUMBER
            setTextColor(mInputBoxTextColor)
            setPadding(0, 0, 0, 0)
            setBackgroundResource(mInputBoxBackground)
            setOnKeyListener(this@InputBoxView)
            addTextChangedListener(this@InputBoxView)
            onFocusChangeListener = this@InputBoxView
        }
    }

    /**
     * 获取 `ChildView` 子View [LayoutParams]
     */
    private fun fetchChildViewLayoutParams(): LayoutParams {
        return LayoutParams(
            mInputBoxWidth,
            mInputBoxWidth
        )
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        this.mMeasuredWidth = measuredWidth

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (mInputBoxSpacing <= 0)
            mUseSpace = false

        val totalChildWidth: Int = (mInputBoxSpacing + mInputBoxWidth) * childCount
        if (totalChildWidth > mMeasuredWidth)
            mUseSpace = false

        for (index: Int in 0 until childCount) {
            val child: View = getChildAt(index)
            if (mUseSpace) {
                child.layout(
                    ((mInputBoxWidth + mInputBoxSpacing) * index) + mInputBoxSpacing / 2,
                    t,
                    (mInputBoxWidth + mInputBoxSpacing) * (index + 1),
                    b
                )
            } else {
                val totalChildSpace: Int = mMeasuredWidth - (mInputBoxWidth * childCount)
                val childSpace: Int = totalChildSpace / childCount
                child.layout(
                    ((mInputBoxWidth + childSpace) * index) + childSpace / 2,
                    t,
                    (mInputBoxWidth + childSpace) * (index + 1),
                    b
                )
            }
        }
    }


    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.equals(KeyEvent.ACTION_DOWN) == false) {

        }
        return true
    }

    override fun afterTextChanged(s: Editable?) {
        TODO("Not yet implemented")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        TODO("Not yet implemented")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        TODO("Not yet implemented")
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {

    }


}