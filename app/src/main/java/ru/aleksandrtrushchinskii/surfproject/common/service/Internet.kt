package ru.aleksandrtrushchinskii.surfproject.common.service

import android.content.Context
import android.net.ConnectivityManager
import ru.aleksandrtrushchinskii.surfproject.common.tools.logDebug


class Internet(context: Context, private val toaster: Toaster) {

    private val networkInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

    val available: Boolean
        get() = networkInfo != null && networkInfo.isConnectedOrConnecting


    fun ifAvailable(code: () -> Unit) {
        if (available) {
            code()
        } else {
            logDebug("Internet not Available")
            toaster.internetNotAvailable()
        }
    }

}