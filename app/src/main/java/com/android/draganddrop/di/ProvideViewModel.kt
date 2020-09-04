package com.android.draganddrop.di

import com.android.draganddrop.domain.usecases.TodoListUseCase
import com.android.draganddrop.domain.usecases.UpdateTodoListUseCase
import com.android.draganddrop.ui.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class ProvideViewModel {

    @Provides
    fun provideFeatureViewModel(getTodoListUseCase: TodoListUseCase,updateTodoListUseCase: UpdateTodoListUseCase) =
        MainViewModel(getTodoListUseCase,updateTodoListUseCase)

    @Provides
    fun provideFeatureViewModelFactory(provider: Provider<MainViewModel>) =
        MainViewModel.Factory(provider)

}

