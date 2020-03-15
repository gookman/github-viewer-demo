package dk.bluebox.demo.githubviewer.common.network

import dk.bluebox.demo.githubviewer.common.network.ServiceConstants
import dk.bluebox.demo.githubviewer.common.network.entities.PullRequestEntity
import dk.bluebox.demo.githubviewer.common.network.entities.SearchResponseEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET(ServiceConstants.REPOSITORIES_PATH)
    fun getRepositories(): Single<SearchResponseEntity>

    @GET(ServiceConstants.PULL_REQUESTS_PATH)
    fun getPullRequests(@Path("owner") owner: String, @Path("name") name: String): Single<List<PullRequestEntity>>
}