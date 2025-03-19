package com.faizzfanani.service_pcs.domain.usecase

import com.faizzfanani.core.base.BaseUseCase
import com.faizzfanani.core.domain.usecase.BaseUseCaseImpl
import com.faizzfanani.core.utils.Result
import com.faizzfanani.service_pcs.domain.model.PcsUser
import com.faizzfanani.service_pcs.domain.repository.PcsRepository
import javax.inject.Inject

class GetPcsUserDetailUseCase @Inject constructor(
    baseUseCaseImpl: BaseUseCaseImpl,
    private val pcsRepository: PcsRepository,
): BaseUseCase(baseUseCaseImpl) {

    suspend fun execute(id: String, output: Output){
        allowExecute {
            output.loading.invoke(true)
            pcsRepository.getPcsUserDetail(id)
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
        val success: ((PcsUser) -> Unit),
        val error: ((String) -> Unit)
    )
}