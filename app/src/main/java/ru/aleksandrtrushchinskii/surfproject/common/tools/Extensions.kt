package ru.aleksandrtrushchinskii.surfproject.common.tools

import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.aleksandrtrushchinskii.surfproject.ui.MainActivity


fun ViewGroup?.inflate(@LayoutRes layoutId: Int): View {
    return LayoutInflater.from(this?.context).inflate(layoutId, this, false)
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun Fragment.finish() {
    (this.activity as MainActivity).finishFragment(this)
}

fun Fragment.startAnotherFragment(fragmentClassName: String) {
    (this.activity as MainActivity).startFragment(fragmentClassName)
}