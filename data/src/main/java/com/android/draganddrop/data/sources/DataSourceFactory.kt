package com.android.draganddrop.data.sources

import javax.inject.Inject

class DataSourceFactory @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun remote(): RemoteDataSource = remoteDataSource
    fun local(): LocalDataSource = localDataSource

}