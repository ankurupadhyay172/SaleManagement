package com.ankurupadhyay.salemanagement.utils

import com.rollcall.android.utils.LoadState
import kotlinx.coroutines.flow.*


fun <T> Flow<T>.toLoadingState():Flow<LoadState<T>> {
    return map<T, LoadState<T>>{ LoadState.Loaded(it) }
        .onStart {
            @Suppress("UNCHECKED_CAST")
            emit(LoadState.Loading as LoadState<T>)
        }.onEach { LoadState.Loading }
        .catch { e->
            emit(LoadState.Error<T>(e))
        }
}