package com.universeindustry.retrofit2json.utils.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.ViewConfiguration
import androidx.core.content.ContextCompat

object Resizing {
    fun reSizedImage(context : Context, image : Int, width : Int, height : Int) : Drawable {
        val drawable = ContextCompat.getDrawable(context, image)
        val bitmap = (drawable as BitmapDrawable).bitmap
        return BitmapDrawable(context.resources, Bitmap.createScaledBitmap(bitmap, width, height, true))
    }

    fun statusBar(context : Context): Int {
        var result = 0
        val resourceId =  context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) { result = context.resources.getDimensionPixelSize(resourceId) }
        return result
    }
    fun navigationBar(context : Context): Int {
        val hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey()
        val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0 && !hasMenuKey) {
            context.resources.getDimensionPixelSize(resourceId)
        } else 0
    }
    fun screenWidth(activity : Activity) : Int{
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val pointWidht = size.x
        return pointWidht
    }
    fun screenHeight(activity : Activity) : Int{
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }
}