package com.sample.pagingsample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sample.pagingsample.data.GithubRepository
import com.sample.pagingsample.model.Repo
import com.sample.pagingsample.model.RepoSearchResult

class SearchRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {
    private val _queryLiveData = MutableLiveData<String>()
    private val _repoResult: LiveData<RepoSearchResult> = Transformations.map(_queryLiveData) {
        repository.search(it)
    }

    val repos: LiveData<PagedList<Repo>> = Transformations.switchMap(_repoResult) {
        it.data
    }

    val networkErrors: LiveData<String> = Transformations.switchMap(_repoResult) {
        it.networkErrors
    }

    fun searchRepo(query: String) {
        _queryLiveData.postValue(query)
    }

    fun lastQueryValue(): String? = _queryLiveData.value
}