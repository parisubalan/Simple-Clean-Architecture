package com.dev.cleanarchitecture

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

class Utils(private val context: Context) {
    var progressBar: Dialog
    val TOKEN_STATUS = "access expired"
    var userLatLang: String? = null

    init {
        progressBar = customProgressDialog()
    }

    private fun customProgressDialog(): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.progress_bar_dialog)
        if (dialog.window != null) {
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun shortToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun moveNextActivity(context: Context, intent: Intent?, needToFinish: Boolean) {
        context.startActivity(intent)
        if (needToFinish) {
            (context as Activity).finish()
        }
    }

    val isNetworkAvailable: Boolean
        get() {
            val isConnect: Boolean
            val manager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            isConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                ?.state == NetworkInfo.State.CONNECTED || manager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI
            )?.state == NetworkInfo.State.CONNECTED
            return isConnect
        }

    fun getAddress(location : Location):String{
        var addressesLine = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(location.latitude,location.longitude,1)?.get(0)
            addressesLine = addresses!!.getAddressLine(0)
            if (!TextUtils.isEmpty(addressesLine))
                return addressesLine
        }catch (e:Exception){
            e.printStackTrace()
        }
        return ""
    }

    fun restartActivity(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 22) {
            activity.startActivity(activity.intent)
            activity.finish()
            activity.overridePendingTransition(0, 0)
        } else {
            activity.finish()
            activity.startActivity(activity.intent)
        }
    }

    fun getCurrentTime(): String {
        return DateFormat.format("HH:mm:ss", System.currentTimeMillis()).toString()
    }

    fun getCurrentDate(): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(System.currentTimeMillis())
    }

    fun getTimeStamp(): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(System.currentTimeMillis())
    }

    fun decimalFormat(value: Float): String? {
        val decimalFormat = DecimalFormat("0.00")
        return decimalFormat.format(value.toDouble())
    }

    fun dateFormat(value : String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(value)
    }

    fun showProgressBar(){
        progressBar.show()
    }

    fun dismissProgressBar(){
        if (progressBar.isShowing)
            progressBar.dismiss()
    }
}