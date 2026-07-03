package com.powerfit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper
import com.powerfit.app.data.Exercicios
import java.util.*

class TreinoDiaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_treino_dia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<TextView>(R.id.txtTituloHeader).text = "TREINO DO DIA"

        val container = view.findViewById<LinearLayout>(R.id.containerExercicios)
        val usuario = DatabaseHelper.carregar(requireContext())
        val cal = Calendar.getInstance()
        val diasMap = mapOf(
            Calendar.MONDAY to "Segunda", Calendar.TUESDAY to "Terca",
            Calendar.WEDNESDAY to "Quarta", Calendar.THURSDAY to "Quinta",
            Calendar.FRIDAY to "Sexta", Calendar.SATURDAY to "Sabado"
        )
        val dia = diasMap[cal.get(Calendar.DAY_OF_WEEK)] ?: "Domingo"

        view.findViewById<TextView>(R.id.txtTitulo).text = "TREINO - $dia"

        val musculos = Exercicios.splitSemanal[dia] ?: emptyList()

        if (musculos.isEmpty()) {
            val txtDescanso = TextView(requireContext()).apply {
                text = "Hoje e dia de descanso!"
                setTextColor(resources.getColor(R.color.texto, null))
                textSize = 16f
                setPadding(32, 32, 32, 32)
            }
            container.addView(txtDescanso)
            return
        }

        for (musculo in musculos) {
            val titulo = TextView(requireContext()).apply {
                text = musculo.uppercase()
                setTextColor(resources.getColor(R.color.accent, null))
                textSize = 18f
                setPadding(0, 24, 0, 8)
            }
            container.addView(titulo)

            val lista = Exercicios.dados[musculo]?.get(usuario.objetivo) ?: emptyList()
            for (ex in lista) {
                val txt = TextView(requireContext()).apply {
                    text = "${ex.nome}\n${ex.series}x ${ex.reps} | Desc: ${ex.descanso}s"
                    setTextColor(resources.getColor(R.color.texto, null))
                    textSize = 14f
                    setPadding(16, 4, 16, 4)
                }
                container.addView(txt)
            }
        }
    }
}
