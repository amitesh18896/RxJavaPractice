package com.pallaw.rxjavapractice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val reactiveDataSource: ReactiveDataSource by lazy { ReactiveDataSource() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            testCompletable()
        }
    }

    private fun testCompletable() {
        reactiveDataSource.getCompletable().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {// 2. when process finished
                    Log.d("Completable ", "onComplete, Thread: ${Thread.currentThread().name}")
                }

                override fun onSubscribe(d: Disposable) {// 1. when attached to the subscriber
                    Log.d("Completable ", "onSubscribe, Thread: ${Thread.currentThread().name}")
                }

                override fun onError(e: Throwable) {// will only be called if there is some error in the process
                    Log.d("Completable ", "onError, Thread: ${Thread.currentThread().name}")
                }

            })
    }

    private fun testMaybe() {
        reactiveDataSource.getMaybeEmpty().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MaybeObserver<String> {
                override fun onSuccess(t: String) {// only when something to return
                    Log.d("Maybe empty", "onSuccess, Thread: ${Thread.currentThread().name}")
                }

                override fun onComplete() {// 2. only when nothing to return
                    Log.d("Maybe empty", "onComplete, Thread: ${Thread.currentThread().name}")
                }

                override fun onSubscribe(d: Disposable) {// 1. when attached to the subscriber
                    Log.d("Maybe empty", "onSubscribe, Thread: ${Thread.currentThread().name}")
                }

                override fun onError(e: Throwable) {// only when error in conversion of item
                    Log.d("Maybe empty", "onError, Thread: ${Thread.currentThread().name}")
                }

            })

        reactiveDataSource.getMaybeString().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MaybeObserver<String> {
                override fun onSuccess(t: String) {// 2. only when nothing to return
                    Log.d("Maybe String", "onSuccess, Thread: ${Thread.currentThread().name}")
                }

                override fun onComplete() {// will not be called because there would be a data emittion so the on success will be called
                    Log.d("Maybe String", "onComplete, Thread: ${Thread.currentThread().name}")
                }

                override fun onSubscribe(d: Disposable) {// 1. when attached to the subscriber
                    Log.d("Maybe String", "onSubscribe, Thread: ${Thread.currentThread().name}")
                }

                override fun onError(e: Throwable) {// only when error in conversion of item
                    Log.d("Maybe String", "onError, Thread: ${Thread.currentThread().name}")
                }

            })
    }

    private fun testSingles() {
        reactiveDataSource.getSingleList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<String>> {
                override fun onSuccess(t: List<String>) {// 2. when data is returned
                    Log.d("Single list", "onSuccess, Thread: ${Thread.currentThread().name}")
                    for (oneString in t) {
                        Log.d("Single list", "item : $oneString")
                    }
                }

                override fun onSubscribe(d: Disposable) {// 1. when attached to the subscriber
                    Log.d("Single list", "onSubscribe, Thread: ${Thread.currentThread().name}")
                }

                override fun onError(e: Throwable) {// only when error in conversion of item
                    Log.d("Single list", "onError, Thread: ${Thread.currentThread().name}")
                }

            })
    }

    private fun testObservables() {
        reactiveDataSource.getObservableList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onComplete() {// 3. when all items are emitted
                    Log.d("Observable list", "onComplete, Thread: ${Thread.currentThread().name}")
                }

                override fun onSubscribe(d: Disposable) {// 1. when attached to the subscriber
                    Log.d("Observable list", "onSubscribe, Thread: ${Thread.currentThread().name}")
                }

                override fun onNext(t: String) {// 2. for each item emitted
                    Log.d("Observable list", "onNext $t, Thread: ${Thread.currentThread().name}")
                }

                override fun onError(e: Throwable) {// only when error in conversion of item
                    Log.d(
                        "Observable list",
                        "onError ${e.message}, Thread: ${Thread.currentThread().name}"
                    )
                }
            })
    }
}
