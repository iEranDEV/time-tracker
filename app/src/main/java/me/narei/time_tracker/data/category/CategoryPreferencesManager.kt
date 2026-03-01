package me.narei.time_tracker.data.category

import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class CategoryPreferencesManager(private val context: Context) {

    private val HIDDEN_CATEGORIES_KEY = stringSetPreferencesKey("hidden_categories")

    val hiddenCategoriesFlow: Flow<Set<Category>> = context.dataStore.data.map { preferences ->
        val disabledNames = preferences[HIDDEN_CATEGORIES_KEY] ?: emptySet()
        disabledNames.mapNotNull { name ->
            runCatching { Category.valueOf(name) }.getOrNull()
        }.toSet()
    }

    suspend fun toggleHiddenCategory(category: Category) {
        context.dataStore.edit { preferences ->
            val currentHidden = preferences[HIDDEN_CATEGORIES_KEY] ?: emptySet()
            val newHidden = currentHidden.toMutableSet()

            if (currentHidden.contains(category.name)) {
                newHidden.remove(category.name)
            } else {
                newHidden.add(category.name)
            }

            preferences[HIDDEN_CATEGORIES_KEY] = newHidden
        }
    }

    suspend fun resetHiddenCategories() {
        context.dataStore.edit { preferences ->
            preferences[HIDDEN_CATEGORIES_KEY] = emptySet()
        }
    }

}