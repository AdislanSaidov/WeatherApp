package com.weather.weatherapp.utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxUtils {
    fun <T> singleAsync(): SingleTransformer<T, T> {
        return SingleTransformer { upstream: Single<T> ->
            upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun compAsync(): CompletableTransformer {
        return CompletableTransformer { upstream: Completable ->
            upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> async(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream: Observable<T>->
            upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}