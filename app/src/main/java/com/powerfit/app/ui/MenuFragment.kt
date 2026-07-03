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
            R.id.btnTreinoDia to TreinoDiaFragment(),
            R.id.btnTreinoSemana to TreinoSemanaFragment(),
            R.id.btnTimer to TimerFragment(),
            R.id.btnHistorico to HistoricoFragment(),
            R.id.btnRegistrarPeso to RegistrarPesoFragment(),
            R.id.btnEvolucao to EvolucaoPesoFragment(),
            R.id.btnVerImc to ImcFragment(),
            R.id.btnAtualizarPerfil to PerfilFragment(),
            R.id.btnAssistente to AssistenteFragment()
        )

        botoes.forEach { (id, fragment) ->
            view.findViewById<Button>(id)?.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}
