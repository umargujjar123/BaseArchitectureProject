package com.example.basearchitectureproject.user_details.ui

//import com.theartofdev.edmodo.cropper.CropImage

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.basearchitectureproject.R
import com.example.basearchitectureproject.base.BaseActivity
import com.example.basearchitectureproject.databinding.UserDetailsLayoutBinding
import com.example.basearchitectureproject.user_details.database.UserDb
import com.example.basearchitectureproject.user_details.viewmodel.UploadUserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import g5.consultency.cuitalibilam.base.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class UserDetailActivity : BaseActivity<UserDetailsLayoutBinding>() {
    val uploadDetailViewModel: UploadUserDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        initObserver()
        isInternetConnected()
    }

    private fun initObserver() {

        uploadDetailViewModel.rootClick.observe(this@UserDetailActivity) {
            hideKeyboard()
        }

        uploadDetailViewModel.userDetails.observe(this@UserDetailActivity)
        {
            when (it.status) {
                Resource.Status.LOADING -> {
                    showCustomDialogueNewDesign("Restoring data...")
                }
                Resource.Status.SUCCESS -> {
                    Log.e("TAG", "initObserver: The data in the reponse coming is ${it.data}")
                    dataBinding?.userName?.setText(it.data?.userName)
                    dataBinding?.userEmail?.setText(it.data?.email)
                    dataBinding?.userMobile?.setText(it.data?.userMobileNumber)
                    hideCustomDialogueNewDesign()
                    showSucessSnakbar(it.message ?: "")
                }
                Resource.Status.ERROR -> {
                    hideCustomDialogueNewDesign()
                    showErrorSnakbar(it.message ?: "")
                }
                Resource.Status.FAILURE -> {
                    hideCustomDialogueNewDesign()
                    showErrorSnakbar(it.message ?: "")
                }
            }
        }
        uploadDetailViewModel.getDataFromDB.observe(this@UserDetailActivity)
        {


                    dataBinding?.userName?.setText(it.toString())
                    hideCustomDialogueNewDesign()
        }

        uploadDetailViewModel.backBtn.observe(this@UserDetailActivity)
        {
            finish()
        }
        uploadDetailViewModel.initUploadDetailLD.observe(this@UserDetailActivity)
        {
            when (it.status) {
                Resource.Status.LOADING -> {
                    showCustomDialogueNewDesign("Updating Information...")
                }
                Resource.Status.SUCCESS -> {
                    Log.e("TAG", "initObserver: The data in the response is coming", )
                    dataBinding?.userName?.setText(it.data?.userDetails?.userName)
                    dataBinding?.userEmail?.setText(it.data?.userDetails?.email)
                    dataBinding?.userMobile?.setText(it.data?.userDetails?.userMobileNumber)
                    hideCustomDialogueNewDesign()
                    it.message?.let { successMessage ->
                        showSucessSnakbar(successMessage)
                    }
                    lifecycleScope.launch(Dispatchers.IO) {
                        withContext(Dispatchers.Main) {
                        }
                        delay(1500)
                    }
                }
                Resource.Status.ERROR -> {
                    hideCustomDialogueNewDesign()
                    showErrorSnakbar(it.message ?: "")
                }
                Resource.Status.FAILURE -> {
                    hideCustomDialogueNewDesign()
                    showErrorSnakbar(it.message ?: "")
                }
            }
        }


    }

    private fun initDataBinding() {
        with(dataBinding!!) {
            lifecycleOwner = this@UserDetailActivity
            viewmodel = this@UserDetailActivity.uploadDetailViewModel
        }
    }

    override fun getResLayout(): Int {
        return R.layout.user_details_layout
    }

    fun isInternetConnected() {
        val conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        if (netInfo == null) {
            Log.e("TAG", "isInternetConnected() returned: NoInternet Available")
        } else {
            Log.e("TAG", "isInternetConnected() returned: Internet Available")

        }
    }

}