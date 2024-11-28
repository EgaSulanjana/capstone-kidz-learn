package com.c242ps187.kidzlearnapp.utils

import android.widget.ProgressBar
import androidx.core.view.isVisible

object Utils {
    fun ProgressBar.showLoading(isShow: Boolean) {
        this.isVisible = isShow
    }
}