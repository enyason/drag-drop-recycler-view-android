package com.android.draganddrop.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.draganddrop.domain.models.TodoDomain
import com.android.draganddrop.domain.usecases.TodoListUseCase
import com.android.draganddrop.domain.usecases.UpdateTodoListUseCase
import com.android.draganddrop.utils.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(
    private val getTodoListUseCase: TodoListUseCase,
    private val updateTodoListUseCase: UpdateTodoListUseCase
) :
    ViewModel() {


    private val _todoList = MutableLiveData<Result<List<Todo>>>()
    val todoList: LiveData<Result<List<Todo>>>
        get() = _todoList

    private val container = CompositeDisposable()


    fun getTodoList() {

        val disposable = getTodoListUseCase
            .execute()
            .doOnSubscribe {
                _todoList.postValue(Result.Loading)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _todoList.postValue(Result.Success(it.map { todo ->
                        with(todo) {
                            Todo(
                                userId,
                                id,
                                title,
                                completed
                            )
                        }
                    }))
                }, {
                    _todoList.postValue(Result.Error(it))

                }
            )

        container.add(disposable)
    }

    fun updateDb(it: List<Todo>) {

        updateTodoListUseCase.execute(it.map { todo ->
            with(todo) {
                TodoDomain(
                    userId,
                    id,
                    title,
                    completed
                )
            }
        }).subscribe()
    }


    class Factory(val provider: Provider<MainViewModel>) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return provider.get() as T // Delegate call to provider
        }
    }
}