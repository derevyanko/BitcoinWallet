package com.derevianko.domain.usecase

import com.derevianko.domain.model.Transaction
import com.derevianko.domain.repository.TransactionsRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    operator fun invoke(transaction: Transaction, balance: Float = 0f): Boolean {
        if (!transaction.isDeposit && transaction.value > balance) return false
        transactionsRepository.addTransaction(transaction)

        return true
    }
}