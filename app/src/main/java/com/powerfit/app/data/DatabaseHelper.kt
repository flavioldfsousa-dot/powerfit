package com.powerfit.app.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Usuario(
    var nome: String = "",
    var peso: Float = 0f,
    var altura: Float = 0f,
    var objetivo: String = "forca",
    var treinosFeitos: MutableList<TreinoFeito> = mutableListOf(),
    var historicoPeso: MutableList<RegistroPeso> = mutableListOf()
)

data class TreinoFeito(
    var data: String = "",
    var musculos: String = ""
)

data class RegistroPeso(
    var peso: Float = 0f,
    var data: String = ""
)

object DatabaseHelper {
    private const val PREF_NAME = "powerfit_prefs"
    private const val KEY_USUARIO = "usuario"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun salvar(context: Context, usuario: Usuario) {
        val json = Gson().toJson(usuario)
        getPrefs(context).edit().putString(KEY_USUARIO, json).apply()
    }

    fun carregar(context: Context): Usuario {
        val json = getPrefs(context).getString(KEY_USUARIO, null)
        return if (json != null) {
            Gson().fromJson(json, Usuario::class.java)
        } else {
            Usuario()
        }
    }

    fun calcularImc(peso: Float, altura: Float): Pair<Float, String> {
        if (altura <= 0) return Pair(0f, "Altura invalida")
        val imc = peso / (altura * altura)
        val classe = when {
            imc < 18.5f -> "Abaixo do peso"
            imc < 25f -> "Peso normal"
            imc < 30f -> "Sobrepeso"
            else -> "Obesidade"
        }
        return Pair(imc, classe)
    }
}
