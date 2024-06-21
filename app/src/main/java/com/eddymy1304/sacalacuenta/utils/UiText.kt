package com.eddymy1304.sacalacuenta.utils

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {

    data class DynamicString(val text: String) : UiText()

    data object Empty : UiText()

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    fun asString(context: Context?): String {
        return when(this) {
            is DynamicString -> text
            Empty -> ""
            is StringResource -> context?.getString(resId, *args).orEmpty()
        }
    }
}