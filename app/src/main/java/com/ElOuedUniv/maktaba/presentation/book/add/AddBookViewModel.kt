package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: AddBookUiAction) {
        when (action) {
            is AddBookUiAction.OnTitleChange -> {
                _uiState.update { it.copy(title = action.title, errorMessage = null) }
                validateInputs()
            }
            is AddBookUiAction.OnIsbnChange -> {
                _uiState.update { it.copy(isbn = action.isbn, errorMessage = null) }
                validateInputs()
            }
            is AddBookUiAction.OnPagesChange -> {
                _uiState.update { it.copy(nbPages = action.pages, errorMessage = null) }
                validateInputs()
            }
            is AddBookUiAction.OnImageSelected -> {
                _uiState.update { it.copy(selectedImageUri = action.imageUri, errorMessage = null) }
            }
            is AddBookUiAction.OnImagePicked -> {
                _uiState.update { it.copy(imageUri = action.uri, errorMessage = null) }
            }
            AddBookUiAction.OnAddClick -> {
                validateInputs()
                if (_uiState.value.isFormValid) {
                    addBook()
                }
            }
        }
    }

    private fun validateInputs() {
        val current = _uiState.value

        val titleError = if (current.title.isBlank()) "اسم الكتاب مطلوب" else null

        val isbnError = when {
            current.isbn.isBlank() -> "رقم ISBN مطلوب"
            current.isbn.length != 10 && current.isbn.length != 13 -> "يجب أن يكون 10 أو 13 رقماً"
            current.isbn.any { !it.isDigit() } -> "يجب إدخال أرقام فقط"
            else -> null
        }

        val pagesInt = current.nbPages.toIntOrNull()
        val nbPagesError = when {
            current.nbPages.isBlank() -> "عدد الصفحات مطلوب"
            pagesInt == null || pagesInt <= 0 -> "يجب إدخال رقم صحيح أكبر من 0"
            else -> null
        }

        val valid = titleError == null && isbnError == null && nbPagesError == null
        
        android.util.Log.d("AddBookVM", "Validation: title=$titleError, isbn=$isbnError, pages=$nbPagesError, isValid=$valid")

        _uiState.update {
            it.copy(
                titleError = titleError,
                isbnError = isbnError,
                nbPagesError = nbPagesError,
                isFormValid = valid
            )
        }
    }

    private fun addBook() {
        val current = _uiState.value
        android.util.Log.d("AddBookVM", "Starting to add book: ${current.title}")
        
        val book = Book(
            isbn = current.isbn,
            title = current.title,
            nbPages = current.nbPages.toIntOrNull() ?: 0,
            imageUrl = current.selectedImageUri
        )
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                addBookUseCase(book, current.imageUri)
                android.util.Log.d("AddBookVM", "Book added successfully")
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                android.util.Log.e("AddBookVM", "Error adding book", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "فشل في إضافة الكتاب"
                    )
                }
            }
        }
    }
}
