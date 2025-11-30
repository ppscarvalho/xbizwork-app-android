package com.br.xbizitwork.core.util.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Base class para Casos de Uso que retornam um [Flow] encapsulado em um [UiState].
 *
 * Essa abstração padroniza o comportamento de:
 * - Emissão automática de estado de carregamento (Loading)
 * - Execução da lógica de negócio
 * - Tratamento de erros (Error)
 * - Emissão do resultado de sucesso (Success)
 *
 * Deve ser utilizada quando o Caso de Uso representa uma operação que:
 * - Dispara uma ação única
 * - Precisa informar estado de carregamento
 * - Precisa tratar exceções de forma centralizada
 *
 * Exemplo de uso:
 *
 * ```
 * class SignUpUseCase(
 *     private val repository: UserAuthRepository
 * ) : FlowUseCase<SignUpUseCase.Params, ApiResultModel>() {
 *
 *     override suspend fun executeTask(params: Params): UiState<ApiResultModel> {
 *         val result = repository.signUp(params.request)
 *         return UiState.Success(result)
 *     }
 * }
 * ```
 *
 * @param Parameters Tipo dos parâmetros de entrada do Caso de Uso.
 * @param Result Tipo do resultado em caso de sucesso.
 */
abstract class FlowUseCase<in Parameters, Result> {
    /**
     * Executa o Caso de Uso emitindo automaticamente os estados de UI.
     *
     * Fluxo:
     * 1. Emite [UiState.Loading]
     * 2. Executa a lógica definida em [executeTask]
     * 3. Emite o resultado retornado
     * 4. Em caso de exceção, emite [UiState.Error]
     *
     * @param parameters Parâmetros necessários para a execução do Caso de Uso.
     * @return [Flow] contendo estados de UI da operação.
     */
    operator fun invoke(parameters: Parameters): Flow<UiState<Result>> = flow {
        emit(UiState.Loading)
        emit(executeTask(parameters))
    }.catch { throwable ->
        emit(UiState.Error(throwable))
    }

    /**
     * Método responsável por implementar a regra de negócio do Caso de Uso.
     *
     * Aqui deve ser feita apenas a lógica principal da operação,
     * sem se preocupar com estados de Loading ou tratamento de exceções,
     * pois isso já é tratado na classe base.
     *
     * @param parameters Parâmetros recebidos para execução.
     * @return Um [UiState] representando o resultado da operação.
     */
    protected abstract suspend fun executeTask(parameters: Parameters): UiState<Result>
}

/**
 * Base class para Casos de Uso que já retornam um [Flow] pronto,
 * sem encapsular automaticamente em [UiState].
 *
 * Essa abstração deve ser usada quando:
 * - O Caso de Uso precisa expor um fluxo contínuo de dados
 * - O comportamento de Loading/Error já é tratado externamente
 * - O fluxo de dados não representa uma ação pontual
 *
 * Exemplos de uso:
 * - Observação de listas (stream de dados)
 * - Atualizações em tempo real
 * - Leituras contínuas do banco local
 *
 * Exemplo de uso:
 *
 * ```
 * class ObserveUsersUseCase(
 *     private val repository: UserRepository
 * ) : RawFlowUseCase<Unit, List<UserModel>>() {
 *
 *     override suspend fun executeTaskFlow(Unit): Flow<List<UserModel>> {
 *         return repository.observeUsers()
 *     }
 * }
 * ```
 *
 * @param Parameters Tipo dos parâmetros de entrada do Caso de Uso.
 * @param Result Tipo dos dados emitidos no fluxo.
 */
abstract class RawFlowUseCase<in Parameters, Result : Any> {

    /**
     * Executa o Caso de Uso retornando diretamente um [Flow].
     *
     * Nenhum estado de Loading ou tratamento de erro é aplicado automaticamente.
     * Essa responsabilidade fica para a camada que consumir o fluxo.
     *
     * @param parameters Parâmetros necessários para a execução.
     * @return [Flow] contendo os dados emitidos pela operação.
     */
    suspend operator fun invoke(parameters: Parameters): Flow<Result> = executeTaskFlow(parameters)

    /**
     * Implementação da lógica do fluxo contínuo.
     *
     * Deve retornar um [Flow] já configurado com as emissões necessárias.
     *
     * @param parameters Parâmetros recebidos para execução.
     * @return [Flow] com os dados da operação.
     */
    protected abstract suspend fun executeTaskFlow(parameters: Parameters): Flow<Result>
}
