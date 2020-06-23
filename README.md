# InputBoxView组件用法介绍

## 前言

该组件具备常见验证码输入框功能，定义输入框位数，显示模式等。在组件中有两个核心类，分别是VerifyCodeView(验证码输入框)、TelephoneNumberView(手机号输入框)，使用中提供两个创建方式：代码方式、布局方式。而且，为了创建过程中多配置问题，也会进行默认配置，简化创建过程。该页面只针对VerifyCodeView控件讲解，需要查看详细代码可以下载，框架内部提供一些常用案例。

###  效果图


### 方法

| 方法                                        | 返回类型     | 描述                                |
| ------------------------------------------- | ------------ | ----------------------------------- |
| setInputBoxNumber(Int)                      | Builder      | 输入框的数量                        |
| setInputBoxType(InputDataType)              | Builder      | 输入类型 (类型使用nputDataType枚举) |
| setInputBoxWidth(Int)                       | Builder      | 输入框的宽度                        |
| setInputBoxTextColorId(Int)                 | Builder      | 输入框文字颜色                      |
| setInputBoxTextColor(Int)                   | Builder      | 输入框文字颜色                      |
| setInputBoxTextSize(Float)                  | Builder      | 输入框文字大小                      |
| setInputBoxBackground(Int)                  | Builder      | 输入框背景                          |
| setInputBoxCursorType(Int)                  | Builder      | 光标样式                            |
| setInputBoxCursorVisible(Boolean)           | Builder      | 是否隐藏光标                        |
| setInputBoxSpacing(Int)                     | Builder      | 输入框间距                          |
| setOnInputDataListener(OnInputDataListener) | Builder      | 事件监听                            |
| build()                                     | InputBoxView | 初始化InputBoxView                  |

### Attributes属性

| Attributes               | format               | describe                                                     |
| ------------------------ | -------------------- | ------------------------------------------------------------ |
| input_box_number         | integer              | 输入框的数量                                                 |
| input_box_type           | enum                 | 输入类型(number:普通数字、numberPassword:数字密码、text:文本、textPassword：文本密码) |
| input_box_width          | dimension\|reference | 输入框的宽度                                                 |
| input_box_text_color     | color\|reference     | 输入框文字颜色                                               |
| input_box_text_size      | dimension\|reference | 输入框文字大小                                               |
| input_box_backgroud      | reference            | 输入框背景                                                   |
| input_box_cursor_type    | reference            | 光标样式                                                     |
| input_box_cursor_visible | boolean              | 是否隐藏光标                                                 |
| input_box_spacing        | dimension\|reference | 输入框间距                                                   |
### 使用步骤

#### Step 1.增加依赖

```
  dependencies{
      implementation 'com.weishuai:inputboxwidget:1.0.0'
  }
```

#### Step 2、在布局文件中增加InputBoxView,可以设置自定义属性

```
  <com.linwei.inputboxview.widget.VerifyCodeView
            android:id="@+id/mInputBoxView1"
            style="@style/WrapAndWrap"
            android:layout_marginTop="@dimen/dp_20"
            app:input_box_backgroud="@drawable/select_input_box_line_bg"
            app:input_box_cursor_type="@drawable/shape_input_box_cursor"
            app:input_box_number="4"
            app:input_box_spacing="5dp"
            app:input_box_text_color="@color/colorInputBoxTextColor"
            app:input_box_text_size="6sp"
            app:input_box_type="number"
            app:input_box_width="30dp" />
```

#### Step 3、在代码中增加InputBoxView监听事件

```
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
```

### 另外可以通过代码初始化InputBoxView

```
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
```

## 赞赏

如果Open Coder对您有很大帮助，您愿意扫描下面的二维码，只需要支付0.01，表达您对我认可和鼓励。
> 非常感谢您的捐赠。谢谢!

<div align="center">
<img src="https://github.com/WeiSmart/tablayout/blob/master/screenshots/weixin_pay.jpg" width=20%>
<img src="https://github.com/WeiSmart/tablayout/blob/master/screenshots/zifubao_pay.jpg" width=20%>
</div>
---

## About me
- #### Email:linwei9605@gmail.com   
- #### Blog: [https://offer.github.io/](https://offer.github.io/)
- #### 掘金: [https://juejin.im/user/59091b030ce46300618529e0](https://juejin.im/user/59091b030ce46300618529e0)

## License
```
   Copyright 2020 offer

      Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.```

```
