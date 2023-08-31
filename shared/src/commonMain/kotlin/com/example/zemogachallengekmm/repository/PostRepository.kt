package com.example.zemogachallengekmm.repository

import com.example.zemogachallengekmm.model.Post
import com.example.zemogachallengekmm.remote.dto.PostDto

interface PostRepository: PostDataSource, PostApi

interface PostDataSource {
    suspend fun insertPosts(posts: List<Post>)
    suspend fun getPostsFromDB(): List<Post>
    suspend fun deletePosts()
}

interface PostApi {
    suspend fun getPostsFromApi(): List<PostDto>
}

