package com.example.zemogachallengekmm.viewmodel

import com.example.zemogachallengekmm.db.DatabaseDriverFactory
import com.example.zemogachallengekmm.model.Post
import com.example.zemogachallengekmm.usecase.DeletePostsUseCase
import com.example.zemogachallengekmm.usecase.GetPostsUseCase
import com.example.zemogachallengekmm.utils.Resource
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class PostViewModel(
    databaseDriverFactory: DatabaseDriverFactory
): KMMViewModel()  {

    private val _uiState = MutableStateFlow(viewModelScope, PostUiState())
    @NativeCoroutinesState
    val uiState = _uiState.asStateFlow()

    private val getPostsUseCase = GetPostsUseCase(databaseDriverFactory)
    private val deletePostsUseCase = DeletePostsUseCase(databaseDriverFactory)

    fun getPostsFromDB() {
        getPosts(isCached = true)
    }

    fun getPostsFromApi() {
        getPosts(isCached = false)
    }

    private fun getPosts(isCached: Boolean) {
        viewModelScope.coroutineScope.launch {
            getPostsUseCase.invoke(isCached = isCached).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _uiState.value = PostUiState(
                            isLoading = false,
                            posts = result.data.orEmpty()
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = PostUiState(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = PostUiState(
                            isLoading = false,
                            errorMessage = result.message.orEmpty()
                        )
                    }
                }
            }
        }
    }

    fun deletePosts() {
        viewModelScope.coroutineScope.launch {
            deletePostsUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.value = PostUiState(
                            posts = result.data.orEmpty()
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = PostUiState(
                            errorMessage = result.message.orEmpty()
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}

data class PostUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val errorMessage: String = ""
)