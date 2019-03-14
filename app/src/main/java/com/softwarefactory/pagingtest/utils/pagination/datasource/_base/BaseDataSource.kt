package com.softwarefactory.pagingtest.utils.pagination.datasource._base

import android.arch.paging.PageKeyedDataSource;
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseDataSource<T> : PageKeyedDataSource<Int, T>() {
    lateinit var onDataSourceLoading: OnDataSourceLoading

    private var compositeDisposable = CompositeDisposable()

    protected abstract fun loadInitialData(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>)
    protected abstract fun loadAditionalData(params: LoadParams<Int>, callback: LoadCallback<Int, T>)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        onDataSourceLoading.onFirstFetch()
        loadInitialData(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // since we are keeping data in memory, we will not need to load the data before it.
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        onDataSourceLoading.onDataLoading()
        loadAditionalData(params, callback)
    }

    protected fun submitInitialData(items: List<T>, params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        callback.onResult(items, 0, params.requestedLoadSize)
        if (!items.isEmpty()) {
            onDataSourceLoading.onFirstFetchEndWithData()
        } else {
            onDataSourceLoading.onFirstFetchEndWithoutData()
        }
    }

    protected fun submitData(items: List<T>, params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // If we reach the limit, then put value NULL as adjacent key to state that the list has ended
        // else, we will configure the next key to be fetched
        callback.onResult(items, if (items.isEmpty()) null else params.key + items.size)
        onDataSourceLoading.onDataLoadingEnd()
    }


    protected fun submitInitialError(error: Throwable) {
        onDataSourceLoading.onError()
        error.printStackTrace()
        // You can also have custom error handling with the provided Throwable
    }


    protected fun submitError(error: Throwable) {
        onDataSourceLoading.onError()
        error.printStackTrace()
        // You can also have custom error handling with the provided Throwable
    }
    //endregion

    override fun invalidate() {
        super.invalidate()
        compositeDisposable.dispose()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}
