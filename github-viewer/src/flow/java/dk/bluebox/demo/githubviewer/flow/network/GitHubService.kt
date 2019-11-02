package dk.bluebox.demo.githubviewer.flow.network

import dk.bluebox.demo.githubviewer.common.network.ServiceConstants
import dk.bluebox.demo.githubviewer.common.network.entities.PullRequestEntity
import dk.bluebox.demo.githubviewer.common.network.entities.SearchResponseEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET(ServiceConstants.REPOSITORIES_PATH)
    suspend fun getRepositories(): SearchResponseEntity

    @GET(ServiceConstants.PULL_REQUESTS_PATH)
    suspend fun getPullRequests(@Path("owner") owner: String, @Path("name") name: String): List<PullRequestEntity>
}