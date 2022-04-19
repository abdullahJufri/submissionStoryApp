package com.bangkit.submissionstoryapp.ui


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bangkit.submissionstoryapp.data.remote.model.Authentication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreference private constructor(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>) {

    fun getUser(): Flow<Authentication> {
        return dataStore.data.map {
            Authentication(
                it[NAME_KEY] ?: "",
                it[EMAIL_KEY] ?: "",
                it[PASSWORD_KEY] ?: "",
                it[USERID_KEY] ?: "",
                it[TOKEN_KEY] ?: "",
                it[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(authentication: Authentication) {
        dataStore.edit {
            it[NAME_KEY] = authentication.name
            it[EMAIL_KEY] = authentication.email
            it[PASSWORD_KEY] = authentication.password
            it[USERID_KEY] = authentication.userId
            it[TOKEN_KEY] = authentication.token
            it[STATE_KEY] = authentication.isLogin

        }
    }

//    suspend fun saveUser(user: Authentication) {
//        dataStore.edit { preferences ->
//            preferences[TOKEN_KEY] = user.token
//            preferences[STATE_KEY] = user.isLogin
//
//        }
//    }

    suspend fun logout() {
        dataStore.edit {
            it[STATE_KEY] = false
            it[NAME_KEY] = ""
            it[EMAIL_KEY] = ""
            it[USERID_KEY] = ""
            it[TOKEN_KEY] = ""
            it[PASSWORD_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val USERID_KEY = stringPreferencesKey("userId")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
