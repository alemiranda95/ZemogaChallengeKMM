package com.example.zemogachallengekmm.model

import com.example.zemogachallengekmm.remote.dto.PostDto
import database.PostEntity

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

fun PostEntity.toPost(): Post {
    return Post(
        id = id.toInt(),
        userId = userId.toInt(),
        title = title,
        body = body
    )
}

fun PostDto.toPost(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}