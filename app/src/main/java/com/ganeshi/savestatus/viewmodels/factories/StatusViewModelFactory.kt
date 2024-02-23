package com.ganeshi.savestatus.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ganeshi.savestatus.data.StatusRepo
import com.ganeshi.savestatus.viewmodels.StatusViewModel

class StatusViewModelFactory(private  val repo: StatusRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  StatusViewModel(repo) as T
    }
}