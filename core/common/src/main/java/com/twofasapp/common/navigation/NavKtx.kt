package com.twofasapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

@Composable
fun withOwner(viewModelStoreOwner: ViewModelStoreOwner?, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalViewModelStoreOwner provides viewModelStoreOwner!!
    ) {
        content()
    }
}

fun NamedNavArgument.withDefault(any: Any?): NamedNavArgument {
    return navArgument(this.name) { type = this@withDefault.argument.type; defaultValue = any }
}

fun <T> SavedStateHandle.getOrThrow(key: String): T {
    return get<T>(key) ?: throw IllegalNavArgException(key)
}