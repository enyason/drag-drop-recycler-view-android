package com.android.draganddrop.domain.usecases.base

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

abstract class CompleteableUseCase<Result, in Param> {

    protected abstract fun buildFlowUseCase(params: Param? = null): Completable

    fun execute(params: Param? = null): Completable {
        return buildFlowUseCase(params).subscribeOn(Schedulers.io())
    }
}