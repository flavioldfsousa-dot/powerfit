package com.powerfit.app.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.powerfit.app.MainActivity
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper

class EvolucaoPesoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_evolucao_peso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<TextView>(R.id.txtTituloHeader).text = "EVOLUCAO DO PESO"

        val container = view.findViewById<LinearLayout>(R.id.containerEvolucao)
        val usuario = DatabaseHelper.carregar(requireContext())
        val historico = usuario.historicoPeso.takeLast(20).reversed()

        if (historico.isEmpty()) {
            val txt = TextView(requireContext()).apply {
                text = "Nenhum registro de peso ainda"
                setTextColor(ContextCompat.getColor(context, R.color.texto_secundario))
                textSize = 14f
                gravity = Gravity.CENTER
                setPadding(32, 80, 32, 32)
            }
            container.addView(txt)
            return
        }

        val primeiro = historico.last().peso

        for (reg in historico) {
            val diff = reg.peso - primeiro
            val sinal = if (diff > 0) "+" else ""
            val cor = if (diff <= 0) ContextCompat.getColor(requireContext(), R.color.verde)
            else ContextCompat.getColor(requireContext(), R.color.amarelo)

            val card = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                background = ContextCompat.getDrawable(context, R.drawable.bg_card)
                setPadding(dpToPx(16), dpToPx(14), dpToPx(16), dpToPx(14))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = dpToPx(10) }
                gravity = Gravity.CENTER_VERTICAL
            }

            val txtData = TextView(requireContext()).apply {
                text = reg.data
                setTextColor(ContextCompat.getColor(context, R.color.texto))
                textSize = 14f
                typeface = Typeface.DEFAULT_BOLD
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.4f)
            }

            val txtPeso = TextView(requireContext()).apply {
                text = "${reg.peso}kg"
                setTextColor(Color.WHITE)
                textSize = 14f
                typeface = Typeface.DEFAULT_BOLD
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.3f)
            }

            val txtDiff = TextView(requireContext()).apply {
                text = "${sinal}${"%.1f".format(diff)}kg"
                setTextColor(cor)
                textSize = 12f
                typeface = Typeface.DEFAULT_BOLD
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.3f)
                gravity = Gravity.END
            }

            card.addView(txtData)
            card.addView(txtPeso)
            card.addView(txtDiff)
            container.addView(card)
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
