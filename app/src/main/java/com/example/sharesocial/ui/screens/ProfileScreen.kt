package com.example.sharesocial.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharesocial.viewmodel.ShareSocialViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ShareSocialViewModel) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    var showLanguageDialog by remember { mutableStateOf(false) }

    val languages = listOf("English", "Hindi", "Bengali", "Telugu", "Marathi", "Tamil", "Gujarati", "Punjabi")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { showLanguageDialog = true }, modifier = Modifier.testTag("profile_lang_btn")) {
                        Icon(Icons.Default.Language, contentDescription = "Language")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(90.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("U", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("You (Creator)", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("@sharesocial_user", fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(label = "Posts", count = "12")
                StatItem(label = "Followers", count = "1.4K")
                StatItem(label = "Following", count = "320")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ListItem(
                        headlineContent = { Text("App Language") },
                        supportingContent = { Text(selectedLanguage) },
                        leadingContent = { Icon(Icons.Default.Language, contentDescription = null) },
                        modifier = Modifier
                            .clickable { showLanguageDialog = true }
                            .testTag("language_list_item")
                    )
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                    ListItem(
                        headlineContent = { Text("Saved Posts") },
                        leadingContent = { Icon(Icons.Default.Bookmark, contentDescription = null) },
                        modifier = Modifier.clickable { /* Saved posts */ }
                    )
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                    ListItem(
                        headlineContent = { Text("Settings & Privacy") },
                        leadingContent = { Icon(Icons.Default.Settings, contentDescription = null) },
                        modifier = Modifier.clickable { /* Settings */ }
                    )
                }
            }
        }
    }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text("Select Language") },
            text = {
                Column {
                    languages.forEach { lang ->
                        TextButton(
                            onClick = {
                                viewModel.setLanguage(lang)
                                showLanguageDialog = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("lang_option_$lang")
                        ) {
                            Text(lang, fontSize = 16.sp, fontWeight = if (selectedLanguage == lang) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun StatItem(label: String, count: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(count, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(label, fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)
    }
}
