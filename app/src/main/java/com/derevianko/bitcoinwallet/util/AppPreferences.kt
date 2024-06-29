package com.derevianko.bitcoinwallet.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.derevianko.bitcoinwallet.BuildConfig
import javax.inject.Inject

object AppPreferences {

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(
            BuildConfig.SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    fun clear() = sharedPreferences.edit().clear().apply()

    var balance: Float?
        get() = Key.PREFS_BALANCE.getFloat()
        set(value) = Key.PREFS_BALANCE.setFloat(value)

    var bitcoinRate: String?
        get() = Key.PREFS_BITCOIN_RATE.getString()
        set(value) = Key.PREFS_BITCOIN_RATE.setString(value)

    var bitcoinRateUpdatedTime: String?
        get() = Key.PREFS_BITCOIN_RATE_UPDATED_TIME.getString()
        set(value) = Key.PREFS_BITCOIN_RATE_UPDATED_TIME.setString(value)

    var bitcoinPriceCurrencySymbol: String?
        get() = Key.PREFS_BITCOIN_RATE_CURRENCY_SYMBOL.getString()
        set(value) = Key.PREFS_BITCOIN_RATE_CURRENCY_SYMBOL.setString(value)

    private enum class Key {
        PREFS_BALANCE,
        PREFS_BITCOIN_RATE,
        PREFS_BITCOIN_RATE_UPDATED_TIME,
        PREFS_BITCOIN_RATE_CURRENCY_SYMBOL;

        fun getFloat(): Float? = if (sharedPreferences.contains(name)) sharedPreferences.getFloat(name, 0f) else null
        fun getString(): String? = if (sharedPreferences.contains(name)) sharedPreferences.getString(name, "") else null

        fun setFloat(value: Float?) = value?.let { sharedPreferences.edit { putFloat(name, value) } } ?: remove()
        fun setString(value: String?) = value?.let { sharedPreferences.edit { putString(name, value) } } ?: remove()

        fun remove() = sharedPreferences.edit { remove(name) }
    }
}