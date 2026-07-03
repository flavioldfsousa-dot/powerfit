package com.powerfit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.powerfit.app.MainActivity
import com.powerfit.app.R
import com.powerfit.app.data.DatabaseHelper
import com.powerfit.app.data.LocaleHelper

class PerfilFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<TextView>(R.id.txtTituloHeader).text = getString(R.string.profile)

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
                Toast.makeText(requireContext(), getString(R.string.saved_success), Toast.LENGTH_SHORT).show()
            }
        }

        val languageSpinner = view.findViewById<Spinner>(R.id.spinnerLanguage)
        val languages = LocaleHelper.getLanguageList()
        val langNames = languages.map { it.second }.toTypedArray()
        val currentLang = LocaleHelper.getLanguage(requireContext())

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, langNames)
        languageSpinner.adapter = adapter

        val currentIndex = languages.indexOfFirst { it.first == currentLang }
        if (currentIndex >= 0) languageSpinner.setSelection(currentIndex)

        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                val selectedCode = languages[position].first
                if (selectedCode != currentLang) {
                    LocaleHelper.setLanguage(requireContext(), selectedCode)
                    LocaleHelper.applyLanguage(requireContext())
                    activity?.recreate()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val btnPersonalizar = view.findViewById<LinearLayout>(R.id.btnPersonalizarTreinos)
        btnPersonalizar?.setOnClickListener {
            (requireActivity() as MainActivity).navigateTo(PersonalizarTreinosFragment())
        }
    }
}
