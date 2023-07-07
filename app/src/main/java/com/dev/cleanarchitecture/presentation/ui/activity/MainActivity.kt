package com.dev.cleanarchitecture.presentation.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.dev.cleanarchitecture.Utils
import com.dev.cleanarchitecture.databinding.ActivityMainBinding
import com.dev.cleanarchitecture.domain.model.request.LoginReq
import com.dev.cleanarchitecture.preference.MySharedPreference
import com.dev.cleanarchitecture.presentation.viewmodel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var utils: Utils
    private lateinit var mySession: MySharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialization()
    }

    private fun initialization() {
        viewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        utils = Utils(this)
        mySession = MySharedPreference(this)
        binding.loginBtn.apply {
            setOnClickListener {
                viewModel.login(LoginReq("EMPIA00005"))
            }
        }

        viewModel.loginResponse.observe(this) {
            if (it.isLoading) {
                utils.showProgressBar()
            }
            if (it.data != null) {
                utils.dismissProgressBar()
                println("--->>>>>> Status : ${it.data.status}")
                println("--->>>>>> Message : ${it.data.message}")
                Toast.makeText(this, it.data.status, Toast.LENGTH_SHORT).show()
            }

            if (!TextUtils.isEmpty(it.error)) {
                utils.dismissProgressBar()
                println("--->>>>>> Error : ${it.error}")
                Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(sessionBroadCastReceiver, IntentFilter("session_expired"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(sessionBroadCastReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(sessionBroadCastReceiver)
    }

    private val sessionBroadCastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            mySession.clearMySession()
            utils.shortToast("Log out...")
        }

    }
}