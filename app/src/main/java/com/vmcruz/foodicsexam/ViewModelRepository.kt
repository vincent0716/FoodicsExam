package com.vmcruz.foodicsexam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmcruz.foodicsexam.Repository.Repository
import com.vmcruz.foodicsexam.ViewModel.AppViewModel

class ViewModelRepository(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}