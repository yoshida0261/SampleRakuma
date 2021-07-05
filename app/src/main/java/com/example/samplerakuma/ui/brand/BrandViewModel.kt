package com.example.samplerakuma.ui.brand

import android.app.Application
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.samplerakuma.network.response.Brands
import com.example.samplerakuma.repository.BrandRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.io.PrintWriter
import java.io.StringWriter

class BrandViewModel(
    application: Application,
    private val repository: BrandRepository
) : AndroidViewModel(application) {
    private val disposables = CompositeDisposable()
    val isLoading = ObservableField(false)
    val brandsAdapterItems = ObservableArrayList<Brands>()

    init {
        fetchBrands()
    }

    @VisibleForTesting
    fun fetchBrands() {
        disposables.add(repository.fetchBrands()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.set(true)
            }
            .doFinally {
                isLoading.set(false)
            }.subscribe({ brands ->
                brandsAdapterItems.clear()
                brandsAdapterItems.addAll(brands)
            }, {
                val sw = StringWriter()
                val pw = PrintWriter(sw)
                it.printStackTrace(pw)
                Log.e("error", sw.toString())
            })
        )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BrandViewModel(
                application,
                BrandRepository.getInstance()
            ) as T
        }
    }
}


