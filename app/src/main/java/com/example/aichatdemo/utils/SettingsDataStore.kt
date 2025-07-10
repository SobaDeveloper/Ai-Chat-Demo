package com.example.aichatdemo.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.aichatdemo.feature.chat.AiModel
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    val selectedAiModel: Flow<AiModel> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val aiModelString = preferences[KEY_SELECTED_MODEL]
            try {
                aiModelString?.let { AiModel.valueOf(it) } ?: AiModel.FLASH_LITE_2_0
            } catch (e: IllegalArgumentException) {
                AiModel.FLASH_LITE_2_0
            }
        }

    val aiPrompt: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_AI_PROMPT] ?: DEFAULT_PROMPT
    }

    suspend fun setSelectedModel(model: AiModel) {
        context.dataStore.edit { settings ->
            settings[KEY_SELECTED_MODEL] = model.name
        }
    }

    suspend fun setAiPrompt(prompt: String) {
        context.dataStore.edit { settings ->
            settings[KEY_AI_PROMPT] = prompt
        }
    }

    companion object {
        val KEY_SELECTED_MODEL = stringPreferencesKey("selected_model")
        val KEY_AI_PROMPT = stringPreferencesKey("ai_prompt")
        const val DEFAULT_PROMPT = "You are an English language vocabulary instructor. You will help students define words and provide " +
                "three examples " +
                "of word usages."
    }
}