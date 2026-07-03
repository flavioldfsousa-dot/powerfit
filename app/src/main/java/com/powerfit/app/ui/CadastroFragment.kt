package com.powerfit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper
import com.powerfit.app.data.Usuario

class CadastroFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.txtTituloHeader).text = "POWER FIT"

        val inputNome = view.findViewById<EditText>(R.id.inputNome)
        val inputPeso = view.findViewById<EditText>(R.id.inputPeso)
        val inputAltura = view.findViewById<EditText>(R.id.inputAltura)
        val spinnerObjetivo = view.findViewById<Spinner>(R.id.spinnerObjetivo)
        val btnSalvar = view.findViewById<Button>(R.id.btnSalvar)

        val objetivos = arrayOf("Forca", "Definicao", "Perda de peso")
        spinnerObjetivo.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, objetivos)

        btnSalvar.setOnClickListener {
            val nome = inputNome.text.toString().trim()
            val peso = inputPeso.text.toString().toFloatOrNull() ?: 0f
            val altura = inputAltura.text.toString().toFloatOrNull() ?: 0f
            val objetivoMap = mapOf("Forca" to "forca", "Definicao" to "definicao", "Perda de peso" to "perda_de_peso")
            val objetivo = objetivoMap[spinnerObjetivo.selectedItem.toString()] ?: "forca"

            if (nome.isNotEmpty() && peso > 0 && altura > 0) {
                val usuario = Usuario(nome, peso, altura, objetivo)
                DatabaseHelper.salvar(requireContext(), usuario)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, MenuFragment())
                    .commit()
            }
        }
    }
}
