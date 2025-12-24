package com.br.xbizitwork.core.util.extensions

fun String.containsNumber(): Boolean {
    val regex = Regex(".*\\d+.*")
    return regex.matches(this)
}

fun String.containsUpperCase(): Boolean {
    val regex = Regex(".*[A-Z]+.*")
    return regex.matches(this)
}

fun String.containsSpecialChar(): Boolean {
    val regex = Regex(".*[^A-Za-z\\d]+.*")
    return regex.matches(this)
}

fun String.getInitials(): String{
    val nameParts = this.split(" ")
    return if(nameParts.size >=2){
        "${nameParts[0].firstOrNull() ?: '?'}${nameParts[1].firstOrNull() ?: '?'}"
    }else if(nameParts.isNotEmpty() && nameParts[0].isNotEmpty()){
        "${nameParts[0].first()}"
    }else{
        "??"
    }
}