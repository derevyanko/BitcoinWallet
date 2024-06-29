package com.derevianko.domain.usecase

import com.derevianko.domain.repository.TransactionsRepository
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    operator fun invoke() = transactionsRepository.getAll()
}