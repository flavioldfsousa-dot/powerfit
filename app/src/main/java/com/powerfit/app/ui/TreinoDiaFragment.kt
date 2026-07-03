package com.powerfit.app.ui

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
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
import com.powerfit.app.MainActivity
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper
import com.powerfit.app.data.Exercicios
import java.util.Calendar

class TreinoDiaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_treino_dia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val txtTituloHeader = view.findViewById<TextView>(R.id.txtTituloHeader)
        txtTituloHeader.text = getString(R.string.today_workout_title)

        val container = view.findViewById<LinearLayout>(R.id.containerExercicios)
        val usuario = DatabaseHelper.carregar(requireContext())

        val diasMap = mapOf(
            Calendar.MONDAY to getString(R.string.monday), Calendar.TUESDAY to getString(R.string.tuesday),
            Calendar.WEDNESDAY to getString(R.string.wednesday), Calendar.THURSDAY to getString(R.string.thursday),
            Calendar.FRIDAY to getString(R.string.friday), Calendar.SATURDAY to getString(R.string.saturday)
        )
        val dia = diasMap[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)] ?: getString(R.string.sunday)

        view.findViewById<TextView>(R.id.txtTitulo).text = "${getString(R.string.workout_day)} - $dia"

        val musculos = Exercicios.splitSemanal[dia] ?: emptyList()
        view.findViewById<TextView>(R.id.txtSubtitulo).text = "${getString(R.string.workouts_by_muscle)}: ${musculos.joinToString(", ") { it.uppercase() }}"

        if (musculos.isEmpty()) {
            val txtDescanso = TextView(requireContext()).apply {
                text = getString(R.string.rest_day)
                setTextColor(ContextCompat.getColor(context, R.color.texto))
                textSize = 16f
                gravity = Gravity.CENTER
                setPadding(32, 80, 32, 32)
            }
            container.addView(txtDescanso)
            return
        }

        for (musculo in musculos) {
            val muscleIconRes = when (musculo) {
                "peito" -> R.drawable.ic_muscle_chest
                "costas" -> R.drawable.ic_muscle_back
                "pernas" -> R.drawable.ic_muscle_legs
                "bracos" -> R.drawable.ic_muscle_arms
                "ombro" -> R.drawable.ic_muscle_shoulders
                "abdomen" -> R.drawable.ic_muscle_abs
                else -> R.drawable.ic_muscle_chest
            }

            val muscleHeader = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(0, 24, 0, 12)
            }

            val iconView = ImageView(requireContext()).apply {
                setImageResource(muscleIconRes)
                val size = dpToPx(28)
                layoutParams = LinearLayout.LayoutParams(size, size).apply {
                    marginEnd = dpToPx(10)
                }
                setColorFilter(ContextCompat.getColor(context, R.color.titulo))
            }

            val txtMuscle = TextView(requireContext()).apply {
                text = musculo.uppercase()
                setTextColor(ContextCompat.getColor(context, R.color.titulo))
                textSize = 18f
                typeface = Typeface.DEFAULT_BOLD
            }

            muscleHeader.addView(iconView)
            muscleHeader.addView(txtMuscle)
            container.addView(muscleHeader)

            val lista = Exercicios.dados[musculo]?.get(usuario.objetivo) ?: emptyList()
            for (ex in lista) {
                val card = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.VERTICAL
                    background = ContextCompat.getDrawable(context, R.drawable.bg_card)
                    setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        bottomMargin = dpToPx(12)
                    }
                }

                val txtNome = TextView(requireContext()).apply {
                    text = ex.nome
                    setTextColor(Color.WHITE)
                    textSize = 16f
                    typeface = Typeface.DEFAULT_BOLD
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { bottomMargin = dpToPx(8) }
                }
                card.addView(txtNome)

                val chipAparelho = TextView(requireContext()).apply {
                    text = "${getString(R.string.equipment)}: ${ex.aparelho}"
                    setTextColor(ContextCompat.getColor(context, R.color.accent))
                    textSize = 12f
                    typeface = Typeface.DEFAULT_BOLD
                    background = ContextCompat.getDrawable(context, R.drawable.bg_chip)
                    val chipLp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        dpToPx(28)
                    ).apply { bottomMargin = dpToPx(8) }
                    setPadding(dpToPx(10), 0, dpToPx(10), 0)
                    gravity = Gravity.CENTER_VERTICAL
                    layoutParams = chipLp
                }
                card.addView(chipAparelho)

                if (ex.descricao.isNotEmpty()) {
                    val txtDescricao = TextView(requireContext()).apply {
                        text = ex.descricao
                        setTextColor(ContextCompat.getColor(context, R.color.texto_secundario))
                        textSize = 13f
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply { bottomMargin = dpToPx(8) }
                    }
                    card.addView(txtDescricao)
                }

                val txtInfo = TextView(requireContext()).apply {
                    text = "${ex.series}x ${ex.reps} | ${getString(R.string.rest_label)}: ${ex.descanso}s"
                    setTextColor(ContextCompat.getColor(context, R.color.texto))
                    textSize = 14f
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { bottomMargin = dpToPx(4) }
                }
                card.addView(txtInfo)

                if (ex.alternativasCasa.isNotEmpty()) {
                    val txtAltCasaLabel = TextView(requireContext()).apply {
                        text = getString(R.string.alt_home)
                        setTextColor(ContextCompat.getColor(context, R.color.amarelo))
                        textSize = 12f
                        typeface = Typeface.DEFAULT_BOLD
                        setPadding(0, dpToPx(6), 0, dpToPx(2))
                    }
                    card.addView(txtAltCasaLabel)

                    for (alt in ex.alternativasCasa) {
                        val txtAlt = TextView(requireContext()).apply {
                            text = "- $alt"
                            setTextColor(ContextCompat.getColor(context, R.color.amarelo))
                            textSize = 12f
                            setPadding(dpToPx(8), 0, 0, dpToPx(2))
                        }
                        card.addView(txtAlt)
                    }
                }

                if (ex.alternativasAparelho.isNotEmpty()) {
                    val txtAltAparelhoLabel = TextView(requireContext()).apply {
                        text = getString(R.string.alt_equipment)
                        setTextColor(ContextCompat.getColor(context, R.color.accent2))
                        textSize = 12f
                        typeface = Typeface.DEFAULT_BOLD
                        setPadding(0, dpToPx(6), 0, dpToPx(2))
                    }
                    card.addView(txtAltAparelhoLabel)

                    for (alt in ex.alternativasAparelho) {
                        val txtAlt = TextView(requireContext()).apply {
                            text = "- $alt"
                            setTextColor(ContextCompat.getColor(context, R.color.accent2))
                            textSize = 12f
                            setPadding(dpToPx(8), 0, 0, dpToPx(2))
                        }
                        card.addView(txtAlt)
                    }
                }

                container.addView(card)
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
