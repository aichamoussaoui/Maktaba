package com.ElOuedUniv.maktaba.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class OnboardingPreferences(
    private val context: Context
) {
    companion object {
        private val HAS_COMPLETED_ONBOARDING =
            booleanPreferencesKey("has_completed_onboarding")
    }

    val hasCompletedOnboarding: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[HAS_COMPLETED_ONBOARDING] ?: false
        }

    suspend fun setOnboardingCompleted() {
        context.dataStore.edit { preferences ->
            preferences[HAS_COMPLETED_ONBOARDING] = true
        }
    }
}