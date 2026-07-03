package com.powerfit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper
import com.powerfit.app.data.RegistroPeso
import java.text.SimpleDateFormat
import java.util.*

class RegistrarPesoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registrar_peso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<TextView>(R.id.txtTituloHeader).text = "REGISTRAR PESO"

        val inputPeso = view.findViewById<EditText>(R.id.inputPeso)
        val btnSalvar = view.findViewById<Button>(R.id.btnSalvarPeso)
        val txtStatus = view.findViewById<TextView>(R.id.txtStatus)

        btnSalvar.setOnClickListener {
            val peso = inputPeso.text.toString().toFloatOrNull()
            if (peso != null && peso > 0) {
                val usuario = DatabaseHelper.carregar(requireContext())
                val data = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                usuario.historicoPeso.add(RegistroPeso(peso, data))
                usuario.peso = peso
                DatabaseHelper.salvar(requireContext(), usuario)
                txtStatus.text = "Peso ${peso}kg salvo!"
                inputPeso.text.clear()
            }
        }
    }
}
