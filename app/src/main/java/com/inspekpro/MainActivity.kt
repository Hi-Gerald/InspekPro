package com.inspekpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Rect
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    @JvmField
    var firebaseAuth: FirebaseAuth? = null

    private var currentSelectedTabId = R.id.tabDashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<View>(R.id.bottomNavContainer)
        val fabAdd = findViewById<View>(R.id.fabAdd)

        // Start LogoutService to handle logout on app exit
        val intent = Intent(this, com.inspekpro.service.LogoutService::class.java)
        startService(intent)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main_root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            val density = resources.displayMetrics.density
            
            bottomNav.setPadding(0, 0, 0, systemBars.bottom)
            
            val lpFab = fabAdd.layoutParams as ViewGroup.MarginLayoutParams
            lpFab.bottomMargin = (32 * density).toInt() + systemBars.bottom
            fabAdd.layoutParams = lpFab

            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        val isFirebaseLoggedIn = try {
            firebaseAuth?.currentUser != null
        } catch (_: Exception) {
            false
        }

        if (isFirebaseLoggedIn) {
            navGraph.setStartDestination(R.id.dashboardFragment)
        } else {
            navGraph.setStartDestination(R.id.loginFragment)
        }
        navController.graph = navGraph

        setupBottomNavigation(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val showNav = when (destination.id) {
                R.id.dashboardFragment,
                R.id.inspectionListFragment,
                R.id.reportFragment,
                R.id.profileFragment -> true
                else -> false
            }
            
            bottomNav.visibility = if (showNav) View.VISIBLE else View.GONE
            fabAdd.visibility = if (showNav) View.VISIBLE else View.GONE
            
            if (showNav) {
                updateTabUI(when (destination.id) {
                    R.id.dashboardFragment -> R.id.tabDashboard
                    R.id.inspectionListFragment -> R.id.tabInspeksi
                    R.id.reportFragment -> R.id.tabLaporan
                    R.id.profileFragment -> R.id.tabAkun
                    else -> currentSelectedTabId
                })
            }
        }
        
        fabAdd.setOnClickListener {
            navController.navigate(R.id.addInspectionFragment)
        }
    }

    private fun setupBottomNavigation(navController: androidx.navigation.NavController) {
        val tabDashboard = findViewById<View>(R.id.tabDashboard)
        val tabInspeksi = findViewById<View>(R.id.tabInspeksi)
        val tabLaporan = findViewById<View>(R.id.tabLaporan)
        val tabAkun = findViewById<View>(R.id.tabAkun)

        val clickListener = View.OnClickListener { v ->
            val clickedTabId = v.id
            if (clickedTabId == currentSelectedTabId) return@OnClickListener

            when (clickedTabId) {
                R.id.tabDashboard -> {
                    navController.navigate(R.id.dashboardFragment)
                }
                R.id.tabInspeksi -> {
                    navController.navigate(R.id.inspectionListFragment)
                }
                R.id.tabLaporan -> {
                    navController.navigate(R.id.reportFragment)
                }
                R.id.tabAkun -> {
                    navController.navigate(R.id.profileFragment)
                }
            }
        }

        tabDashboard.setOnClickListener(clickListener)
        tabInspeksi.setOnClickListener(clickListener)
        tabLaporan.setOnClickListener(clickListener)
        tabAkun.setOnClickListener(clickListener)
    }

    private fun updateTabUI(newTabId: Int) {
        if (newTabId == currentSelectedTabId) return
        
        val grayColor = ContextCompat.getColor(this, R.color.text_secondary)
        val blueColor = ContextCompat.getColor(this, R.color.primary)

        val tabViews = listOf(
            Triple(R.id.tabDashboard, R.id.ivTabDashboard, R.id.tvTabDashboard),
            Triple(R.id.tabInspeksi, R.id.ivTabInspeksi, R.id.tvTabInspeksi),
            Triple(R.id.tabLaporan, R.id.ivTabLaporan, R.id.tvTabLaporan),
            Triple(R.id.tabAkun, R.id.ivTabAkun, R.id.tvTabAkun)
        )

        for (tab in tabViews) {
            val (id, ivId, tvId) = tab
            val imageView = findViewById<ImageView>(ivId)
            val textView = findViewById<TextView>(tvId)

            if (id == newTabId) {
                imageView.setColorFilter(blueColor)
                textView.setTextColor(blueColor)
                textView.setTypeface(null, android.graphics.Typeface.BOLD)
            } else {
                imageView.setColorFilter(grayColor)
                textView.setTextColor(grayColor)
                textView.setTypeface(null, android.graphics.Typeface.NORMAL)
            }
        }
        currentSelectedTabId = newTabId
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}