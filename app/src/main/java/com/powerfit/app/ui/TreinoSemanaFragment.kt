package com.powerfit.app.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper
import com.powerfit.app.data.Exercicios
import java.util.Calendar

class TreinoSemanaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_treino_semana, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<TextView>(R.id.txtTituloHeader).text = "TREINO DA SEMANA"

        val container = view.findViewById<LinearLayout>(R.id.containerSemana)
        val usuario = DatabaseHelper.carregar(requireContext())

        val diaAtual = mapOf(
            Calendar.MONDAY to "Segunda", Calendar.TUESDAY to "Terca",
            Calendar.WEDNESDAY to "Quarta", Calendar.THURSDAY to "Quinta",
            Calendar.FRIDAY to "Sexta", Calendar.SATURDAY to "Sabado"
        )[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)]

        for (dia in Exercicios.diasSemana) {
            val isToday = dia == diaAtual

            val card = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                background = ContextCompat.getDrawable(
                    context,
                    if (isToday) R.drawable.bg_card_highlight else R.drawable.bg_card
                )
                setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = dpToPx(12) }
            }

            val txtDia = TextView(requireContext()).apply {
                text = dia.uppercase()
                setTextColor(ContextCompat.getColor(context, R.color.titulo))
                textSize = 16f
                typeface = Typeface.DEFAULT_BOLD
                if (isToday) {
                    text = "${dia.uppercase()}  (HOJE)"
                }
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = dpToPx(8) }
            }
            card.addView(txtDia)

            val musculos = Exercicios.splitSemanal[dia] ?: emptyList()
            for (musculo in musculos) {
                val muscleRow = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    setPadding(0, dpToPx(2), 0, dpToPx(2))
                }

                val muscleIconRes = when (musculo) {
                    "peito" -> R.drawable.ic_muscle_chest
                    "costas" -> R.drawable.ic_muscle_back
                    "pernas" -> R.drawable.ic_muscle_legs
                    "bracos" -> R.drawable.ic_muscle_arms
                    "ombro" -> R.drawable.ic_muscle_shoulders
                    "abdomen" -> R.drawable.ic_muscle_abs
                    else -> R.drawable.ic_muscle_chest
                }

                val iconView = ImageView(requireContext()).apply {
                    setImageResource(muscleIconRes)
                    val size = dpToPx(20)
                    layoutParams = LinearLayout.LayoutParams(size, size).apply {
                        marginEnd = dpToPx(8)
                    }
                    setColorFilter(ContextCompat.getColor(context, R.color.titulo))
                }

                val txtMusculo = TextView(requireContext()).apply {
                    text = musculo.uppercase()
                    setTextColor(ContextCompat.getColor(context, R.color.texto))
                    textSize = 14f
                    typeface = Typeface.DEFAULT_BOLD
                }

                muscleRow.addView(iconView)
                muscleRow.addView(txtMusculo)
                card.addView(muscleRow)

                val lista = Exercicios.dados[musculo]?.get(usuario.objetivo) ?: emptyList()
                val count = lista.size
                val txtCount = TextView(requireContext()).apply {
                    text = "    $count exercicios"
                    setTextColor(ContextCompat.getColor(context, R.color.texto_secundario))
                    textSize = 12f
                }
                card.addView(txtCount)
            }

            container.addView(card)
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
