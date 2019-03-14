package com.softwarefactory.pagingtest.domain.models

data class Repos(
        var name : String?,
        var description : String?,
        var language : String?,
        var url : String?,
        var startCount : Int?,
        var watchersCount : Int?
) {
    constructor() : this(null, null, null, null, null, null)
}