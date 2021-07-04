package com.example.samplerakuma.network.service

import com.example.samplerakuma.network.response.Brands
import io.reactivex.rxjava3.core.Single

import retrofit2.http.GET

interface RakumaService {

    @GET("/api/brands")
    fun getBrandsName(): Single<List<Brands>>

    class RakumaServiceImpl() : RakumaService {
        fun createRakumaService() {}
        override fun getBrandsName(): Single<List<Brands>> {
            val brandsData = listOf(Brands("test"), Brands("test2"))
            return Single.just(brandsData)
        }
    }
}
