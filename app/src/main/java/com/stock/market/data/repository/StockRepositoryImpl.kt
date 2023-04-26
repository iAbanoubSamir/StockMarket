package com.stock.market.data.repository

import com.stock.market.data.local.StockDatabase
import com.stock.market.data.mappers.toCompanyListing
import com.stock.market.data.remote.StockApi
import com.stock.market.domain.model.CompanyListing
import com.stock.market.domain.repository.StockRepository
import com.stock.market.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDatabaseEmpty = localListings.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDatabaseEmpty && !fetchFromRemote

            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()

            } catch (e: IOException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: HttpException) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}