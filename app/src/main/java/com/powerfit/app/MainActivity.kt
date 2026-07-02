package com.powerfit.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.powerfit.app.ui.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        val usuario = com.powerfit.app.data.DatabaseHelper.carregar(this)

        if (usuario.nome.isEmpty()) {
            loadFragment(CadastroFragment())
        } else {
            loadFragment(MenuFragment())
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_treino -> { loadFragment(TreinoDiaFragment()); true }
                R.id.nav_semana -> { loadFragment(TreinoSemanaFragment()); true }
                R.id.nav_timer -> { loadFragment(TimerFragment()); true }
                R.id.nav_historico -> { loadFragment(HistoricoFragment()); true }
                R.id.nav_perfil -> { loadFragment(PerfilFragment()); true }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
