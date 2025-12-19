package com.br.xbizitwork.data.remote.user.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.remote.user.dtos.responses.UserResponse
import com.br.xbizitwork.domain.model.user.UserModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Mapper para converter UserResponse em UserModel
 * ATUALIZADO: Com logs padronizados e valores default
 */
@RequiresApi(Build.VERSION_CODES.O)
fun UserResponse.toModel(): UserModel {
    // 🔍 LOG: Dados recebidos da API
    logInfo("USER_MAPPER", "Convertendo UserResponse: id=$id, name=$name")
    logInfo("USER_MAPPER", "Dados: cpf=$cpf, gender=$gender, mobilePhone=$mobilePhone, city=$city, state=$state")

    // ⚠️ VALIDAÇÃO: name é obrigatório!
    if (name.isBlank()) {
        logInfo("USER_MAPPER", "ERRO: name está vazio!")
        throw IllegalArgumentException("name não pode estar vazio")
    }

    return UserModel(
        id = id,
        authId = null, // API não retorna authId
        name = name,
        cpf = cpf,
        dateOfBirth = dateOfBirth?.let {
            try {
                LocalDate.parse(it, DateTimeFormatter.ISO_DATE_TIME)
            } catch (_: Exception) {
                logInfo("USER_MAPPER", "Erro ao parsear dateOfBirth: $it")
                null
            }
        },
        gender = gender,
        zipCode = zipCode,
        address = address,
        number = number,
        neighborhood = neighborhood,
        city = city,
        state = state,
        mobilePhone = mobilePhone,
        status = false, // ✅ Default false (API não retorna)
        registration = false, // ✅ Default false (API não retorna)
        latitude = latitude,
        longitude = longitude
    ).also {
        logInfo("USER_MAPPER", "UserModel criado: id=${it.id}, name=${it.name}")
    }
}

