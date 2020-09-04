package com.android.draganddrop.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.android.draganddrop.databinding.ActivityMainBinding
import com.android.draganddrop.utils.Result
import com.android.draganddrop.utils.makeGone
import com.android.draganddrop.utils.makeVisible
import com.android.draganddrop.utils.toast
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory
    lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getTodoList()


        val dragDropManager = RecyclerViewDragDropManager()

        var adapter = DraggableExampleItemAdapter{displaced,removed->
            /**
             * save to db
             */
            viewModel.updateDb(listOf(displaced,removed))
        }
        val wrappedAdapter = dragDropManager.createWrappedAdapter(adapter)

        binding.todoRecyclerView.apply {
            setAdapter(wrappedAdapter)
            layoutManager = LinearLayoutManager(this@MainActivity)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        }

        dragDropManager.setInitiateOnLongPress(true)
        dragDropManager.attachRecyclerView(binding.todoRecyclerView)


//        val todoAdapter = object : RecyclerViewAdapter<Todo>(ToDoDiffUtil()) {
//            override fun getLayoutRes(model: Todo): Int {
//                return R.layout.row_item
//            }
//
//            override fun getViewHolder(
//                view: View,
//                recyclerViewAdapter: RecyclerViewAdapter<Todo>
//            ): ViewHolder<Todo> {
//                return TodoViewHolder(RowItemBinding.bind(view))
//            }
//
//        }

//
//        binding.todoRecyclerView.apply {
//            adapter = todoAdapter
//            layoutManager = LinearLayoutManager(this@MainActivity)
//        }

        viewModel.todoList.observe(this, Observer {

            when (it) {
                Result.Loading -> {
                    binding.progressBar.makeVisible()
                }
                is Result.Success -> {
                    it.data?.let {
                        adapter.mProvider = it.toMutableList()
                    }
                    binding.progressBar.makeGone()

                }

                is Result.Error -> {
                    binding.progressBar.makeGone()
                    toast(it.error.message)
                }
            }
        })

    }

}