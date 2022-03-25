package com.example.kabinetzhitelya_kotlin.ui.`auth-mvvm`

//import android.view.View
//import androidx.lifecycle.ViewModel
//import com.example.kabinetzhitelya_kotlin.app.App
//import com.example.kabinetzhitelya_kotlin.domain.AuthInteractor
//import com.example.kabinetzhitelya_kotlin.ui.Screens
//import com.example.kabinetzhitelya_kotlin.ui.auth.AuthView
//import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.rxkotlin.addTo
//import io.reactivex.rxkotlin.subscribeBy
//import io.reactivex.schedulers.Schedulers
//import io.reactivex.subjects.BehaviorSubject
//import io.reactivex.subjects.PublishSubject

//class AuthMvvmViewModel: ViewModel() {
//
//    enum class State {
//        LOADING,
//        SUCCESS,
//        ERROR
//    }
//
//    val loadingState = BehaviorSubject.create<Int>()
//    val errorMessages = PublishSubject.create<String>()
//
//    private val compositeDisposable = CompositeDisposable()
//    private val authInteractor = AuthInteractor()
//    private val router = App.INSTANCE.router
//
//    fun login(username: String, password: String) = authInteractor.login(username, password)
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .doOnSubscribe {
//            loadingState.onNext(View.VISIBLE)
//        }
//        .subscribeBy(
//            onNext = {
//                router.newRootScreen(Screens.WebviewScreen(null))
//                loadingState.onNext(View.GONE)
//            },
//            onError = { error ->
//                if (error is HttpException) {
//                    val code = error.code()
//                    when (code) {
//                        400 -> errorMessages.onNext("Неверный логин или пароль")
//                    }
//                }
//                loadingState.onNext(View.GONE)
//            }
//        ).addTo(compositeDisposable)
//
//
//    override fun onCleared() {
//        compositeDisposable.clear()
//        super.onCleared()
//    }
//
//}