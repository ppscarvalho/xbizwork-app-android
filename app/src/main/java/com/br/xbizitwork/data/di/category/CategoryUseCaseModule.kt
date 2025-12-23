package com.br.xbizitwork.data.di.category

import com.br.xbizitwork.domain.repository.CategoryRepository
import com.br.xbizitwork.domain.usecase.category.GetAllCategoryUseCase
import com.br.xbizitwork.domain.usecase.category.GetAllCategoryUseCaseImpl
import com.br.xbizitwork.domain.usecase.category.GetCategoriesUseCase
import com.br.xbizitwork.domain.usecase.category.GetCategoriesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryUseCaseModule {

    @Provides
    @Singleton
    fun provideGetAllCategoryUseCase(
        repository: CategoryRepository
    ) : GetAllCategoryUseCase {
        return GetAllCategoryUseCaseImpl(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(
        repository: CategoryRepository
    ): GetCategoriesUseCase {
        return GetCategoriesUseCaseImpl(repository)
    }
}