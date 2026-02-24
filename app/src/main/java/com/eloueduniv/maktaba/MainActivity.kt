package com.eloueduniv.maktaba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eloueduniv.maktaba.data.repository.BookRepository
import com.eloueduniv.maktaba.domain.usecase.GetBooksUseCase
import com.eloueduniv.maktaba.presentation.screens.BookListScreen
import com.eloueduniv.maktaba.presentation.theme.MaktabaTheme
import com.eloueduniv.maktaba.presentation.viewmodel.BookViewModel

/**
 * Main Activity - Entry point of the application
 * Sets up the MVVM architecture and displays the BookListScreen
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Manual Dependency Injection (Simple approach for learning)
        // In a real app, you would use Hilt or Koin for DI
        val bookRepository = BookRepository()
        val getBooksUseCase = GetBooksUseCase(bookRepository)
        val bookViewModel = BookViewModel(getBooksUseCase)
        
        setContent {
            MaktabaTheme {
                BookListScreen(viewModel = bookViewModel)
            }
        }
    }
}