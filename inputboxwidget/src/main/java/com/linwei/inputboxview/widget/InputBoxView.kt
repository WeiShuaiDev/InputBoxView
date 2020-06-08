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
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.linwei.inputboxview.R
import com.linwei.inputboxview.enum.InputDataType
import com.linwei.inputboxview.ext.color
import com.linwei.inputboxview.listener.InputTransformationMethod
import com.linwei.inputboxview.listener.OnInputDataListener
import com.linwei.inputboxview.utils.UIUtils
import java.lang.reflect.Field

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/4
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:[InputBoxView] 输入框
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

    //输入类型
    private var mInputBoxType: Int = 0

    //输入框的宽度
    private var mInputBoxWidth: Int = 0

    //输入框文字颜色
    private var mInputBoxTextColor: Int = 0

    //输入框文字大小
    private var mInputBoxTextSize: Float = 0f

    //输入框背景
    private var mInputBoxBackground: Int = 0

    //光标样式
    private var mInputBoxCursorType: Int = 0

    //是否隐藏光标
    private var mInputBoxCursorVisible: Boolean = false

    //输入框间距
    private var mInputBoxSpacing: Int = 0

    private var mMeasuredWidth: Int = 0
    private var mUseSpace: Boolean = false


    init {
        val typeArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.InputBoxView)
        mInputBoxNumber =
            typeArray.getInteger(R.styleable.InputBoxView_input_box_number, 4)

        mInputBoxType = typeArray.getInt(R.styleable.InputBoxView_input_box_type, 0)

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
     * 增加子 [EditText] 到父容器 `Container`,同时第一个 [EditText] 获取焦点
     */
    private fun addChildViewToContainer() {
        for (index: Int in 0 until mInputBoxNumber) {
            val child: EditText = initChildView(index)
            if (index == 0)
                child.isFocusable = true
            addView(child)
        }
    }

    /**
     * @return EditText
     */
    private fun initChildView(index: Int): EditText {
        return EditText(context).apply {
            layoutParams = fetchChildViewLayoutParams()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textAlignment = TEXT_ALIGNMENT_CENTER
            }
            gravity = Gravity.CENTER
            id = index
            maxEms = 1
            maxLines = 1
            filters = arrayOf<InputFilter>(LengthFilter(1))
            textSize = mInputBoxTextSize
            isCursorVisible = mInputBoxCursorVisible
            inputType = InputType.TYPE_CLASS_NUMBER
            setInputType(this)
            setInputCursorDrawable(this)
            setTextColor(mInputBoxTextColor)
            setPadding(0, 0, 0, 0)
            setBackgroundResource(mInputBoxBackground)
            setOnKeyListener(this@InputBoxView)
            addTextChangedListener(this@InputBoxView)
            onFocusChangeListener = this@InputBoxView
        }
    }

    /**
     * 修改 [EditText] 控件 `setInputType`类型
     * @param editText [EditText]
     */
    private fun setInputType(editText: EditText) {
        when (mInputBoxType) {
            InputDataType.NUMBER.type -> {
                editText.inputType = InputType.TYPE_CLASS_NUMBER
            }

            InputDataType.NUMBER_PASSWORD.type -> {
                editText.inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                editText.transformationMethod = InputTransformationMethod()
            }

            InputDataType.TEXT.type -> {
                editText.inputType = InputType.TYPE_CLASS_TEXT
            }

            InputDataType.TEXT_PASSWORD.type -> {
                editText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                editText.transformationMethod = InputTransformationMethod()
            }
            else -> {
                editText.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
    }

    /**
     *  修改 [EditText] 控件光标样式
     * @param editText [EditText]
     */
    private fun setInputCursorDrawable(editText: EditText) {
        //当前光标显示，修改光标样式
        if (mInputBoxCursorVisible) {
            try {
                val field: Field =
                    TextView::class.java.getDeclaredField("mCursorDrawableRes")
                field.isAccessible = true
                field[editText] = mInputBoxCursorType
            } catch (e: Exception) {
                e.printStackTrace()
            }
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
                    (mInputBoxWidth + mInputBoxSpacing) * (index + 1) - mInputBoxSpacing / 2,
                    b
                )
            } else {
                val totalChildSpace: Int = mMeasuredWidth - (mInputBoxWidth * childCount)
                val childSpace: Int = totalChildSpace / childCount
                child.layout(
                    ((mInputBoxWidth + childSpace) * index) + childSpace / 2,
                    t,
                    (mInputBoxWidth + childSpace) * (index + 1) - childSpace / 2,
                    b
                )
            }
        }
    }


    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action!! == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
            System.out.println("+++")
            removeViewFocus()
        }
        return false
    }

    override fun afterTextChanged(view: Editable?) {
        if (view?.length != 0) updateViewFocus()
        val text: String = getText()
        mListener?.onTextChange(view, text)

        val lastChildTextLength: Int =
            (getChildAt(mInputBoxNumber - 1) as EditText).length()
        if (lastChildTextLength > 0) {
            mListener?.onComplete(view, text)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus)
            updateViewFocus()
    }

//    override fun setEnabled(enabled: Boolean) {
//        var view: EditText
//        for (index: Int in 0 until childCount) {
//            view = getChildAt(index) as EditText
//            view.isEnabled = enabled
//        }
//    }

    /**
     * 更新 `ViewGroup` 中 [EditText] 焦点。
     */
    private fun updateViewFocus() {
        var view: EditText
        for (index: Int in 0 until childCount) {
            view = getChildAt(index) as EditText
            if (view.text.isNullOrEmpty()) {
                view.isCursorVisible = mInputBoxCursorVisible
                view.requestFocus()
                return
            } else {
                view.isCursorVisible = false
                if (index == childCount - 1) view.requestFocus()
            }
        }
    }

    /**
     * 清除 `ViewGroup` 中 [EditText] 焦点。
     */
    private fun removeViewFocus() {
        var view: EditText
        for (index: Int in (childCount - 1)..0) {
            view = getChildAt(index) as EditText
            if (view.text.isNotEmpty()) {
                System.out.println("index${index}")
                view.setText("")
                view.isCursorVisible = mInputBoxCursorVisible
                view.requestFocus()
                return
            }
        }
    }

    /**
     *清除 `ViewGroup` 中 [EditText] 中所有数据
     */
    private fun removeViewData() {
        var view: EditText
        for (index: Int in (childCount - 1)..0) {
            view = getChildAt(index) as EditText
            if (!view.text.isNullOrEmpty()) {
                view.setText("")
                if (index == 0) {
                    view.isCursorVisible = mInputBoxCursorVisible
                    view.requestFocus()
                }
            }
        }
    }

    /**
     * 获取 [EditText] 所有数据
     * @return view数据
     */
    private fun getText(): String {
        var view: EditText
        val sb = StringBuilder()
        for (index: Int in 0 until childCount) {
            view = getChildAt(index) as EditText
            sb.append(view.text)
        }
        return sb.toString()
    }

    private var mListener: OnInputDataListener? = null
    fun setOnInputDataListener(listener: OnInputDataListener) {
        this.mListener = listener
    }
}