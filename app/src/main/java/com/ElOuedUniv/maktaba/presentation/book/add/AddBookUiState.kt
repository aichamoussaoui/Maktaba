package com.ElOuedUniv.maktaba.presentation.book.add

import android.net.Uri
data class AddBookUiState(
    val title: String = "",
    val isbn: String = "",
    val nbPages: String = "",
    val imageUri: Uri? = null,
    val selectedImageUri: String? = null,
    val titleError: String? = null,
    val isbnError: String? = null,
    val nbPagesError: String? = null,
    val isFormValid: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
