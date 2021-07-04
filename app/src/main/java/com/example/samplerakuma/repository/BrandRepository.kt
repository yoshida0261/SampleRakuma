package com.example.samplerakuma.repository

import com.example.samplerakuma.network.response.Brands
import com.example.samplerakuma.network.service.RakumaService
//import io.reactivex.Single
import io.reactivex.rxjava3.core.Single
//import io.reactivex.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Schedulers

interface BrandRepository {
    companion object {
        private var instance: BrandRepository? = null

        fun getInstance(): BrandRepository =
            instance ?: BrandRepositoryImpl()
    }

    fun fetchBrands(): Single<List<Brands>>

    fun fetchFavoriteBrand()

    private class BrandRepositoryImpl() : BrandRepository {
        val service = RakumaService.RakumaServiceImpl()
        override fun fetchBrands(): Single<List<Brands>> {
            return service.getBrandsName().subscribeOn(Schedulers.io())
        }

        override fun fetchFavoriteBrand() {
            TODO("Not yet implemented")
        }
    }
}


