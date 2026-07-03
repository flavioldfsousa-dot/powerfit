package com.powerfit.app.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.powerfit.app.MainActivity
import com.powerfit.app.R
import com.powerfit.app.data.AssistenteIA

class AssistenteFragment : Fragment() {

    private lateinit var containerChat: LinearLayout
    private lateinit var scrollChat: ScrollView
    private lateinit var inputMensagem: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_assistente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<TextView>(R.id.txtTituloHeader).text = "ASSISTENTE IA"

        containerChat = view.findViewById(R.id.containerChat)
        scrollChat = view.findViewById(R.id.scrollChat)
        inputMensagem = view.findViewById(R.id.inputMensagem)
        val btnEnviar = view.findViewById<Button>(R.id.btnEnviar)

        btnEnviar.setOnClickListener { enviarMensagem() }

        inputMensagem.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                enviarMensagem()
                true
            }
            false
        }

        adicionarMensagemBot(
            "Ola! Sou seu assistente de treinos. Pode perguntar sobre:\n\n" +
                    "- Treinos e exercicios\n" +
                    "- Alimentacao e hidratacao\n" +
                    "- Descanso e recuperacao\n" +
                    "- Motivacao\n" +
                    "- IMC e saude\n\n" +
                    "Como posso te ajudar?",
            listOf("Treino de hoje", "Me motive", "Dica de alimentacao")
        )
    }

    private fun enviarMensagem() {
        val texto = inputMensagem.text.toString().trim()
        if (texto.isEmpty()) return

        adicionarMensagemUsuario(texto)
        inputMensagem.text.clear()

        val resposta = AssistenteIA.responder(texto, requireContext())

        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            adicionarMensagemBot(resposta.texto, resposta.sugestoes)
        }, 300)
    }

    private fun adicionarMensagemUsuario(texto: String) {
        val card = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(16), dpToPx(8), dpToPx(16), dpToPx(8))
            gravity = Gravity.END
        }

        val txt = TextView(requireContext()).apply {
            this.text = texto
            setTextColor(Color.WHITE)
            textSize = 15f
            setPadding(dpToPx(16), dpToPx(12), dpToPx(16), dpToPx(12))
            background = ContextCompat.getDrawable(context, R.drawable.bg_btn_accent)
            maxWidth = (resources.displayMetrics.widthPixels * 0.75).toInt()
        }

        card.addView(txt)
        containerChat.addView(card)
        scrollChat.post { scrollChat.fullScroll(View.FOCUS_DOWN) }
    }

    private fun adicionarMensagemBot(texto: String, sugestoes: List<String> = emptyList()) {
        val card = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(16), dpToPx(8), dpToPx(16), dpToPx(8))
            gravity = Gravity.START
        }

        val txt = TextView(requireContext()).apply {
            this.text = texto.replace("**", "")
            setTextColor(ContextCompat.getColor(context, R.color.texto))
            textSize = 15f
            setPadding(dpToPx(16), dpToPx(12), dpToPx(16), dpToPx(12))
            background = ContextCompat.getDrawable(context, R.drawable.bg_card)
            maxWidth = (resources.displayMetrics.widthPixels * 0.85).toInt()
        }

        card.addView(txt)

        if (sugestoes.isNotEmpty()) {
            val sugestaoLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, dpToPx(8), 0, 0)
                gravity = Gravity.START
            }

            for (sugestao in sugestoes.take(3)) {
                val btn = Button(requireContext()).apply {
                    text = sugestao
                    setTextColor(ContextCompat.getColor(context, R.color.accent))
                    setBackgroundColor(Color.TRANSPARENT)
                    textSize = 12f
                    setPadding(dpToPx(8), dpToPx(4), dpToPx(8), dpToPx(4))
                    isAllCaps = false
                    typeface = Typeface.DEFAULT_BOLD
                    setOnClickListener {
                        inputMensagem.setText(sugestao)
                        enviarMensagem()
                    }
                }
                sugestaoLayout.addView(btn)
            }

            card.addView(sugestaoLayout)
        }

        containerChat.addView(card)
        scrollChat.post { scrollChat.fullScroll(View.FOCUS_DOWN) }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
