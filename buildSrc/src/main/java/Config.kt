object Config {
    // sdk config(s)
    const val minSdk = 24
    const val targetSdk = 34

    // namespace(s)
    const val applicationId = "id.faizzfanani.technical_test"
    const val baseNameSpace = "id.faizzfanani.technical_test"
    const val coreNamespace = "${baseNameSpace}.core"
    const val coreUINamespace = "${baseNameSpace}.core_ui"
    const val navigationNamespace = "${baseNameSpace}.navigation"
    const val coreStorageNamespace = "${baseNameSpace}.core_storage"
    const val serviceGithubNamespace = "${baseNameSpace}.service_github"
    const val featureGithubNamespace = "${baseNameSpace}.feature_github_user"

    // base url(s)
    const val githubBaseUrl = "https://api.github.com/"

    // module(s)
    const val coreModule = ":core"
    const val coreUIModule = ":core-ui"
    const val coreStorageModule = ":core-storage"
    const val navigationModule = ":navigation"
    const val serviceGithubModule = ":service:github"
    const val featureGithubModule = ":feature:github_user"
}