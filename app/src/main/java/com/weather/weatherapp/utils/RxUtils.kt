package com.weather.weatherapp.utils

import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
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
}