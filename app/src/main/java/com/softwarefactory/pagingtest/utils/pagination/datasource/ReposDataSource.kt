package com.softwarefactory.pagingtest.utils.pagination.datasource

import android.annotation.SuppressLint
import com.softwarefactory.pagingtest.domain.managers.ReposManager
import com.softwarefactory.pagingtest.domain.models.Repos
import com.softwarefactory.pagingtest.utils.pagination.datasource._base.BaseDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ReposDataSource(var user : String) : BaseDataSource<Repos>() {
    private val manager : ReposManager = ReposManager()

    @SuppressLint("CheckResult")
    override fun loadInitialData(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Repos>) {
        // in the initial load, we will start at page 0, and retrieve the number of pages in the params.requestLoadSize
        manager.getListOfRepos(user, 0, params.requestedLoadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::addDisposable)
                .subscribe(
                        { items -> submitInitialData(items, params, callback) },
                        { error -> submitInitialError(error) }
                )
    }

    @SuppressLint("CheckResult")
    override fun loadAditionalData(params: LoadParams<Int>, callback: LoadCallback<Int, Repos>) {
        manager.getListOfRepos(user, params.key, params.requestedLoadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::addDisposable)
                .subscribe(
                        { items -> submitData(items, params, callback) },
                        { error -> submitError(error) }
                )
    }
}