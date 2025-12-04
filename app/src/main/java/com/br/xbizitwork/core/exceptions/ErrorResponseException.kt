package com.br.xbizitwork.core.exceptions

import com.br.xbizitwork.core.model.api.OperationErrorResponse

class ErrorResponseException(val error: OperationErrorResponse) : RuntimeException() {}