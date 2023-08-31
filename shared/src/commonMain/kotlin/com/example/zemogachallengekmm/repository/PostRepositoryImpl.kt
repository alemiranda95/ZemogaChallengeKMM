package com.example.zemogachallengekmm.repository

import com.example.zemogachallengekmm.database.PostDatabase
import com.example.zemogachallengekmm.db.DatabaseDriverFactory
import com.example.zemogachallengekmm.model.Post
import com.example.zemogachallengekmm.model.toPost
import com.example.zemogachallengekmm.remote.dto.PostDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class PostRepositoryImpl(
    databaseDriverFactory: DatabaseDriverFactory
): PostRepository {

    private val queries = PostDatabase(databaseDriverFactory.createDriver()).postQueries
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    override suspend fun insertPosts(posts: List<Post>) {
        queries.transaction {
            posts.forEach { post ->
                queries.insertPost(
                    id = post.id.toLong(),
                    userId = post.userId.toLong(),
                    title = post.title,
                    body = post.body
                )
            }
        }
    }

    override suspend fun getPostsFromDB(): List<Post> {
        return queries.getPosts().executeAsList().map { it.toPost() }
    }

    override suspend fun deletePosts() {
        queries.transaction {
            queries.deletePosts()
        }
    }

    override suspend fun getPostsFromApi(): List<PostDto> {
        return httpClient.get("https://jsonplaceholder.typicode.com/posts").body()
    }
}