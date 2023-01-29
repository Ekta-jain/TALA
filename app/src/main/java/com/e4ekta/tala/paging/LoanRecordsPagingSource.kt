package com.e4ekta.tala.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

import com.e4ekta.tala.models.LoanResponseItem
import com.e4ekta.tala.retrofit.LoanAPI
import java.lang.Exception

class LoanRecordsPagingSource(private val loanAPI: LoanAPI) : PagingSource<Int, LoanResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LoanResponseItem> {
        return try {
            val position = params.key ?: 1
            val response = loanAPI.getRecords(position,4)

            return LoadResult.Page(
                data = response,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == 5) null else position + 1 //consider total pages here 3
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LoanResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}