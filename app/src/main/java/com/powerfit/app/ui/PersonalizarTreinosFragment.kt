package com.powerfit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.powerfit.app.R
import com.powerfit.app.data.Exercicios

class PersonalizarTreinosFragment : Fragment() {

    private var objetivo = "forca"
    private var exerciciosData = mutableMapOf<String, List<Exercicios.Exercicio>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_personalizar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<TextView>(R.id.txtTituloHeader).text = getString(R.string.customize_title)

        val btnForca = view.findViewById<Button>(R.id.btnForca)
        val btnDefinicao = view.findViewById<Button>(R.id.btnDefinicao)
        val btnPerdaPeso = view.findViewById<Button>(R.id.btnPerdaPeso)

        btnForca.setOnClickListener { selectObj(btnForca, "forca") }
        btnDefinicao.setOnClickListener { selectObj(btnDefinicao, "definicao") }
        btnPerdaPeso.setOnClickListener { selectObj(btnPerdaPeso, "perda_de_peso") }

        view.findViewById<Button>(R.id.btnSalvar).setOnClickListener {
            salvarPersonalizacao()
        }

        view.findViewById<Button>(R.id.btnResetar).setOnClickListener {
            resetarPadrao()
        }

        carregarExercicios()
    }

    private fun selectObj(el: Button, obj: String) {
        view?.let { v ->
            v.findViewById<Button>(R.id.btnForca).setBackgroundResource(R.drawable.bg_chip)
            v.findViewById<Button>(R.id.btnDefinicao).setBackgroundResource(R.drawable.bg_chip)
            v.findViewById<Button>(R.id.btnPerdaPeso).setBackgroundResource(R.drawable.bg_chip)
        }
        el.setBackgroundResource(R.drawable.bg_btn_accent)
        objetivo = obj
        renderizarLista()
    }

    private fun carregarExercicios() {
        val muscles = listOf("peito", "costas", "pernas", "ombro", "bracos", "abdomen")
        for (muscle in muscles) {
            exerciciosData[muscle] = Exercicios.dados[muscle]?.get(objetivo) ?: emptyList()
        }
        renderizarLista()
    }

    private fun renderizarLista() {
        val container = view?.findViewById<LinearLayout>(R.id.listaExercicios) ?: return
        container.removeAllViews()

        val saved = carregarSalvo()
        val muscles = listOf("peito", "costas", "pernas", "ombro", "bracos", "abdomen")

        for (muscle in muscles) {
            val exercicios = saved[muscle] ?: exerciciosData[muscle] ?: emptyList()

            val muscleCard = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(32, 32, 32, 32)
                setBackgroundResource(R.drawable.bg_card)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.bottomMargin = 32
                layoutParams = params
            }

            val muscleName = when (muscle) {
                "peito" -> getString(R.string.muscle_chest)
                "costas" -> getString(R.string.muscle_back)
                "pernas" -> getString(R.string.muscle_legs)
                "ombro" -> getString(R.string.muscle_shoulders)
                "bracos" -> getString(R.string.muscle_arms)
                "abdomen" -> getString(R.string.muscle_abs)
                else -> muscle
            }

            val headerText = TextView(requireContext()).apply {
                text = "$muscleName (${exercicios.size} ${getString(R.string.exercises)})"
                setTextColor(android.graphics.Color.parseColor("#E8E8F0"))
                textSize = 16f
                paint.isFakeBoldText = true
                setPadding(0, 0, 0, 24)
            }
            muscleCard.addView(headerText)

            for ((index, ex) in exercicios.withIndex()) {
                val exRow = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(24, 16, 24, 16)
                    val bg = android.graphics.drawable.GradientDrawable().apply {
                        setColor(android.graphics.Color.parseColor("#1A1A2E"))
                        cornerRadius = 24f
                    }
                    background = bg
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.bottomMargin = 12
                    layoutParams = params
                    gravity = android.view.Gravity.CENTER_VERTICAL
                }

                val moveUpBtn = ImageButton(requireContext()).apply {
                    setImageResource(android.R.drawable.arrow_up_float)
                    setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    setColorFilter(android.graphics.Color.parseColor("#8888A0"))
                    val params = LinearLayout.LayoutParams(64, 64)
                    layoutParams = params
                    setOnClickListener { moverExercicio(muscle, index, "up") }
                }

                val moveDownBtn = ImageButton(requireContext()).apply {
                    setImageResource(android.R.drawable.arrow_down_float)
                    setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    setColorFilter(android.graphics.Color.parseColor("#8888A0"))
                    val params = LinearLayout.LayoutParams(64, 64)
                    layoutParams = params
                    setOnClickListener { moverExercicio(muscle, index, "down") }
                }

                val exInfo = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.VERTICAL
                    val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                    params.marginStart = 16
                    params.marginEnd = 16
                    layoutParams = params
                }

                val exName = TextView(requireContext()).apply {
                    text = ex.nome
                    setTextColor(android.graphics.Color.parseColor("#E8E8F0"))
                    textSize = 14f
                }

                val exDetail = TextView(requireContext()).apply {
                    text = "${ex.series} ${getString(R.string.exercises)} | ${ex.descanso}s"
                    setTextColor(android.graphics.Color.parseColor("#8888A0"))
                    textSize = 12f
                }

                exInfo.addView(exName)
                exInfo.addView(exDetail)

                exRow.addView(moveUpBtn)
                exRow.addView(moveDownBtn)
                exRow.addView(exInfo)

                muscleCard.addView(exRow)
            }

            container.addView(muscleCard)
        }
    }

    private fun carregarSalvo(): MutableMap<String, List<Exercicios.Exercicio>> {
        val prefs = requireContext().getSharedPreferences("powerfit_prefs", 0)
        val json = prefs.getString("schedule_$objetivo", null)
        return if (json != null) {
            val type = object : TypeToken<Map<String, List<Exercicios.Exercicio>>>() {}.type
            Gson().fromJson(json, type) ?: mutableMapOf()
        } else {
            mutableMapOf()
        }
    }

    private fun moverExercicio(muscle: String, index: Int, direction: String) {
        val schedule = carregarSalvo().toMutableMap()
        val list = (schedule[muscle] ?: exerciciosData[muscle] ?: emptyList()).toMutableList()

        val newIndex = if (direction == "up") index - 1 else index + 1
        if (newIndex < 0 || newIndex >= list.size) return

        val temp = list[index]
        list[index] = list[newIndex]
        list[newIndex] = temp

        schedule[muscle] = list
        salvarSalvo(schedule)
        renderizarLista()
    }

    private fun salvarSalvo(schedule: Map<String, List<Exercicios.Exercicio>>) {
        val prefs = requireContext().getSharedPreferences("powerfit_prefs", 0)
        val json = Gson().toJson(schedule)
        prefs.edit().putString("schedule_$objetivo", json).apply()
    }

    private fun salvarPersonalizacao() {
        Toast.makeText(requireContext(), getString(R.string.customization_saved), Toast.LENGTH_SHORT).show()
    }

    private fun resetarPadrao() {
        val prefs = requireContext().getSharedPreferences("powerfit_prefs", 0)
        prefs.edit().remove("schedule_$objetivo").apply()
        renderizarLista()
        Toast.makeText(requireContext(), getString(R.string.restored_default), Toast.LENGTH_SHORT).show()
    }
}
