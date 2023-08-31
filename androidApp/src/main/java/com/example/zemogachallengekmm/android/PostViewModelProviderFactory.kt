package com.example.zemogachallengekmm.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zemogachallengekmm.db.DatabaseDriverFactory
import com.example.zemogachallengekmm.viewmodel.PostViewModel

class PostViewModelProviderFactory constructor(private val databaseDriverFactory: DatabaseDriverFactory): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            PostViewModel(databaseDriverFactory) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}