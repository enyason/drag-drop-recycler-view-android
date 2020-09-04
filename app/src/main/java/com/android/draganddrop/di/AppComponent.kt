package com.android.draganddrop.di

import com.android.draganddrop.App
import javax.inject.Singleton

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBindingModule::class, AppModule::class,ProvideViewModel::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}