package com.ElOuedUniv.maktaba.presentation.book.add

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookView(
    onBackClick: () -> Unit,
    viewModel: AddBookViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val pink = Color(0xFFE91E63)
    val lightPink = Color(0xFFF8BBD0)
    val softPink = Color(0xFFFCE4EC)

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onAction(AddBookUiAction.OnImagePicked(uri))
        viewModel.onAction(AddBookUiAction.OnImageSelected(uri?.toString()))
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            Toast.makeText(context, "Book added successfully!", Toast.LENGTH_SHORT).show()
            onBackClick()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = "ADD BOOK",
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = pink
                    ) 
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = softPink
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        listOf(softPink, Color.White)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Image Picker Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = lightPink.copy(alpha = 0.4f)
                    ),
                    onClick = { imagePickerLauncher.launch("image/*") }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (uiState.selectedImageUri != null) {
                            AsyncImage(
                                model = uiState.selectedImageUri,
                                contentDescription = "Selected image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            IconButton(
                                onClick = { 
                                    viewModel.onAction(AddBookUiAction.OnImagePicked(null))
                                    viewModel.onAction(AddBookUiAction.OnImageSelected(null))
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .background(Color.White.copy(alpha = 0.7f), RoundedCornerShape(10.dp))
                            ) {
                                Icon(Icons.Default.Clear, contentDescription = "Remove photo", tint = pink)
                            }
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.AddPhotoAlternate,
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp),
                                    tint = pink
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    "ADD COVER IMAGE",
                                    fontWeight = FontWeight.Bold,
                                    color = pink
                                )
                            }
                        }
                    }
                }

                // Input Fields
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(
                        value = uiState.title,
                        onValueChange = { viewModel.onAction(AddBookUiAction.OnTitleChange(it)) },
                        label = { Text("ادخل اسم الكتاب") },
                        placeholder = { Text("مثال: Clean Code") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = pink,
                            focusedLabelColor = pink,
                            cursorColor = pink,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        isError = uiState.titleError != null,
                        supportingText = { uiState.titleError?.let { Text(it) } }
                    )

                    OutlinedTextField(
                        value = uiState.isbn,
                        onValueChange = { viewModel.onAction(AddBookUiAction.OnIsbnChange(it)) },
                        label = { Text("ادخل رقم الكتاب (ISBN)") },
                        placeholder = { Text("مثال: 9780132350884") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = pink,
                            focusedLabelColor = pink,
                            cursorColor = pink,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        isError = uiState.isbnError != null,
                        supportingText = { uiState.isbnError?.let { Text(it) } }
                    )

                    OutlinedTextField(
                        value = uiState.nbPages,
                        onValueChange = { viewModel.onAction(AddBookUiAction.OnPagesChange(it)) },
                        label = { Text("ادخل عدد الصفحات") },
                        placeholder = { Text("مثال: 464") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = pink,
                            focusedLabelColor = pink,
                            cursorColor = pink,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        isError = uiState.nbPagesError != null,
                        supportingText = { uiState.nbPagesError?.let { Text(it) } }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Cancel", fontWeight = FontWeight.Bold, color = pink)
                    }

                    Button(
                        onClick = { viewModel.onAction(AddBookUiAction.OnAddClick) },
                        enabled = uiState.isFormValid && !uiState.isLoading,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = pink)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Confirm", fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
