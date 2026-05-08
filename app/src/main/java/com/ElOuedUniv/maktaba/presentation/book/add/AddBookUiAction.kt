package com.ElOuedUniv.maktaba.presentation.book.add

import android.net.Uri

sealed interface AddBookUiAction {
    data class OnTitleChange(val title: String) : AddBookUiAction
    data class OnIsbnChange(val isbn: String) : AddBookUiAction
    data class OnPagesChange(val pages: String) : AddBookUiAction
    data class OnImageSelected(val imageUri: String?) : AddBookUiAction

    data class OnImagePicked(val uri: Uri?) : AddBookUiAction
    object OnAddClick : AddBookUiAction
}
