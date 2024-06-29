package com.derevianko.data.mappers

import com.derevianko.data.local.entity.TransactionDbEntity
import com.derevianko.domain.model.Transaction

class TransactionsMapper {

    object FromDbToDomain {

        operator fun invoke(from: TransactionDbEntity) = Transaction(
            id = from.id,
            value = from.value,
            isDeposit = from.isDeposit,
            category = from.category,
            date = from.date
        )
    }

    object FromDomainToDb {

        operator fun invoke(from: Transaction) = TransactionDbEntity(
            value = from.value,
            isDeposit = from.isDeposit,
            category = from.category,
            date = from.date
        )
    }
}