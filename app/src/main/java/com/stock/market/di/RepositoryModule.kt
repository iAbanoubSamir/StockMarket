package com.stock.market.di

import com.stock.market.data.csv.CSVParser
import com.stock.market.data.csv.CompanyListingParser
import com.stock.market.data.repository.StockRepositoryImpl
import com.stock.market.domain.model.CompanyListing
import com.stock.market.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Singleton
    @Binds
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}