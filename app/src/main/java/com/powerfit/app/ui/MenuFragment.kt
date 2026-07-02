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
import java.text.SimpleDateFormat
import java.util.*

class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usuario = DatabaseHelper.carregar(requireContext())
        val txtOla = view.findViewById<TextView>(R.id.txtOla)
        val txtInfo = view.findViewById<TextView>(R.id.txtInfo)

        val (imc, classe) = DatabaseHelper.calcularImc(usuario.peso, usuario.altura)
        val objetivoLabel = when (usuario.objetivo) {
            "forca" -> "Forca"
            "definicao" -> "Definicao"
            "perda_de_peso" -> "Perda de peso"
            else -> ""
        }

        txtOla.text = "Ola, ${usuario.nome}!"
        txtInfo.text = "Peso: ${usuario.peso}kg | IMC: ${"%.1f".format(imc)} ($classe) | $objetivoLabel"

        val botoes = mapOf(
            R.id.btnTreinoDia to "Treino do dia",
            R.id.btnTreinoSemana to "Treino da semana",
            R.id.btnTimer to "Treinar com timer",
            R.id.btnHistorico to "Historico de treinos",
            R.id.btnRegistrarPeso to "Registrar peso",
            R.id.btnEvolucao to "Evolucao do peso",
            R.id.btnVerImc to "Ver IMC",
            R.id.btnAtualizarPerfil to "Atualizar perfil"
        )

        botoes.forEach { (id, _) ->
            view.findViewById<Button>(id)?.setOnClickListener {
                when (id) {
                    R.id.btnTreinoDia -> navigateTo(TreinoDiaFragment())
                    R.id.btnTreinoSemana -> navigateTo(TreinoSemanaFragment())
                    R.id.btnTimer -> navigateTo(TimerFragment())
                    R.id.btnHistorico -> navigateTo(HistoricoFragment())
                    R.id.btnRegistrarPeso -> navigateTo(RegistrarPesoFragment())
                    R.id.btnEvolucao -> navigateTo(EvolucaoPesoFragment())
                    R.id.btnVerImc -> navigateTo(ImcFragment())
                    R.id.btnAtualizarPerfil -> navigateTo(CadastroFragment())
                }
            }
        }
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
