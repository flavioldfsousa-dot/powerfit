package com.powerfit.app.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.powerfit.app.MainActivity
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

        val cardDisplay = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            background = ContextCompat.getDrawable(context, R.drawable.bg_card_highlight)
            setPadding(dpToPx(24), dpToPx(32), dpToPx(24), dpToPx(32))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(16)
            }
            gravity = Gravity.CENTER
        }

        val txtImc = TextView(requireContext()).apply {
            text = "%.1f".format(imc)
            setTextColor(Color.WHITE)
            textSize = 56f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
        }
        cardDisplay.addView(txtImc)

        val txtClasse = TextView(requireContext()).apply {
            text = classe
            setTextColor(ContextCompat.getColor(context, R.color.accent))
            textSize = 18f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setPadding(0, dpToPx(8), 0, 0)
        }
        cardDisplay.addView(txtClasse)

        container.addView(cardDisplay)

        val cardInfo = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            background = ContextCompat.getDrawable(context, R.drawable.bg_card)
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(12)
            }
        }

        val txtPesoAltura = TextView(requireContext()).apply {
            text = "Peso: ${usuario.peso}kg | Altura: ${usuario.altura}m"
            setTextColor(ContextCompat.getColor(context, R.color.texto))
            textSize = 14f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(16) }
        }
        cardInfo.addView(txtPesoAltura)

        val referencias = listOf(
            "Abaixo de 18.5 - Abaixo do peso",
            "18.5 a 24.9 - Peso normal",
            "25.0 a 29.9 - Sobrepeso",
            "30.0 ou mais - Obesidade"
        )
        for (ref in referencias) {
            val txtRef = TextView(requireContext()).apply {
                text = ref
                setTextColor(ContextCompat.getColor(context, R.color.texto_secundario))
                textSize = 13f
                setPadding(dpToPx(8), 0, 0, dpToPx(4))
            }
            cardInfo.addView(txtRef)
        }

        container.addView(cardInfo)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
