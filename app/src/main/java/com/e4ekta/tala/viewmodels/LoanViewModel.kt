package com.e4ekta.tala.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.e4ekta.tala.repository.LoanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(private val repository: LoanRepository) : ViewModel() {
    val list = repository.getLoanRecords().cachedIn(viewModelScope)
}
