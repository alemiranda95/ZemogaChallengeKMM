package com.example.zemogachallengekmm.db

import android.content.Context
import com.example.zemogachallengekmm.database.PostDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(PostDatabase.Schema, context, "post.db")
    }
}