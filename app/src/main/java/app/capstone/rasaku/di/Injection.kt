package app.capstone.rasaku.di

import android.content.Context
import app.capstone.rasaku.data.RasakuDatabase
import app.capstone.rasaku.data.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = RasakuDatabase.getInstance(context)
        return Repository.getInstance(database)
    }
}