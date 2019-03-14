package com.softwarefactory.pagingtest.presentation.ui

import com.softwarefactory.pagingtest.domain.models.Repos
import com.softwarefactory.pagingtest.presentation.ui.base.BasePaginationViewModel
import com.softwarefactory.pagingtest.utils.pagination.factory.ReposDataSourceFactory

class MainViewModel : BasePaginationViewModel<ReposDataSourceFactory, Repos>() {
    init {
        dataSourceFactory = ReposDataSourceFactory(getListener(), null)
    }

    override fun getPageSize(): Int = 3

    /**
     * Handles a new user search
     */
    fun newSearch(user : String) {
        dataSourceFactory.user = user
        clearData()
    }
}