package com.rollcall.android.utils

import androidx.annotation.Keep


sealed class LoadState<out T>: ErrorGettable {

    object Loading: LoadState<Nothing>()
    class Loaded<T>(val value:T): LoadState<T>()
    class Error<T>(val e:Throwable): LoadState<T>()

    val isLoading get() = this is Loading
    override fun getErrorIfExists() = if(this is Error) e else null
    fun getValueOrNull():T? = if(this is Loaded<T>)value else null

}

@Keep
sealed class LoadingState: ErrorGettable {
    object Loading: LoadingState()
    object Loaded: LoadingState()
    class Error(val e:Throwable): LoadingState()

    public val isLoading get() = this is Loading
    override fun getErrorIfExists() = if (this is Error) e else null


}


@Keep
interface ErrorGettable{
    fun getErrorIfExists():Throwable?
}

