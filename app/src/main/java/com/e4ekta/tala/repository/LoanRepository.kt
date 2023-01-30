package com.e4ekta.tala.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.e4ekta.tala.paging.LoanRecordsPagingSource
import com.e4ekta.tala.retrofit.LoanAPI
import javax.inject.Inject

class LoanRepository @Inject constructor(private val loanAPI: LoanAPI) {

    fun getLoanRecords() = Pager(
        config = PagingConfig(pageSize = 4, maxSize = 15),
        pagingSourceFactory = { LoanRecordsPagingSource(loanAPI) }
    ).liveData
}
