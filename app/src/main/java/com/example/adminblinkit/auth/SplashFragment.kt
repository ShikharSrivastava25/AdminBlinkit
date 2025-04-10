package com.example.adminblinkit.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.adminblinkit.activity.AdminMainActivty
import com.example.adminblinkit.R
import com.example.adminblinkit.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private val viewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        SetStatusBarColor()

        // execute the piece of code after delay so that splash screen is visible for 3s
        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                viewModel.isACurrentUser.collect {
                    if (it) {
                        startActivity(Intent(requireActivity(), AdminMainActivty::class.java))
                        requireActivity().finish()
                    } else {
                        findNavController().navigate(R.id.action_splashFragment_to_signinFragment)
                    }
                }
            }
        }, 3000)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun SetStatusBarColor() {
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(), R.color.yellow)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}