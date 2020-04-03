package com.sample.pagingsample

import android.content.Context
import com.sample.pagingsample.api.GithubService
import com.sample.pagingsample.data.GithubRepository
import com.sample.pagingsample.db.GithubLocalCache
import com.sample.pagingsample.db.RepoDatabase
import com.sample.pagingsample.ui.ViewModelFactory
import java.util.concurrent.Executors

object Injector {
    private fun provideCache(context: Context): GithubLocalCache {
        val database = RepoDatabase.getInstance(context)
        return GithubLocalCache(database.reposDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(GithubService.create(), provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        return ViewModelFactory(provideGithubRepository(context))
    }

}