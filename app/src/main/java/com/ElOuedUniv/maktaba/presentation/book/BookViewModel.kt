package com.ElOuedUniv.maktaba.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _isAddingBook = MutableStateFlow(false)
    val isAddingBook: StateFlow<Boolean> = _isAddingBook.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
                getBooksUseCase().catch {
                    _isLoading.value = false
                }.collect { bookList ->
                    _books.value = bookList
                    _isLoading.value = false
                }

        }
    }

    /**
     * TODO: Exercise 3 - Handle UI Actions
     */
    fun onAction(action: BookUiAction) {
        when (action) {
            BookUiAction.RefreshBooks -> refreshBooks()
            BookUiAction.OnAddBookClick -> {
                _isAddingBook.value = true
            }
            BookUiAction.OnDismissAddBook -> {
                _isAddingBook.value = false
            }
            is BookUiAction.OnAddBookConfirm -> {
                viewModelScope.launch {
                    addBookUseCase(
                        Book(
                            title = action.title,
                            isbn = action.isbn,
                            nbPages = action.nbPages
                        )
                    )
                }
                _isAddingBook.value = false
            }
        }
    }

    fun refreshBooks() {
        loadBooks()
    }
    fun addBook(book: Book) {
        val current = _books.value.toMutableList()
        current.add(book)
        _books.value = current
    }
}

