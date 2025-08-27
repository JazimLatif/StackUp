package com.jazim.stackup.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "followed_users")

fun followedKey(userId: Int) = booleanPreferencesKey("followed_$userId")

class FollowingDataStore(private val context: Context) {

    suspend fun toggleFollow(userId: Int, followed: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[followedKey(userId)] = followed
        }
    }

    fun getAllFollowedUserIds(): Flow<Set<Int>> = context.dataStore.data
        .map { prefs ->
            prefs.asMap().keys
                .filter { it.name.startsWith("followed_") && prefs[it] == true }
                .map { it.name.removePrefix("followed_").toInt() }
                .toSet()
        }
}