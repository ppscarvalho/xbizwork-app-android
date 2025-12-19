package com.br.xbizitwork.data.di.subcategory

import com.br.xbizitwork.domain.repository.SubcategoryRepository
import com.br.xbizitwork.domain.usecase.subcategory.GetSubcategoriesByCategoryUseCase
import com.br.xbizitwork.domain.usecase.subcategory.GetSubcategoriesByCategoryUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubcategoryUseCaseModule {

    @Provides
    @Singleton
    fun provideGetSubcategoriesByCategoryUseCase(
        repository: SubcategoryRepository
    ): GetSubcategoriesByCategoryUseCase {
        return GetSubcategoriesByCategoryUseCaseImpl(repository)
    }
}
