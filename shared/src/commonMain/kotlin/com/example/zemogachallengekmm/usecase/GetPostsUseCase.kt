package com.example.zemogachallengekmm.usecase

import com.example.zemogachallengekmm.repository.PostRepositoryImpl
import com.example.zemogachallengekmm.db.DatabaseDriverFactory
import com.example.zemogachallengekmm.model.Post
import com.example.zemogachallengekmm.model.toPost
import com.example.zemogachallengekmm.repository.PostRepository
import com.example.zemogachallengekmm.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPostsUseCase(
    databaseDriverFactory: DatabaseDriverFactory,
    private val repository: PostRepository = PostRepositoryImpl(databaseDriverFactory)
) {

    operator fun invoke(isCached: Boolean): Flow<Resource<List<Post>>> = flow {
        try {
            emit(Resource.Loading())

            val posts = if (isCached) {
                repository.getPostsFromDB()
            } else {
                repository.getPostsFromApi().map { postDto ->
                    postDto.toPost()
                }.also { posts ->
                    repository.insertPosts(posts)
                }
            }

            if (posts.isEmpty()) {
                emit(Resource.Error("No posts."))
            } else {
                emit(Resource.Success(data = posts))
            }
        } catch(e: Exception) {
            emit(Resource.Error(message = e.message.orEmpty()))
        }
    }
}