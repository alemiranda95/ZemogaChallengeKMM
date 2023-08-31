package com.example.zemogachallengekmm.usecase

import com.example.zemogachallengekmm.repository.PostRepositoryImpl
import com.example.zemogachallengekmm.db.DatabaseDriverFactory
import com.example.zemogachallengekmm.model.Post
import com.example.zemogachallengekmm.repository.PostRepository
import com.example.zemogachallengekmm.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeletePostsUseCase(
    databaseDriverFactory: DatabaseDriverFactory,
    private val repository: PostRepository = PostRepositoryImpl(databaseDriverFactory)
) {

    operator fun invoke(): Flow<Resource<List<Post>>> = flow {
        try {
            repository.deletePosts()
            emit(Resource.Success(data = emptyList()))
        } catch(e: Exception) {
            emit(Resource.Error(message = e.message.orEmpty()))
        }
    }
}