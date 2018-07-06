package ru.aleksandrtrushchinskii.surfproject.ui.adapter

import android.os.Bundle
import android.view.View
import ru.aleksandrtrushchinskii.surfproject.BR
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.tools.TODO_ID_KEY
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelAdapter
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.EditFragment


object TodoAdapter : ViewModelAdapter() {

    init {
        cell(Todo::class.java, R.layout.todo_item, BR.todo)

        sharedObject(this, BR.todoAdapter)
    }


    fun setData(data: List<Any>) {
        items.clear()

        for (item in data) {
            items.add(item)
        }

        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
    }

    fun itemClicked(view: View, todo: Todo) {
        LoadingState.start()

        val bundle = Bundle().apply { putString(TODO_ID_KEY, todo.id) }
        Navigation.startFragment(EditFragment::class.java.simpleName, bundle)
    }

}