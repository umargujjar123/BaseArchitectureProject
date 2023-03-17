package com.example.basearchitectureproject.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.snackbar.Snackbar
import com.example.basearchitectureproject.R

abstract class BaseFragment<DB : ViewDataBinding?> : Fragment(),
    LifecycleOwner {
    var dataBinding: DB? = null
    protected var mContext: Context? = null
    private lateinit var loader: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        OnCreateView(inflater, savedInstanceState)
        dataBinding = DataBindingUtil.inflate(inflater, getlayout(), container, false)
        mContext = activity
        dataBinding!!.lifecycleOwner = this

        loader = Dialog(requireContext())
        loader.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loader.setContentView(R.layout.loader)
        loader.setCancelable(false)
        loader.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dataBinding!!.root
    }

    fun isConnectedToInternet(): Boolean {
        val connectivity =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivity.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnectedOrConnecting) {

        } else {
            showErrorSnakbar("Check your internet connection and try again")
        }
        return networkInfo != null && networkInfo.isConnectedOrConnecting

    }

    abstract fun OnCreateView(inflater: LayoutInflater?, savedInstanceState: Bundle?)
    abstract fun getlayout(): Int

    open fun showErrorSnakbar(Error: String) {
        val snackbar = Snackbar.make(
            requireActivity().findViewById(android.R.id.content), Error,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.RED)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.WHITE)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.RED)
        textView.textSize = 15f
        snackbar.show()
    }

    open fun showSucessSnakbar(Error: String) {
        val snackbar = Snackbar.make(
            requireActivity().findViewById(android.R.id.content), Error,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.WHITE)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(resources.getColor(R.color.tiny_dot_orange_color5))
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.textSize = 15f
        snackbar.show()
    }

    open fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

    open fun showLoader() {
        if (!loader.isShowing) loader.show()
    }

    open fun hideLoader() {
        if (loader.isShowing) loader.dismiss()
    }

    override fun onDestroy() {
        hideLoader()
        super.onDestroy()
    }
}
