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
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper

class HistoricoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_historico, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<TextView>(R.id.txtTituloHeader).text = "HISTORICO"

        val container = view.findViewById<LinearLayout>(R.id.containerHistorico)
        val usuario = DatabaseHelper.carregar(requireContext())
        val treinos = usuario.treinosFeitos.takeLast(30).reversed()

        if (treinos.isEmpty()) {
            val txt = TextView(requireContext()).apply {
                text = "Nenhum treino registrado ainda"
                setTextColor(ContextCompat.getColor(context, R.color.texto_secundario))
                textSize = 14f
                gravity = Gravity.CENTER
                setPadding(32, 80, 32, 32)
            }
            container.addView(txt)
            return
        }

        for (t in treinos) {
            val card = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                background = ContextCompat.getDrawable(context, R.drawable.bg_card)
                setPadding(dpToPx(16), dpToPx(14), dpToPx(16), dpToPx(14))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = dpToPx(10) }
            }

            val txtData = TextView(requireContext()).apply {
                text = t.data
                setTextColor(ContextCompat.getColor(context, R.color.titulo))
                textSize = 14f
                typeface = Typeface.DEFAULT_BOLD
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = dpToPx(4) }
            }
            card.addView(txtData)

            val txtMusculos = TextView(requireContext()).apply {
                text = t.musculos
                setTextColor(ContextCompat.getColor(context, R.color.texto))
                textSize = 13f
            }
            card.addView(txtMusculos)

            container.addView(card)
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
