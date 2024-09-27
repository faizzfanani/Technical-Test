package com.faizzfanani.service_github.domain.usecase

import com.faizzfanani.core.base.BaseUseCase
import com.faizzfanani.core.domain.usecase.BaseUseCaseImpl
import com.faizzfanani.core.utils.Result
import com.faizzfanani.service_github.domain.model.GithubUser
import com.faizzfanani.service_github.domain.repository.GithubRepository
import javax.inject.Inject

class GetGithubUserUseCase @Inject constructor(
    baseUseCaseImpl: BaseUseCaseImpl,
    private val githubRepository: GithubRepository,
): BaseUseCase(baseUseCaseImpl) {

    suspend fun execute(username: String?, output: Output){
        allowExecute {
            output.loading.invoke(true)
            githubRepository.getGithubUser(username ?: "")
                .collect { result ->
                    when(result){
                        is Result.Success -> {
                            output.success.invoke(result.data)
                        }
                        is Result.Error -> {
                            output.error.invoke(result.message)
                        }
                    }
                    output.loading.invoke(false)
                }
        }
    }

    data class Output(
        val loading: ((Boolean) -> Unit),
        val success: ((List<GithubUser>) -> Unit),
        val error: ((String) -> Unit)
    )
}