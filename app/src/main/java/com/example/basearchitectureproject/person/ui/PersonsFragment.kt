package com.example.basearchitectureproject.person.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.basearchitectureproject.R
import com.example.basearchitectureproject.base.BaseFragment
import com.example.basearchitectureproject.common.extentions.disableShowHideAnimation
import com.example.basearchitectureproject.databinding.FragmentPersonsBinding
import com.example.basearchitectureproject.person.adopter.PersonsAdopter
import com.example.basearchitectureproject.person.viewmodel.PersonsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class PersonsFragment : BaseFragment<FragmentPersonsBinding>() {

    private val personsViewModel by viewModels<PersonsViewModel>()

    override fun OnCreateView(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding!!.viewModel = personsViewModel
        dataBinding!!.adapter = PersonsAdopter(personsViewModel)
        observeLiveDataObjects(view)
    }

    private fun observeLiveDataObjects(view: View) {
        personsViewModel.addPersonAction.observe(viewLifecycleOwner) {
            addFragment(
                android.R.id.content,
                AddPersonFragment(),
                AddPersonFragment::class.java.simpleName
            )
        }

        personsViewModel.personDetailAction.observe(viewLifecycleOwner) { person ->
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(
                PersonsFragmentDirections.actionPersonsFragmentToPersonDetailsFragment(person)
            )
        }


    }


    private fun addFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        fragmentTag: String
    ) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(containerViewId, fragment, fragmentTag)
            .addToBackStack(null)
            .commit()
    }

    override fun getlayout() = R.layout.fragment_persons

}