package com.derevianko.bitcoinwallet.presentation.main

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.derevianko.bitcoinwallet.util.AppPreferences
import com.derevianko.domain.model.CurrentPrice
import com.derevianko.domain.model.Transaction
import com.derevianko.domain.usecase.AddTransactionUseCase
import com.derevianko.domain.usecase.GetAllTransactionsUseCase
import com.derevianko.domain.usecase.GetBitcoinPriceUseCase
import com.derevianko.domain.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coroutineContext: CoroutineContext,
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getBitcoinPriceUseCase: GetBitcoinPriceUseCase,
): ViewModel() {

    private val scope = CoroutineScope(coroutineContext)

    val balance: MutableStateFlow<Float> = MutableStateFlow(AppPreferences.balance ?: 0f)

    private val _bitcoinPrice: MutableStateFlow<State<CurrentPrice>> by lazy {
        val priceValue = AppPreferences.bitcoinRate ?: return@lazy MutableStateFlow(State.Idle)
        val updatedTime = AppPreferences.bitcoinRateUpdatedTime ?: return@lazy MutableStateFlow(
            State.Idle)
        val currencySymbol = AppPreferences.bitcoinPriceCurrencySymbol ?: return@lazy MutableStateFlow(
            State.Idle)

        MutableStateFlow(
            State.Success(
                CurrentPrice(
            time = updatedTime,
            rate = priceValue,
            symbol = currencySymbol
        )
            ))
    }

    val bitcoinPrice: StateFlow<State<CurrentPrice>> = _bitcoinPrice.asStateFlow()

    val transactions: Flow<PagingData<Transaction>> = getAllTransactionsUseCase()
        .cachedIn(scope)

    fun getBitcoinPrice() = scope.launch {
        val currentPrice = getBitcoinPriceUseCase(bitcoinPrice.value)
        _bitcoinPrice.value = currentPrice
        updateAppPreferences()
    }

    fun makeDeposit(value: Float) = scope.launch {
        val transaction = Transaction(
            id = 0,
            value = value,
            isDeposit = true,
            category = null,
            date = Date().time
        )
        addTransactionUseCase(transaction)

        balance.value = balance.value + value
        AppPreferences.balance = balance.value
    }

    fun addTransaction(value: Float, category: String) = scope.launch {
        val transaction = Transaction(
            id = 0,
            value = value,
            isDeposit = false,
            category = category,
            date = Date().time
        )

        val isAddedTransaction = addTransactionUseCase(transaction, balance.value)

        if (isAddedTransaction) {
            balance.value = balance.value - value
            AppPreferences.balance = balance.value
        }
    }

    private fun updateAppPreferences() = when (val value = bitcoinPrice.value) {
        is State.Success -> {
            AppPreferences.run {
                bitcoinRate = value.data.rate
                bitcoinPriceCurrencySymbol = value.data.symbol
                bitcoinRateUpdatedTime = value.data.time
            }
        }
        else -> {}
    }
}