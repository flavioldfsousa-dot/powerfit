package com.powerfit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper

class PerfilFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usuario = DatabaseHelper.carregar(requireContext())
        val inputNome = view.findViewById<EditText>(R.id.inputNomePerfil)
        val inputPeso = view.findViewById<EditText>(R.id.inputPesoPerfil)
        val inputAltura = view.findViewById<EditText>(R.id.inputAlturaPerfil)
        val btnSalvar = view.findViewById<Button>(R.id.btnSalvarPerfil)

        inputNome.setText(usuario.nome)
        inputPeso.setText(usuario.peso.toString())
        inputAltura.setText(usuario.altura.toString())

        btnSalvar.setOnClickListener {
            val nome = inputNome.text.toString().trim()
            val peso = inputPeso.text.toString().toFloatOrNull() ?: 0f
            val altura = inputAltura.text.toString().toFloatOrNull() ?: 0f

            if (nome.isNotEmpty() && peso > 0 && altura > 0) {
                usuario.nome = nome
                usuario.peso = peso
                usuario.altura = altura
                DatabaseHelper.salvar(requireContext(), usuario)
                Toast.makeText(requireContext(), "Perfil atualizado!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
