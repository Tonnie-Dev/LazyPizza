package com.tonyxlab.lazypizza.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tonyxlab.lazypizza.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(Constants.DATASTORE_NAME)

class DataStore(context: Context) {

    val dataStore = context.dataStore

   fun getUserId(): Flow<String> {
        return dataStore.data.map { prefs ->
            prefs[CURRENT_USER_ID] ?: "GUEST"
        }
    }

    suspend fun setUserId(userId: String){
        dataStore.edit { prefs ->
            prefs[CURRENT_USER_ID] = userId
        }
    }

    companion object {
        val CURRENT_USER_ID = stringPreferencesKey("user_id")
    }
}