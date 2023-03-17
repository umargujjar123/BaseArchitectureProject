package com.example.basearchitectureproject.person.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.basearchitectureproject.R
import com.example.basearchitectureproject.base.BaseFragment
import com.example.basearchitectureproject.common.extentions.disableShowHideAnimation
import com.example.basearchitectureproject.data.Person
import com.example.basearchitectureproject.databinding.FragmentAddPersonBinding
import com.example.basearchitectureproject.person.adopter.PersonsAdopter
import com.example.basearchitectureproject.person.viewmodel.AddPersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class AddPersonFragment : BaseFragment<FragmentAddPersonBinding>(){

    private val addPersonsViewModel by viewModels<AddPersonViewModel>()


    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding?.let {
            with(it) {
                lifecycleOwner = this@AddPersonFragment
                viewModel = addPersonsViewModel
            }
        }


        addPersonsViewModel.addPersonAction.observe(viewLifecycleOwner) {
            Log.e("TAG", "OnCreateView --> addPersonAction: called")
            addPersonsViewModel.addPerson(Person(name = dataBinding!!.pName.text.toString(), age = dataBinding!!.pAge.text.toString().toDouble()))
        }

        addPersonsViewModel.loading.observe(viewLifecycleOwner) {
            if (it) showLoader() else hideLoader()
        }

    }

    override fun getlayout() = R.layout.fragment_add_person
    override fun OnCreateView(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
    }

}