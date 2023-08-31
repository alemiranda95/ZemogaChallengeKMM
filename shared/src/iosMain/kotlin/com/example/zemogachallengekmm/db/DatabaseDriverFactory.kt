package com.example.zemogachallengekmm.db

import com.example.zemogachallengekmm.database.PostDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(PostDatabase.Schema, "post.db")
    }
}