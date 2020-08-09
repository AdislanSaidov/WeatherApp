package com.weather.weatherapp.ui.search

import com.jakewharton.rxrelay2.PublishRelay
import com.weather.weatherapp.data.datasource.SearchRepository
import com.weather.weatherapp.ui.base.BasePresenter
import com.weather.weatherapp.utils.RxUtils
import moxy.InjectViewState
import timber.log.Timber
import java.util.concurrent.TimeUnit

@InjectViewState
class SearchPresenter(private val repository: SearchRepository) : BasePresenter<SearchMvp>() {

    private val publishRelay = PublishRelay.create<String>()

    override fun onFirstViewAttach() {
        disposable.add(publishRelay
            .debounce(SUGGESTION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                repository.search(it).compose(RxUtils.async())
            }.subscribe ( {
                if(it.isEmpty()){
                    viewState.showNotFound()
                }else {
                    viewState.showAreas(it)
                }
            }, Timber::e))
    }

    fun onSearchQueryChanged(query: String){
        publishRelay.accept(query)
    }

    companion object {
        private const val SUGGESTION_TIMEOUT_MS = 300L
    }


}