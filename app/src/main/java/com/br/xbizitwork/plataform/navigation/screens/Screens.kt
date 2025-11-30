package com.br.xbizitwork.plataform.navigation.screens

import kotlinx.serialization.Serializable

@Serializable
sealed interface Graphs{
    @Serializable
    data object AuthGraph: Graphs

    @Serializable
    data object HomeGraph: Graphs
}

@Serializable
sealed interface AuthScreens{
    @Serializable
    data object LoginScreen: AuthScreens

    @Serializable
    data object RegisterScreen: AuthScreens
}

@Serializable
sealed interface HomeScreens{
    @Serializable
    data object HomeScreen: HomeScreens

    @Serializable
    data class SignUpScreen(val userId: String? = ""): HomeScreens

    @Serializable
    data object OptionScreen: HomeScreens

    @Serializable
    data object ProfileScreen: HomeScreens

    @Serializable
    data object ReserveScreen: HomeScreens

    @Serializable
    data object PromoteYourWorkScreen: HomeScreens

    @Serializable
    data object SearchScreen: HomeScreens

    @Serializable
    data object FavoriteScreen: HomeScreens

    @Serializable
    data object FinanceScreen: HomeScreens

    @Serializable
    data object DashboardScreen: HomeScreens

    @Serializable
    data object ChangePasswordScreen: HomeScreens
}