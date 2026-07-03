package com.powerfit.app.data

import android.content.Context
import android.content.SharedPreferences
import java.util.Locale

object LocaleHelper {
    private const val PREF_NAME = "powerfit_prefs"
    private const val KEY_LANG = "app_language"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setLanguage(context: Context, langCode: String) {
        getPrefs(context).edit().putString(KEY_LANG, langCode).apply()
    }

    fun getLanguage(context: Context): String {
        return getPrefs(context).getString(KEY_LANG, "pt") ?: "pt"
    }

    fun applyLanguage(context: Context) {
        val lang = getLanguage(context)
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun getLanguageList(): List<Pair<String, String>> {
        return listOf(
            "pt" to "Portugues (BR)",
            "en" to "English (US)",
            "es" to "Espanhol",
            "fr" to "Francais",
            "de" to "Deutsch",
            "ja" to "日本語"
        )
    }
}
