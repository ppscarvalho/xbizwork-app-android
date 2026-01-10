package com.br.xbizitwork.ui.presentation.features.profile.state

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

// Máscara de CEP: 00000-000
class CepVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
        val out = buildString {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if (i == 4) append("-")
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 8) return offset + 1
                return out.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 5) return offset.coerceIn(0, text.text.length)
                return (offset - 1).coerceIn(0, text.text.length)
            }
        }
        return TransformedText(androidx.compose.ui.text.AnnotatedString(out), offsetMapping)
    }
}