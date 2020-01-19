package com.weather.weatherapp.di.component

import android.app.Application
import android.content.Context
import com.weather.weatherapp.App
import com.weather.weatherapp.di.module.ActivityBindingModule
import com.weather.weatherapp.di.module.AppModule
import com.weather.weatherapp.di.module.DataModule
import com.weather.weatherapp.di.module.FragmentBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjection
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class,
    FragmentBindingModule::class, AndroidInjectionModule::class,
    ActivityBindingModule::class])
interface AppComponent {
//    fun inject(activity: MainActivity)

    //    fun inject(fragment: HomeFragment)
//    fun inject(fragment: SplashFragment)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}