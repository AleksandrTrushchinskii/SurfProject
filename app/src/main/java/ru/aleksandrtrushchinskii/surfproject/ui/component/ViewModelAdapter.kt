package ru.aleksandrtrushchinskii.surfproject.ui.component

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import ru.aleksandrtrushchinskii.surfproject.common.tools.inflate
import java.util.*
import kotlin.collections.HashMap


abstract class ViewModelAdapter : RecyclerView.Adapter<ViewModelAdapter.ViewHolder>() {

    data class CellInfo(val layoutId: Int, val bindingId: Int)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ViewDataBinding>(view)
    }


    protected val items = LinkedList<Any>()

    private val cellMap = HashMap<Class<out Any>, CellInfo>()

    private val sharedObjects = HashMap<Int, Any>()


    protected fun cell(clazz: Class<out Any>, @LayoutRes layoutId: Int, bindingId: Int) {
        cellMap[clazz] = CellInfo(layoutId, bindingId)
    }

    protected fun getCellInfo(viewModel: Any): CellInfo {
        cellMap.entries.filter {
            it.key == viewModel.javaClass
        }.first {
            return it.value
        }

        throw Exception("Cell info for class ${viewModel.javaClass.name} not found.")
    }

    protected fun sharedObject(sharedObject: Any, bindingId: Int) {
        sharedObjects[bindingId] = sharedObject
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return getCellInfo(items[position]).layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(viewType)
        val viewHolder = ViewHolder(view)

        sharedObjects.forEach { viewHolder.binding?.setVariable(it.key, it.value) }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cellInfo = getCellInfo(items[position])

        if (cellInfo.bindingId != 0) {
            holder.binding?.setVariable(cellInfo.bindingId, items[position])
        }
    }

}