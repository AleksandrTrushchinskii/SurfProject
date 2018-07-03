package ru.aleksandrtrushchinskii.surfproject.common.service

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject


class Internet @Inject constructor(context: Context, private val toaster: Toaster) {

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