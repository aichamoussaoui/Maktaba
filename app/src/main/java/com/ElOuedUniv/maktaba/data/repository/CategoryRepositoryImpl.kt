package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category

class CategoryRepositoryImpl : CategoryRepository {

    private val categoriesList = listOf(
        Category(
            id = "1",
            name = "Programming",
            description = "Books about software development and coding",
            bookCount = 3
        ),
        Category(
            id = "2",
            name = "Algorithms",
            description = "Books about algorithms and data structures",
            bookCount = 2
        ),
        Category(
            id = "3",
            name = "Databases",
            description = "Books about database design and management",
            bookCount = 4
        ),
        Category(
            id = "4",
            name = "AI",
            description = "Books about AI and machine learning",
            bookCount = 10
        ),
        Category(
            id = "5",
            name = "Computer Networks",
            description = "Books about networking and communication",
            bookCount = 6
        )
    )

    override fun getAllCategories(): List<Category> {
        return categoriesList.sortedBy { it.name }
    }

    override fun getCategoryById(id: String): Category? {
        return categoriesList.find { it.id == id }
    }
}