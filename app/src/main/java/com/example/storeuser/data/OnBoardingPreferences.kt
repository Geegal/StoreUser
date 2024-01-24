package com.example.storeuser.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.storeuser.common.Constant


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class OnBoardingPreferences(private val context: Context){

    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constant.ON_BOARDING_NAME)
        private val onBoardingKey = booleanPreferencesKey(name = Constant.ON_BOARDING_KEY)
    }

    val getOnBoardingState: Flow<Boolean> = context.dataStore.data.map { pref ->
        pref[onBoardingKey]?:false
    }

    suspend fun saveOnBoardingState(isCompleted: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[onBoardingKey] = isCompleted
        }
    }
}