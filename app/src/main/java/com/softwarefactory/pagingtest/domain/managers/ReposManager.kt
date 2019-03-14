package com.softwarefactory.pagingtest.domain.managers

import com.softwarefactory.pagingtest.data.services.ReposService
import com.softwarefactory.pagingtest.domain.mappers.ReposMapper
import com.softwarefactory.pagingtest.domain.models.Repos
import io.reactivex.Single

class ReposManager {
    var reposService : ReposService = ReposService()

    fun getListOfRepos(user : String, page : Int, perPage : Int) : Single<List<Repos>> {
        return reposService.getReposForUser(user, page, perPage)
                // By calling `onErrorResumeNext` we could apply our own error handling function
                .onErrorResumeNext { throwable -> Single.error(throwable)}
            // Since we are using Retrofit's Response, we will need to parse it and check
                // if the response was successful or not
                .flatMap { response ->
                    if (!response.isSuccessful) {
                        Single.error(Throwable(response.code().toString()))
                    } else Single.just(response)
                }
                // If the response is successful, we retrieve its body
                .map {response -> response.body()}
                // Map the items to domain so that the presentation layer can handle it
                .map { list -> ReposMapper.Instance.mapList(list) }
    }
}