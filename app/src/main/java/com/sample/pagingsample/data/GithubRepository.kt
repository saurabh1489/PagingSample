package com.sample.pagingsample.data

import android.util.Log
import androidx.paging.LivePagedListBuilder
import com.sample.pagingsample.api.GithubService
import com.sample.pagingsample.api.RepoSearchResponse
import com.sample.pagingsample.db.GithubLocalCache
import com.sample.pagingsample.model.RepoSearchResult

class GithubRepository(
    private val service: GithubService,
    private val localCache: GithubLocalCache
) {

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
        private const val TAG = "GithubRepository"
    }

    fun search(query: String): RepoSearchResult {
        Log.d(TAG, "New query: $query")
        val dataSourceFactory = localCache.reposByName(query)
        val boundaryCallback = RepoBoundaryCallback(query, service, localCache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return RepoSearchResult(data, networkErrors)
    }
}