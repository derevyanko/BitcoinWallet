package com.derevianko.bitcoinwallet.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.derevianko.bitcoinwallet.R
import com.derevianko.bitcoinwallet.presentation.util.theme.DarkGreen
import com.derevianko.domain.model.Transaction
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ItemTransaction(
    transaction: Transaction,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick(transaction.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(52.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                if (transaction.isDeposit) {
                    Text(
                        text = stringResource(id = R.string.deposit),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = DarkGreen
                    )
                } else {
                    Text(
                        text = stringResource(R.string.withdraw),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 24.sp,
                        color = Color.Red
                    )

                    Text(
                        text = transaction.category ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = transaction.value.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Text(
                    text = getDate(millis = transaction.date),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun getDate(millis: Long): String {
    val dateFormat = SimpleDateFormat(stringResource(R.string.transaction_time_format), Locale.getDefault())
    return dateFormat.format(millis)
}

@Composable
@Preview
private fun Preview() {
    ItemTransaction(
        transaction = Transaction(
            id = 1,
            value = 0.1f,
            isDeposit = false,
            category = "taxi",
            date = 10000
        ),
        onClick = {}
    )
}