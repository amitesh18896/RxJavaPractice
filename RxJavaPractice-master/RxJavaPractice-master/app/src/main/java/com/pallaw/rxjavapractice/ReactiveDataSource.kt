package com.pallaw.rxjavapractice

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single


class ReactiveDataSource {
    fun getObservableList(): Observable<String> {
        val createdList = arrayListOf<String>("D", "E", "F")
        return Observable.fromIterable(createdList)
    }

    fun getSingleList(): Single<List<String>> {
        val createdList = arrayListOf("G", "H", "I")
        return Single.just(createdList)
    }

    fun getMaybeString(): Maybe<String> {
        return Maybe.just("S")
    }

    fun getMaybeEmpty(): Maybe<String> {
        return Maybe.empty()
    }

    fun getCompletable(): Completable {
        return Completable.complete()
    }
}