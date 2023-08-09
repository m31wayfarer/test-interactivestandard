package com.review.interactivestandard.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceHelper @Inject constructor(@ApplicationContext private val context: Context) :
    IResourceHelper {
    override fun getStringByResId(id: Int, vararg param: Any): String {
        return context.getString(id, *param)
    }
}