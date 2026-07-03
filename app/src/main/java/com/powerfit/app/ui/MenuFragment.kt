package com.powerfit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.powerfit.app.MainActivity
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper
import com.powerfit.app.data.Exercicios
import com.powerfit.app.data.LocaleHelper
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
            "forca" -> getString(R.string.strength)
            "definicao" -> getString(R.string.definition)
            "perda_de_peso" -> getString(R.string.weight_loss)
            else -> ""
        }

        view.findViewById<TextView>(R.id.txtOla).text = "${getString(R.string.greeting)}, ${usuario.nome}!"
        view.findViewById<TextView>(R.id.txtInfo).text = getString(R.string.ready_train)

        view.findViewById<TextView>(R.id.chipPeso).text = "${getString(R.string.weight_kg)}: ${usuario.peso}kg"
        view.findViewById<TextView>(R.id.chipImc).text = "IMC: ${"%.1f".format(imc)}"
        view.findViewById<TextView>(R.id.chipObjetivo).text = objetivoLabel

        val diasMap = mapOf(
            Calendar.MONDAY to getString(R.string.monday_full),
            Calendar.TUESDAY to getString(R.string.tuesday_full),
            Calendar.WEDNESDAY to getString(R.string.wednesday_full),
            Calendar.THURSDAY to getString(R.string.thursday_full),
            Calendar.FRIDAY to getString(R.string.friday_full),
            Calendar.SATURDAY to getString(R.string.saturday_full),
            Calendar.SUNDAY to getString(R.string.sunday_full)
        )
        val diaAtual = diasMap[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)] ?: getString(R.string.sunday_full)
        view.findViewById<TextView>(R.id.txtDiaTreino).text = diaAtual

        val diaSemana = mapOf(
            Calendar.MONDAY to getString(R.string.monday), Calendar.TUESDAY to getString(R.string.tuesday),
            Calendar.WEDNESDAY to getString(R.string.wednesday), Calendar.THURSDAY to getString(R.string.thursday),
            Calendar.FRIDAY to getString(R.string.friday), Calendar.SATURDAY to getString(R.string.saturday)
        )
        val diaKey = diaSemana[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)]
        val musculosHoje = Exercicios.splitSemanal[diaKey]?.joinToString(", ") { it.uppercase() } ?: getString(R.string.rest_day)
        view.findViewById<TextView>(R.id.txtGruposMusculares).text = musculosHoje

        view.findViewById<TextView>(R.id.txtBtnComecarTreino).text = getString(R.string.start_btn)

        val mainActivity = requireActivity() as MainActivity

        view.findViewById<Button>(R.id.btnComecarTreino).setOnClickListener {
            mainActivity.navigateTo(TreinoDiaFragment())
        }

        val navMap = mapOf(
            R.id.cardAssistente to AssistenteFragment(),
            R.id.cardTimer to TimerFragment(),
            R.id.cardHistorico to HistoricoFragment(),
            R.id.cardEvolucao to EvolucaoPesoFragment(),
            R.id.btnTreinoSemana to TreinoSemanaFragment(),
            R.id.btnRegistrarPeso to RegistrarPesoFragment(),
            R.id.btnVerImc to ImcFragment(),
            R.id.btnAtualizarPerfil to PerfilFragment(),
            R.id.btnPersonalizarTreinos to PersonalizarTreinosFragment()
        )

        navMap.forEach { (id, fragment) ->
            view.findViewById<View>(id)?.setOnClickListener {
                mainActivity.navigateTo(fragment)
            }
        }
    }
}
