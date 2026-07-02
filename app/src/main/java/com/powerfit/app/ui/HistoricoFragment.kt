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

class HistoricoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_historico, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container = view.findViewById<LinearLayout>(R.id.containerHistorico)
        val usuario = DatabaseHelper.carregar(requireContext())
        val treinos = usuario.treinosFeitos.takeLast(30).reversed()

        if (treinos.isEmpty()) {
            val txt = TextView(requireContext()).apply {
                text = "Nenhum treino registrado."
                setTextColor(resources.getColor(R.color.texto, null))
                textSize = 14f
                setPadding(32, 32, 32, 32)
            }
            container.addView(txt)
            return
        }

        for (t in treinos) {
            val txtData = TextView(requireContext()).apply {
                text = t.data
                setTextColor(resources.getColor(R.color.accent, null))
                textSize = 14f
                setPadding(0, 12, 0, 4)
            }
            container.addView(txtData)

            val txtMusculos = TextView(requireContext()).apply {
                text = t.musculos
                setTextColor(resources.getColor(R.color.texto, null))
                textSize = 13f
                setPadding(0, 0, 0, 12)
            }
            container.addView(txtMusculos)
        }
    }
}
