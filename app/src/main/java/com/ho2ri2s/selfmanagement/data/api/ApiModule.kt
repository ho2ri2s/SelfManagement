package com.ho2ri2s.selfmanagement.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(HttpLoggingInterceptor().apply {
        // TODO: 機密情報漏らさないようにする
        level = HttpLoggingInterceptor.Level.BODY
      })
      .addInterceptor(authInterceptor)
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    // TODO: kotlin serializationに書き換える
    val baseUrl = "http://10.0.2.2:8080"
    val moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
    return Retrofit.Builder()
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .baseUrl(baseUrl)
      .client(okHttpClient)
      .build()
  }

  @Provides
  @Singleton
  fun provideSelfManagementApi(retrofit: Retrofit): SelfManagementApi {
    return SelfManagementApi(retrofit)
  }
}