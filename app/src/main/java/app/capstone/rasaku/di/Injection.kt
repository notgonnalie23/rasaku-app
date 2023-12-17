package app.capstone.rasaku.di

import android.content.Context
import app.capstone.rasaku.data.RasakuDatabase
import app.capstone.rasaku.data.Repository
import app.capstone.rasaku.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = RasakuDatabase.getInstance(context)
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(database, apiService)
    }
}