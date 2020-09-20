package ru.padev.vkclient.core.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.Callable

object RxUtils {
    @JvmStatic
    fun <T> mainThreadObservable(callable: Callable<T>): Observable<T> {
        return Observable.fromCallable(callable)
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}