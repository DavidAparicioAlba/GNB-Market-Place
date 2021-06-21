package com.example.gnbmarketplace.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gnbmarketplace.core.BaseViewModel
import com.example.gnbmarketplace.core.ScreenState
import com.example.gnbmarketplace.data.cache.SharedPreferencesManager
import com.example.gnbmarketplace.domain.uc.BaseResult
import com.example.gnbmarketplace.domain.uc.ObtainProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
        private val obtainProducts: ObtainProducts,
        private val sharedPreferencesManager: SharedPreferencesManager): BaseViewModel() {

    private val _state: MutableLiveData<ScreenState<MainActivityScreenState>> = MutableLiveData()
    val state: LiveData<ScreenState<MainActivityScreenState>>
        get()= _state

    init {
        obtainProducts()
    }

    private fun setLoading(){
        this._state.value = ScreenState.Render(MainActivityScreenState.IsLoading(true))
    }

    private fun hideLoading(){
        _state.value = ScreenState.Render(MainActivityScreenState.IsLoading(false))
    }

    private fun showToast(message: String){
        _state.value = ScreenState.Render(MainActivityScreenState.ShowToast(message))
    }

    fun obtainProducts() {
        viewModelScope.launch {
            obtainProducts.execute()
                    .onStart {
                        setLoading()
                    }
                    .catch { exception ->
                        hideLoading()
                        showToast(exception.message.toString())
                    }
                    .collect { baseResult ->
                        hideLoading()
                        when(baseResult){
                            is BaseResult.Error -> _state.value = ScreenState.Render(MainActivityScreenState.ErrorShowProducts(baseResult.rawResponse))
                            is BaseResult.Success -> _state.value = ScreenState.Render(MainActivityScreenState.ShowProducts(baseResult.data))
                        }
                    }
        }
    }

}