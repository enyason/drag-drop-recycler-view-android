package com.android.draganddrop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.draganddrop.databinding.RowItemBinding

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder


internal class DraggableExampleItemAdapter(private val onItemPositionChanged: (Todo, Todo) -> Unit) :
    RecyclerView.Adapter<DraggableExampleItemAdapter.MyViewHolder>(),
    DraggableItemAdapter<DraggableExampleItemAdapter.MyViewHolder> {

    var mProvider = mutableListOf<Todo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyViewHolder(val binding: RowItemBinding) :
        AbstractDraggableItemViewHolder(binding.root) {

        var dragHandle = binding.dragHandle
        fun bind(element: Todo) {

            binding.title.text = element.title
//
            if (element.completed) {
                binding.status.text = "Compelted"
            } else {
                binding.status.text = "Not Compelted"
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return mProvider[position].id.toLong()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(RowItemBinding.inflate(inflater))
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {

        holder.bind(mProvider[position])

    }

    override fun getItemCount(): Int {
        return mProvider.size
    }

    override fun onMoveItem(fromPosition: Int, toPosition: Int) {

        val displaced: Todo = mProvider[toPosition]
        val removed: Todo = mProvider.removeAt(fromPosition)
        mProvider.add(toPosition, removed)

        /**
         * save to db
         */
        onItemPositionChanged(displaced, removed)
        println("item moved $fromPosition $toPosition")
    }

    override fun onCheckCanStartDrag(
        holder: MyViewHolder,
        position: Int,
        x: Int,
        y: Int
    ): Boolean {
        val dragHandle = holder.dragHandle
        val handleWidth: Int = dragHandle.width
        val handleHeight: Int = dragHandle.height
        val handleLeft: Int = dragHandle.left
        val handleTop: Int = holder.dragHandle.top

        return x >= handleLeft && x < handleLeft + handleWidth &&
                y >= handleTop && y < handleTop + handleHeight
    }

    override fun onGetItemDraggableRange(
        holder: MyViewHolder,
        position: Int
    ): ItemDraggableRange? {
        // no drag-sortable range specified
        return null
    }

    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int): Boolean {
        return true
    }

    override fun onItemDragStarted(position: Int) {
        notifyDataSetChanged()
    }

    override fun onItemDragFinished(
        fromPosition: Int,
        toPosition: Int,
        result: Boolean
    ) {
        notifyDataSetChanged()
    }

    init {

        // DraggableItemAdapter requires stable ID, and also
        // have to implement the getItemId() method appropriately.
        setHasStableIds(true)
    }
}