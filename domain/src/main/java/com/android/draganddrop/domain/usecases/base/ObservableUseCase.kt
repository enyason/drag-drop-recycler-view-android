package com.android.draganddrop.domain.usecases.base

import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase<Result, in Param> {

    protected abstract fun buildFlowUseCase(params: Param? = null): Maybe<Result>

    fun execute(params: Param? = null): Maybe<Result> {
        return buildFlowUseCase(params).subscribeOn(Schedulers.io())
    }
}