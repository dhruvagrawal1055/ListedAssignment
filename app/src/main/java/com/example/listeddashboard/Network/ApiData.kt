package com.example.listeddashboard.Network
import com.example.listeddashboard.Model.ApiRes
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header


val url = "https://api.inopenapp.com/api/v1/"
//moshi is used to parse the data got from the api
val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
// initialising retrofit
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(url)
        .build()
    interface FetchData{

        @GET("dashboardNew")//annotation to tell retrofit to make a get request
        suspend fun getData(@Header("Authorization") token: String):ApiRes //making the function suspend so that we can call it according to our needs
    }

    object Api {
        val retrofitService: FetchData by lazy {
            retrofit.create(FetchData::class.java)
        }
    }
