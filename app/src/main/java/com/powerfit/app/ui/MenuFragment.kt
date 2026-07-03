package com.powerfit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper
import com.powerfit.app.data.Exercicios
import java.util.Calendar

class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usuario = DatabaseHelper.carregar(requireContext())
        val (imc, _) = DatabaseHelper.calcularImc(usuario.peso, usuario.altura)
        val objetivoLabel = when (usuario.objetivo) {
            "forca" -> "Forca"
            "definicao" -> "Definicao"
            "perda_de_peso" -> "Perda de peso"
            else -> ""
        }

        view.findViewById<TextView>(R.id.txtOla).text = "Ola, ${usuario.nome}!"
        view.findViewById<TextView>(R.id.txtInfo).text = "Pronto para treinar hoje?"

        view.findViewById<TextView>(R.id.chipPeso).text = "Peso: ${usuario.peso}kg"
        view.findViewById<TextView>(R.id.chipImc).text = "IMC: ${"%.1f".format(imc)}"
        view.findViewById<TextView>(R.id.chipObjetivo).text = objetivoLabel

        val diasMap = mapOf(
            Calendar.MONDAY to "Segunda-feira",
            Calendar.TUESDAY to "Terca-feira",
            Calendar.WEDNESDAY to "Quarta-feira",
            Calendar.THURSDAY to "Quinta-feira",
            Calendar.FRIDAY to "Sexta-feira",
            Calendar.SATURDAY to "Sabado",
            Calendar.SUNDAY to "Domingo"
        )
        val diaAtual = diasMap[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)] ?: "Domingo"
        view.findViewById<TextView>(R.id.txtDiaTreino).text = diaAtual

        val diaSemana = mapOf(
            Calendar.MONDAY to "Segunda", Calendar.TUESDAY to "Terca",
            Calendar.WEDNESDAY to "Quarta", Calendar.THURSDAY to "Quinta",
            Calendar.FRIDAY to "Sexta", Calendar.SATURDAY to "Sabado"
        )
        val diaKey = diaSemana[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)]
        val musculosHoje = Exercicios.splitSemanal[diaKey]?.joinToString(", ") { it.uppercase() } ?: "Descanso"
        view.findViewById<TextView>(R.id.txtGruposMusculares).text = musculosHoje

        view.findViewById<Button>(R.id.btnComecarTreino).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TreinoDiaFragment())
                .addToBackStack(null)
                .commit()
        }

        val navMap = mapOf(
            R.id.cardAssistente to AssistenteFragment(),
            R.id.cardTimer to TimerFragment(),
            R.id.cardHistorico to HistoricoFragment(),
            R.id.cardEvolucao to EvolucaoPesoFragment(),
            R.id.btnTreinoSemana to TreinoSemanaFragment(),
            R.id.btnRegistrarPeso to RegistrarPesoFragment(),
            R.id.btnVerImc to ImcFragment(),
            R.id.btnAtualizarPerfil to PerfilFragment()
        )

        navMap.forEach { (id, fragment) ->
            view.findViewById<View>(id)?.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}
