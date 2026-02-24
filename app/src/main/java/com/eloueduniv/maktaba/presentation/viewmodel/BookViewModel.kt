package com.eloueduniv.maktaba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eloueduniv.maktaba.data.model.Book
import com.eloueduniv.maktaba.domain.usecase.GetBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing book-related UI state
 * This follows the MVVM pattern where ViewModel acts as a bridge between
 * the UI and the business logic (Use Cases)
 */
class BookViewModel(
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {

    // Private mutable state for internal use
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    
    // Public immutable state for UI observation
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        // Load books when ViewModel is created
        loadBooks()
    }

    /**
     * Load all books from the use case
     */
    private fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val bookList = getBooksUseCase()
                _books.value = bookList
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Bonus 2
    fun totalPages(): Int {
        return books.value.sumOf { it.nbPages }
    }

    fun loadLongBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _books.value = getBooksUseCase.getBooksMoreThan400Pages()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery

        viewModelScope.launch {
            _isLoading.value = true
            try {
                _books.value = getBooksUseCase.searchBooks(newQuery)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Refresh the books list
     * Can be called from UI to reload data
     */
    fun refreshBooks() {
        loadBooks()
    }
}
