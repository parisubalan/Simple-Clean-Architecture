package com.dev.cleanarchitecture.di

import android.content.Context
import com.dev.cleanarchitecture.common.Constants
import com.dev.cleanarchitecture.data.remote.ApiInterface
import com.dev.cleanarchitecture.data.remote.ResponseInterceptor
import com.dev.cleanarchitecture.data.repository.AuthenticationRepoImpl
import com.dev.cleanarchitecture.domain.repository.AuthenticationRepository
import com.dev.cleanarchitecture.preference.MySharedPreference
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMySharedPreference(@ApplicationContext context : Context) = MySharedPreference(context)

    @Singleton
    @Provides
    fun provideResponseInterceptor(@ApplicationContext context: Context,mySession: MySharedPreference) =
        ResponseInterceptor(context,mySession)

    @Singleton
    @Provides
    fun provideApiInterface(responseInterceptor: ResponseInterceptor): ApiInterface {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(responseInterceptor)
            .callTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthenticationRepository(apiInterface: ApiInterface): AuthenticationRepository {
        return AuthenticationRepoImpl(apiInterface)
    }

}