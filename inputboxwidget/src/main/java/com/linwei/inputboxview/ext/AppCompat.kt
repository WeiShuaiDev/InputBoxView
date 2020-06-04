package com.linwei.inputboxview.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build


/**
 * @param id [Int] 颜色ResId
 */
fun Context.color(id: Int): Int {
   return  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        resources.getColor(id, null)
    } else {
        resources.getColor(id)
    }
}

