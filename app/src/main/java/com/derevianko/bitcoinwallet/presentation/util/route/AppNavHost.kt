package com.derevianko.bitcoinwallet.presentation.util.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.derevianko.bitcoinwallet.presentation.addTransaction.AddTransactionScreen
import com.derevianko.bitcoinwallet.presentation.home.HomeScreen
import com.derevianko.bitcoinwallet.presentation.main.MainViewModel

@Composable
fun AppNavHost(
    mainViewModel: MainViewModel,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route,
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = mainViewModel,
            )
        }

        composable(NavigationItem.AddTransaction.route) {
            AddTransactionScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
    }
}