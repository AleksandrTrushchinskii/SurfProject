package ru.aleksandrtrushchinskii.surfproject.common.tools

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Observable
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo


fun ViewGroup?.inflate(@LayoutRes layoutId: Int): View {
    return LayoutInflater.from(this?.context).inflate(layoutId, this, false)
}

fun <T : ViewDataBinding> ViewGroup?.inflateBinding(@LayoutRes layoutId: Int, attachToRoot: Boolean = false): T {
    return DataBindingUtil.inflate(LayoutInflater.from(this!!.context), layoutId, this, attachToRoot)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Any.logDebug(message: String) {
    Log.d(this::class.java.simpleName, message)
}

fun Any.logError(message: String) {
    Log.e(this::class.java.simpleName, message)
}

fun DocumentSnapshot.toTodo(): Todo {
    val todo = this.toObject(Todo::class.java)

    if (todo != null) {
        todo.id = this.id

        return todo
    } else {
        return Todo()
    }
}

fun SearchView.toObservable() = Observable.create<String> { emitter ->
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            if (!emitter.isDisposed) {
                emitter.onNext(newText)
            }
            return true
        }
    })
}