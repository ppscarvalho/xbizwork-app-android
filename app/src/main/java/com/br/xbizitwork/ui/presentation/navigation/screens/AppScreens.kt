package com.br.xbizitwork.ui.presentation.navigation.screens

import kotlinx.serialization.Serializable

@Serializable
sealed interface Graphs{
    @Serializable
    data object AuthGraphs: Graphs

    @Serializable
    data object HomeGraphs: Graphs

    @Serializable
    data object MenuGraphs: Graphs
}

@Serializable
sealed interface AuthScreens{
    @Serializable
    data object SignInScreen: AuthScreens

    @Serializable
    data object SignUpScreen: AuthScreens
}

@Serializable
sealed interface HomeScreens{
    @Serializable
    data object HomeScreen: HomeScreens

    @Serializable
    data object ProfileScreen: HomeScreens

    @Serializable
    data object EditProfileScreen: HomeScreens

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
}

@Serializable
sealed interface MenuScreens{
    @Serializable
    data object MenuScreen: MenuScreens

    @Serializable
    data object FinancialScreen: MenuScreens

    @Serializable
    data object CreateScheduleScreen: MenuScreens

    @Serializable
    data object ListSchedulesScreen: MenuScreens

    @Serializable
    data object ProfessionalAgendaScreen: MenuScreens

    @Serializable
    data object ChangePasswordScreen: HomeScreens
}