package com.br.xbizitwork.ui.presentation.features.profile.state

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

// Máscara de Telefone: (00) 00000-0000
class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text
        val out = buildString {
            for (i in trimmed.indices) {
                if (i == 0) append("(")
                append(trimmed[i])
                if (i == 1) append(") ")
                if (i == 6) append("-")
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return 0
                if (offset <= 2) return offset + 1 // (XX
                if (offset <= 7) return offset + 3 // (XX) XXXXX
                if (offset <= 11) return offset + 4 // (XX) XXXXX-XXXX
                return out.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 1) return 0
                if (offset <= 4) return (offset - 1).coerceIn(0, text.text.length)
                if (offset <= 10) return (offset - 3).coerceIn(0, text.text.length)
                if (offset <= 15) return (offset - 4).coerceIn(0, text.text.length)
                return text.text.length
            }
        }
        return TransformedText(androidx.compose.ui.text.AnnotatedString(out), offsetMapping)
    }
}