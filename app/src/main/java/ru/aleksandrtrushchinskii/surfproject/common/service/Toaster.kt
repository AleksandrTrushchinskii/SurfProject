package ru.aleksandrtrushchinskii.surfproject.common.service

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import ru.aleksandrtrushchinskii.surfproject.R


class Toaster(private val context: Context) {

    var toast: Toast? = null


    fun internetNotAvailable() {
        showToast(R.string.toast_internet_not_available)
    }

    fun signInWasFailed() {
        showToast(R.string.toast_sign_in_was_failed)
    }

    private fun showToast(@StringRes stringId: Int) {
        if (toast != null) toast = null

        toast = Toast.makeText(context, stringId, Toast.LENGTH_SHORT)

        toast?.show()
    }

}