package ru.aleksandrtrushchinskii.surfproject.common.service

import android.content.Context
import android.net.ConnectivityManager


class Internet(context: Context, private val toaster: Toaster) {

    private val networkInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

    val available: Boolean
        get() = networkInfo != null && networkInfo.isConnectedOrConnecting


    fun ifAvailable(code: () -> Unit) {
        if (available) {
            code()
        } else {
            toaster.internetNotAvailable()
        }
    }

}