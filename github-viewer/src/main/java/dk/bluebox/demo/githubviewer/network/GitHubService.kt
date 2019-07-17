package dk.bluebox.demo.githubviewer.network

import dk.bluebox.demo.githubviewer.network.entities.PullRequestEntity
import dk.bluebox.demo.githubviewer.network.entities.SearchResponseEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("search/repositories?q=stars:>0&sort=stars&order=desc")
    fun getRepositories(): Single<SearchResponseEntity>

    @GET("repos/{owner}/{name}/pulls?sort=updated")
    fun getPullRequests(@Path("owner") owner: String, @Path("name") name: String): Single<List<PullRequestEntity>>
}