package com.powerfit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper
import com.powerfit.app.data.Exercicios

class TreinoSemanaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_treino_semana, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container = view.findViewById<LinearLayout>(R.id.containerSemana)
        val usuario = DatabaseHelper.carregar(requireContext())

        for (dia in Exercicios.diasSemana) {
            val titulo = TextView(requireContext()).apply {
                text = dia.uppercase()
                setTextColor(resources.getColor(R.color.accent, null))
                textSize = 18f
                setPadding(0, 24, 0, 8)
            }
            container.addView(titulo)

            val musculos = Exercicios.splitSemanal[dia] ?: emptyList()
            for (musculo in musculos) {
                val lista = Exercicios.dados[musculo]?.get(usuario.objetivo) ?: emptyList()
                val texto = StringBuilder()
                texto.appendLine("  ${musculo.uppercase()}")
                for (ex in lista) {
                    texto.appendLine("    ${ex.nome} (${ex.series}x ${ex.reps})")
                }
                val txt = TextView(requireContext()).apply {
                    text = texto.toString()
                    setTextColor(resources.getColor(R.color.texto, null))
                    textSize = 13f
                    setPadding(16, 4, 16, 4)
                }
                container.addView(txt)
            }
        }
    }
}
