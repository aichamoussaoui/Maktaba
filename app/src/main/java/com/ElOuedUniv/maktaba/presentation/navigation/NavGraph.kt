package com.ElOuedUniv.maktaba.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ElOuedUniv.maktaba.data.preferences.OnboardingPreferences
import com.ElOuedUniv.maktaba.presentation.book.BookListView
import com.ElOuedUniv.maktaba.presentation.book.add.AddBookView
import com.ElOuedUniv.maktaba.presentation.book.detail.BookDetailView
import com.ElOuedUniv.maktaba.presentation.category.CategoryListView
import com.ElOuedUniv.maktaba.presentation.onboarding.OnboardingView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val onboardingPreferences = remember { OnboardingPreferences(context) }

    val hasCompletedOnboarding by onboardingPreferences
        .hasCompletedOnboarding
        .collectAsState(initial = false)

    val startDestination = if (hasCompletedOnboarding) {
        Screen.BookList.route
    } else {
        Screen.Onboarding.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingView(
                onNavigateToLibrary = {
                    CoroutineScope(Dispatchers.IO).launch {
                        onboardingPreferences.setOnboardingCompleted()
                    }
                    navController.navigate(Screen.BookList.route) {
                        popUpTo(Screen.Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        
        composable(Screen.BookList.route) {
            BookListView(
                onCategoriesClick = { navController.navigate(Screen.CategoryList.route) },
                onAddBookClick = { navController.navigate(Screen.AddBook.route) },
                onBookClick = { isbn -> 
                    navController.navigate(Screen.BookDetail.createRoute(isbn))
                }
            )
        }
        
        composable(Screen.BookDetail.route) {
            BookDetailView(onBackClick = { navController.popBackStack() })
        }
        
        composable(Screen.CategoryList.route) {
            CategoryListView(onBackClick = { navController.popBackStack() })
        }
        
        composable(Screen.AddBook.route) {
            AddBookView(onBackClick = { navController.popBackStack() })
        }
    }
}
