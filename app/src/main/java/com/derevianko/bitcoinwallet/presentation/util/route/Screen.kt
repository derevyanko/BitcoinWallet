package com.derevianko.bitcoinwallet.presentation.util.route

enum class Screen {
    HOME,
    ADD_TRANSACTION,
}

sealed class NavigationItem(val route: String) {
    object Home: NavigationItem(Screen.HOME.name)
    object AddTransaction: NavigationItem(Screen.ADD_TRANSACTION.name)
}
