package ru.padev.vkclient.main.presentation

import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import ru.padev.vkclient.main.data.response.LaunchAppResponse

class LaunchAppMessageLoadingSubject {
    private val subject: BehaviorSubject<LaunchAppResponse> = BehaviorSubject.create()

    fun subscribe() = subject.subscribeOn(Schedulers.io())

    fun post(value: LaunchAppResponse) {
        subject.onNext(value)
    }
}