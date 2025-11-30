package com.br.xbizitwork.core.domain.exceptions

import com.br.xbizitwork.core.data.remote.common.response.OperationErrorResponse

class ErrorResponseException(val error: OperationErrorResponse) : RuntimeException() {}