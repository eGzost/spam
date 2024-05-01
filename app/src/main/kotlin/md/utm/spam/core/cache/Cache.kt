package md.utm.spam.core.cache

import android.content.Context
import androidx.core.content.edit

class Cache(context: Context) {
    private val preferences by lazy {
        context.getSharedPreferences("cache", Context.MODE_PRIVATE)
    }

    private object Keys {
        const val MAILTRAP_KEY = "mailtrap_api_key"
    }

    fun saveKey(key: String) = preferences.edit {
        putString(Keys.MAILTRAP_KEY, key)
    }

    fun getKey() = preferences.getString(Keys.MAILTRAP_KEY, null)
}