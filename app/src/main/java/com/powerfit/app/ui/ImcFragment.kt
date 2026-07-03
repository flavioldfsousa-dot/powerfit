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

class ImcFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_imc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<TextView>(R.id.txtTituloHeader).text = "SEU IMC"

        val container = view.findViewById<LinearLayout>(R.id.containerImc)
        val usuario = DatabaseHelper.carregar(requireContext())
        val (imc, classe) = DatabaseHelper.calcularImc(usuario.peso, usuario.altura)

        val txtImc = TextView(requireContext()).apply {
            text = "%.1f".format(imc)
            setTextColor(resources.getColor(R.color.accent, null))
            textSize = 48f
            setPadding(0, 24, 0, 8)
        }
        container.addView(txtImc)

        val txtClasse = TextView(requireContext()).apply {
            text = classe
            setTextColor(resources.getColor(R.color.texto, null))
            textSize = 18f
            setPadding(0, 8, 0, 16)
        }
        container.addView(txtClasse)

        val info = TextView(requireContext()).apply {
            text = "Peso: ${usuario.peso}kg\nAltura: ${usuario.altura}m\n\n" +
                    "Referencia:\n" +
                    "Abaixo de 18.5 - Abaixo do peso\n" +
                    "18.5 a 24.9 - Peso normal\n" +
                    "25.0 a 29.9 - Sobrepeso\n" +
                    "30.0 ou mais - Obesidade"
            setTextColor(resources.getColor(R.color.texto, null))
            textSize = 13f
            setPadding(0, 8, 0, 0)
        }
        container.addView(info)
    }
}
