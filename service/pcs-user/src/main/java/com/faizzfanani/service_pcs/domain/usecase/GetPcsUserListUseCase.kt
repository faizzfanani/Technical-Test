package com.faizzfanani.service_pcs.domain.usecase

import com.faizzfanani.core.base.BaseUseCase
import com.faizzfanani.core.domain.usecase.BaseUseCaseImpl
import com.faizzfanani.core.utils.Result
import com.faizzfanani.service_pcs.domain.model.PcsUser
import com.faizzfanani.service_pcs.domain.repository.PcsRepository
import javax.inject.Inject

class GetPcsUserListUseCase @Inject constructor(
    baseUseCaseImpl: BaseUseCaseImpl,
    private val pcsRepository: PcsRepository,
): BaseUseCase(baseUseCaseImpl) {

    suspend fun execute(output: Output){
        allowExecute {
            output.loading.invoke(true)
            pcsRepository.getPcsUserList()
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
        val success: ((List<PcsUser>) -> Unit),
        val error: ((String) -> Unit)
    )
}