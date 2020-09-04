package com.android.draganddrop.di


import com.android.draganddrop.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}