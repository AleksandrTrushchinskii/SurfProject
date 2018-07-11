package ru.aleksandrtrushchinskii.surfproject.ui.adapter

import android.view.View
import ru.aleksandrtrushchinskii.surfproject.BR
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelAdapter


class TodoAdapter(private val onClickItem: (todo: Todo) -> Unit) : ViewModelAdapter() {

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

    fun itemClicked(view: View, todo: Todo) {
        onClickItem(todo)
    }

}