package com.example.e_commerceapp.domain.usecases.categoryUseCases

import com.example.e_commerceapp.domain.repositories.CategoryRepository
import javax.inject.Inject

class GetSampleCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke() = categoryRepository.getSampleCategories()
}