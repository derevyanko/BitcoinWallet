package com.derevianko.bitcoinwallet.presentation.home

import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.derevianko.bitcoinwallet.R
import com.derevianko.bitcoinwallet.presentation.main.MainViewModel
import com.derevianko.bitcoinwallet.presentation.home.components.DepositPopup
import com.derevianko.bitcoinwallet.presentation.home.components.ItemTransaction
import com.derevianko.bitcoinwallet.presentation.util.components.ErrorMessage
import com.derevianko.bitcoinwallet.presentation.util.components.LoadingNextPageItem
import com.derevianko.bitcoinwallet.presentation.util.components.PageLoader
import com.derevianko.bitcoinwallet.presentation.util.route.NavigationItem
import com.derevianko.domain.model.CurrentPrice
import com.derevianko.domain.model.Transaction
import com.derevianko.domain.base.State
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val transactionsPagingItems = viewModel.transactions.collectAsLazyPagingItems()
    val bitcoinPriceState by viewModel.bitcoinPrice.collectAsState()
    val balance = viewModel.balance.collectAsState()

    var isPopupVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar(bitcoinPriceState) }
    ) {
        Content(
            balance = balance,
            transactionsPagingItems = transactionsPagingItems,
            onClickDeposit = {
                isPopupVisible = true
            },
            onClickAddTransaction = {
                navController.navigate(NavigationItem.AddTransaction.route)
            },
            modifier = Modifier.padding(it)
        )
    }

    if (isPopupVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { isPopupVisible = false },
            contentAlignment = Alignment.Center
        ) {
            DepositPopup(
                onDepositClicked = { amount ->
                    viewModel.makeDeposit(amount)
                    isPopupVisible = false
                },
                onDismiss = { isPopupVisible = false }
            )
        }
    }
}

@Composable
private fun TopBar(
    bitcoinPriceState: State<CurrentPrice>,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .height(60.dp)
        .background(MaterialTheme.colorScheme.primary)
        .padding(horizontal = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
) {
    Text(
        text = stringResource(id = R.string.app_name),
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight.Bold
    )

    when (bitcoinPriceState) {
        is State.Success -> {
            val rate = bitcoinPriceState.data.rate
            val symbol = bitcoinPriceState.data.symbol
            val decodedSymbol = Html.fromHtml(symbol, Html.FROM_HTML_MODE_LEGACY).toString()

            Text(
                text = rate + decodedSymbol,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        else -> Text(
            text = "Unexpected",
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun Content(
    balance: androidx.compose.runtime.State<Float>,
    transactionsPagingItems: LazyPagingItems<Transaction>,
    modifier: Modifier = Modifier,
    onClickDeposit: () -> Unit = {},
    onClickAddTransaction: () -> Unit = {},
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.balance, balance.value),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )

        Button(
            onClick = { onClickDeposit() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .wrapContentWidth()
                .padding(vertical = 8.dp),
        ) {
            Text(
                text = stringResource(id = R.string.deposit).uppercase(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }

    Button(
        onClick = { onClickAddTransaction() },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.add_transaction),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }

    TransactionsPagingItems(
        transactionsPagingItems = transactionsPagingItems
    )
}

@Composable
private fun TransactionsPagingItems(
    transactionsPagingItems: LazyPagingItems<Transaction>,
    modifier: Modifier = Modifier
) = LazyColumn(
    modifier = modifier
        .padding(vertical = 4.dp)
) {
    items(
        count = transactionsPagingItems.itemCount,
        key = transactionsPagingItems.itemKey { it.id },
        contentType = transactionsPagingItems.itemContentType { "Transactions" }
    ) { index ->
        val transaction = transactionsPagingItems[index]

        if (transaction != null) {
            val isAnotherDay = if (index == 0) true
                else getDateDay(transaction.date) != getDateDay(transactionsPagingItems[index - 1]!!.date)

            if (isAnotherDay) {
                Text(
                    text = getTransactionDay(millis = transaction.date)
                )
            }

            ItemTransaction(
                transaction = transaction,
                onClick = {}
            )
        }
    }
    transactionsPagingItems.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
            }
            loadState.refresh is LoadState.Error -> {
                val error = transactionsPagingItems.loadState.refresh as LoadState.Error
                item {
                    ErrorMessage(
                        modifier = Modifier.fillParentMaxSize(),
                        message = error.error.localizedMessage!!,
                        onClickRetry = { retry() })
                }
            }
            loadState.append is LoadState.Loading -> {
                item { LoadingNextPageItem(modifier = Modifier) }
            }
            loadState.append is LoadState.Error -> {
                val error = transactionsPagingItems.loadState.append as LoadState.Error
                item {
                    ErrorMessage(
                        modifier = Modifier,
                        message = error.error.localizedMessage!!,
                        onClickRetry = { retry() })
                }
            }
        }
    }
}

@Composable
private fun getDateDay(millis: Long): Long {
    return millis / 86400000
}

@Composable
private fun getTransactionDay(millis: Long): String {
    val dateFormat = SimpleDateFormat(stringResource(R.string.transaction_day_of_the_month_format), Locale.getDefault())
    return dateFormat.format(millis)
}