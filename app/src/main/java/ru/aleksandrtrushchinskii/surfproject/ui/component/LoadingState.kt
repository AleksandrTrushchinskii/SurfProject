package ru.aleksandrtrushchinskii.surfproject.ui.component

import android.arch.lifecycle.MutableLiveData


object LoadingState {

    val isActive by lazy {
        MutableLiveData<Boolean>().apply {
            value = false
        }
    }


    fun start() {
        isActive.value = true
    }

    fun stop() {
        isActive.value = false
    }

}