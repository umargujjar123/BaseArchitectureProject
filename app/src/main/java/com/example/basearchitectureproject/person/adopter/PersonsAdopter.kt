package com.example.basearchitectureproject.person.adopter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.basearchitectureproject.data.Person
import com.example.basearchitectureproject.databinding.PersonRowDesignBinding
import com.example.basearchitectureproject.person.viewmodel.PersonsViewModel

class PersonsAdopter(val viewModel: PersonsViewModel) :
    ListAdapter<Person, RecyclerView.ViewHolder>(PersonDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = getItem(position)
        (holder as ViewHolder).bind(item, viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(
        val binding: PersonRowDesignBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Person,
            viewModel: PersonsViewModel
        ) {
            binding.person = item

            binding.parentPanal.setOnClickListener {
                viewModel.personClickListener(item)
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecyclerView.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PersonRowDesignBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(
        oldItem: Person,
        newItem: Person
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: Person,
        newItem: Person
    ): Boolean {
        return oldItem == newItem
    }
}