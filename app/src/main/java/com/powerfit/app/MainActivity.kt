package com.powerfit.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.powerfit.app.ui.*

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNavigation)

        val usuario = com.powerfit.app.data.DatabaseHelper.carregar(this)

        if (usuario.nome.isEmpty()) {
            loadFragment(CadastroFragment(), false)
            bottomNav.visibility = View.GONE
        } else {
            loadFragment(MenuFragment(), false)
            bottomNav.visibility = View.VISIBLE
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_treino -> { loadFragment(TreinoDiaFragment(), false); true }
                R.id.nav_semana -> { loadFragment(TreinoSemanaFragment(), false); true }
                R.id.nav_timer -> { loadFragment(TimerFragment(), false); true }
                R.id.nav_historico -> { loadFragment(HistoricoFragment(), false); true }
                R.id.nav_perfil -> { loadFragment(PerfilFragment(), false); true }
                else -> false
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            when (fragment) {
                is MenuFragment, is CadastroFragment -> {
                    bottomNav.visibility = if (fragment is MenuFragment) View.VISIBLE else View.GONE
                }
                is TreinoDiaFragment, is TreinoSemanaFragment, is TimerFragment,
                is HistoricoFragment, is PerfilFragment -> {
                    bottomNav.visibility = View.VISIBLE
                    updateBottomNavSelection(fragment)
                }
                else -> {
                    bottomNav.visibility = View.GONE
                }
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun loadFragment(fragment: Fragment, addToStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.fragmentContainer, fragment)
        if (addToStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    private fun updateBottomNavSelection(fragment: Fragment) {
        val itemId = when (fragment) {
            is TreinoDiaFragment -> R.id.nav_treino
            is TreinoSemanaFragment -> R.id.nav_semana
            is TimerFragment -> R.id.nav_timer
            is HistoricoFragment -> R.id.nav_historico
            is PerfilFragment -> R.id.nav_perfil
            else -> return
        }
        bottomNav.setOnItemSelectedListener(null)
        bottomNav.selectedItemId = itemId
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_treino -> { loadFragment(TreinoDiaFragment(), false); true }
                R.id.nav_semana -> { loadFragment(TreinoSemanaFragment(), false); true }
                R.id.nav_timer -> { loadFragment(TimerFragment(), false); true }
                R.id.nav_historico -> { loadFragment(HistoricoFragment(), false); true }
                R.id.nav_perfil -> { loadFragment(PerfilFragment(), false); true }
                else -> false
            }
        }
    }

    fun navigateTo(fragment: Fragment) {
        loadFragment(fragment, true)
    }

    fun hideBottomNav() {
        bottomNav.visibility = View.GONE
    }

    fun showBottomNav() {
        bottomNav.visibility = View.VISIBLE
    }
}
