package com.softwarefactory.pagingtest.data.services

import com.softwarefactory.pagingtest.data.endpoints.UsersApi
import com.softwarefactory.pagingtest.data.models.ReposDto
import com.softwarefactory.pagingtest.utils.networking.NetworkTools
import io.reactivex.Single
import retrofit2.Response

class ReposService {
    var api : UsersApi = NetworkTools.retrofit.create(UsersApi::class.java)

    fun getReposForUser(user : String, page : Int, perPage : Int) : Single<Response<List<ReposDto>>> {
        return api.getRepos(user, page, perPage)
    }
}