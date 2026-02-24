package com.eloueduniv.maktaba.domain.usecase

import com.eloueduniv.maktaba.data.model.Book
import com.eloueduniv.maktaba.data.repository.BookRepository

/**
 * Use Case for getting books
 * This follows the Clean Architecture principle of separating business logic
 *
 * Use Cases contain the business logic of the application and are independent
 * of the UI and data sources.
 */
class GetBooksUseCase(
    private val bookRepository: BookRepository
) {
    /**
     * Execute the use case to get all books
     * @return List of all books from the repository
     */
    operator fun invoke(): List<Book> {
        return bookRepository.getAllBooks()
    }

    // Optional business logic example (filter > 400 pages)
    fun getBooksMoreThan400Pages(): List<Book> {
        return bookRepository.getAllBooks().filter { it.nbPages > 400 }
    }

    fun searchBooks(query: String): List<Book> {
        return bookRepository.searchBooks(query)
    }
}
