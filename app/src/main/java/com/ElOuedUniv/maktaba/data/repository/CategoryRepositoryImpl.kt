package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {

    private val categoriesList = listOf(
        Category(
            id = "1",
            name = "Programming",
            description = "Books about software development and coding",
            imageUrl = "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?auto=format&fit=crop&q=80&w=400",
            iconRes = null
        ),
        Category(
            id = "2",
            name = "Algorithms",
            description = "Books about algorithms and data structures",
            imageUrl = "https://images.unsplash.com/photo-1504384308090-c894fdcc538d?auto=format&fit=crop&q=80&w=400",
            iconRes = null
        ),
        Category(
            id = "3",
            name = "Databases",
            description = "Books about database design and management",
            imageUrl = null,
            iconRes = android.R.drawable.ic_menu_save
        )
    )

    
    override fun getAllCategories(): Flow<List<Category>> = flow {
        delay(1000)
        emit(categoriesList)
        }

    override suspend fun getCategoryById(id: String): Category? {
        return categoriesList.find { it.id == id }
    }
}
