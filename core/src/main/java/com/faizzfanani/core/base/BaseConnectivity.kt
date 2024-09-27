package com.faizzfanani.core.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.faizzfanani.core.base.BaseConnectivity.Companion.DEFAULT_DURATION
import com.faizzfanani.core.utils.Event

class BaseConnectivity(val context: Context) : LiveData<Event<Boolean>>() {

    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    private var connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private var state = false

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateConnection()
        }
    }

    override fun onActive() {
        super.onActive()
        updateConnection()
        connectivityManager.registerDefaultNetworkCallback(getCallback())
        context.registerReceiver(networkReceiver, IntentFilter(CONNECTIVITY_ACTION))
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
        context.unregisterReceiver(networkReceiver)
    }

    private fun getCallback(): ConnectivityManager.NetworkCallback {
        connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (!state) {
                    state = true
                    postValue(Event(state))
                }
            }

            override fun onLost(network: Network) {
                state = false
                postValue(Event(state))
            }
        }
        return connectivityManagerCallback
    }

    private fun updateConnection() {
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (state != activeNetwork?.isConnected)
            postValue(Event(activeNetwork?.isConnected == true))
    }

    companion object {
        const val DEFAULT_DURATION = 1000L
        const val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    }
}

fun <T> LiveData<T>.debounce(duration: Long = DEFAULT_DURATION) = MediatorLiveData<T>().also {
    val handler = Handler(Looper.getMainLooper())
    val runnable = Runnable { it.value = this.value }
    it.addSource(this) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, duration)
    }
}
