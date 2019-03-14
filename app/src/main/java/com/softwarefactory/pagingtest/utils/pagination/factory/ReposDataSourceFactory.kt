package com.softwarefactory.pagingtest.utils.pagination.factory

import android.arch.paging.DataSource
import com.softwarefactory.pagingtest.domain.models.Repos
import com.softwarefactory.pagingtest.utils.pagination.datasource.ReposDataSource
import com.softwarefactory.pagingtest.utils.pagination.datasource._base.OnDataSourceLoading

/**
 * Factory class that handles the creation of DataSources
 */
class ReposDataSourceFactory(var loading: OnDataSourceLoading,
                             var user: String?) : DataSource.Factory<Int, Repos>() {
    lateinit var source : ReposDataSource

    override fun create(): DataSource<Int, Repos>? {
        // invalidate the previous data source, if available
        if (::source.isInitialized && source != null) source.invalidate()
        // if we have a user, then create a data source
        if (user != null) {
            source = ReposDataSource(user!!)
            source.onDataSourceLoading = loading
            return source
        }
        return null
    }



}