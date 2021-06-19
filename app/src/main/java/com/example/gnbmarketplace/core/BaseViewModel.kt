package com.example.gnbmarketplace.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    private var _failure: MutableLiveData<Failure> = MutableLiveData()

    val failure: LiveData<Failure>
        get() = _failure

    protected fun handleFailure(failure: Failure) {
        this._failure.value = failure
    }
}