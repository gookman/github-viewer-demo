package dk.bluebox.demo.githubviewer.common.network

object ServiceConstants {
    const val REPOSITORIES_PATH = "search/repositories?q=stars:>0&sort=stars&order=desc"
    const val PULL_REQUESTS_PATH = "repos/{owner}/{name}/pulls?sort=updated"
}
