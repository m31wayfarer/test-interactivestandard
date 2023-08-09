package com.review.interactivestandard.utils

interface IResourceHelper {
    fun getStringByResId(id: Int, vararg param: Any): String
}