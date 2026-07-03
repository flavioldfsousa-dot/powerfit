package com.powerfit.app.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.powerfit.app.MainActivity
import com.powerfit.app.R

class TimerFragment : Fragment() {

    private var timer: CountDownTimer? = null
    private var tempoTotal = 0L
    private var rodando = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<TextView>(R.id.txtTituloHeader).text = "TIMER"

        val txtTempo = view.findViewById<TextView>(R.id.txtTempo)
        txtTempo.setTextColor(ContextCompat.getColor(requireContext(), R.color.accent))

        val btnIniciar = view.findViewById<Button>(R.id.btnIniciar)
        val btnResetar = view.findViewById<Button>(R.id.btnResetar)

        btnIniciar.setOnClickListener {
            if (rodando) {
                timer?.cancel()
                rodando = false
                btnIniciar.text = "CONTINUAR"
            } else {
                rodando = true
                btnIniciar.text = "PAUSAR"
                timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        tempoTotal++
                        val min = tempoTotal / 60
                        val seg = tempoTotal % 60
                        txtTempo.text = String.format("%02d:%02d", min, seg)
                    }

                    override fun onFinish() {}
                }.start()
            }
        }

        btnResetar.setOnClickListener {
            timer?.cancel()
            rodando = false
            tempoTotal = 0L
            txtTempo.text = "00:00"
            btnIniciar.text = "INICIAR"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }
}
