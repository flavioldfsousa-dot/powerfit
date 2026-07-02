package com.powerfit.app.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper

class EvolucaoPesoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_evolucao_peso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container = view.findViewById<LinearLayout>(R.id.containerEvolucao)
        val usuario = DatabaseHelper.carregar(requireContext())
        val historico = usuario.historicoPeso.takeLast(20).reversed()

        if (historico.isEmpty()) {
            val txt = TextView(requireContext()).apply {
                text = "Nenhum registro de peso."
                setTextColor(resources.getColor(R.color.texto, null))
                textSize = 14f
                setPadding(32, 32, 32, 32)
            }
            container.addView(txt)
            return
        }

        val primeiro = historico.last().peso

        for (reg in historico) {
            val diff = reg.peso - primeiro
            val sinal = if (diff > 0) "+" else ""
            val cor = if (diff <= 0) resources.getColor(R.color.verde, null) else resources.getColor(R.color.amarelo, null)

            val layout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 8, 0, 8)
            }

            val txtData = TextView(requireContext()).apply {
                text = reg.data
                setTextColor(resources.getColor(R.color.texto, null))
                textSize = 14f
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.4f)
            }

            val txtPeso = TextView(requireContext()).apply {
                text = "${reg.peso}kg"
                setTextColor(resources.getColor(R.color.texto, null))
                textSize = 14f
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.3f)
            }

            val txtDiff = TextView(requireContext()).apply {
                text = "${sinal}${"%.1f".format(diff)}kg"
                setTextColor(cor)
                textSize = 12f
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.3f)
            }

            layout.addView(txtData)
            layout.addView(txtPeso)
            layout.addView(txtDiff)
            container.addView(layout)
        }
    }
}
