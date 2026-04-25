package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class BookRepositoryImpl @Inject constructor() : BookRepository {

    private val _booksList = mutableListOf(
        Book(
            isbn = "9780132350884",
            title = "Clean Code",
            nbPages = 464,
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780132350884-L.jpg"
        ),
        Book(
            isbn = "9780135957059",
            title = "The Pragmatic Programmer",
            nbPages = 352,
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780135957059-L.jpg"
        ),
        Book(
            isbn = "9780201633610",
            title = "Design Patterns",
            nbPages = 395,
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780201633610-L.jpg"
        ),
        Book(
            isbn = "9780133065268",
            title = "Refactoring",
            nbPages = 448,
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780133065268-L.jpg"
        ),
        Book(
            isbn = "9780596007126",
            title = "Head First Design Patterns",
            nbPages = 688,
            imageUrl = "https://covers.openlibrary.org/b/isbn/9780596007126-L.jpg"
        )
    )
    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(_booksList.toList())
    }
    
    override fun getAllBooks(): Flow<List<Book>> = flow {
        delay(2000) // Simulate delay
        emitAll(booksFlow)
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return _booksList.find { it.isbn == isbn }
    }

    override fun addBook(book: Book) {
        _booksList.add(book)
        booksFlow.tryEmit(_booksList.toList())
    }

    override fun deleteBook(book: Book) {
        val removed = _booksList.removeIf { it.isbn == book.isbn }
        if (removed) {
            booksFlow.tryEmit(_booksList.toList())
        }
    }
    override fun updateBook(book: Book) {
        val index = _booksList.indexOfFirst { it.isbn == book.isbn }
        if (index != -1) {
            _booksList[index] = book
            booksFlow.tryEmit(_booksList.toList())
        }
    }

}
