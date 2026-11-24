package com.example.sharesocial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sharesocial.ui.screens.*
import com.example.sharesocial.ui.theme.ShareSocialTheme
import com.example.sharesocial.viewmodel.ShareSocialViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Explore : Screen("explore", "Explore", Icons.Default.Home)
    object Videos : Screen("videos", "Videos", Icons.Default.PlayArrow)
    object Chat : Screen("chat", "Chat", Icons.Default.Chat)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShareSocialTheme {
                val viewModel: ShareSocialViewModel = viewModel()
                val navController = rememberNavController()

                val items = listOf(
                    Screen.Explore,
                    Screen.Videos,
                    Screen.Chat,
                    Screen.Profile
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route

                        if (currentRoute == Screen.Explore.route ||
                            currentRoute == Screen.Videos.route ||
                            currentRoute == Screen.Chat.route ||
                            currentRoute == Screen.Profile.route
                        ) {
                            NavigationBar {
                                items.forEach { screen ->
                                    NavigationBarItem(
                                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                                        label = { Text(screen.title) },
                                        selected = currentRoute == screen.route,
                                        onClick = {
                                            if (currentRoute != screen.route) {
                                                navController.navigate(screen.route) {
                                                    popUpTo(navController.graph.startDestinationId) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        },
                                        modifier = Modifier.testTag("nav_item_${screen.route}")
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Explore.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Explore.route) {
                            ExploreScreen(
                                viewModel = viewModel,
                                onPostClick = { post ->
                                    navController.navigate("post_detail/${post.id}")
                                }
                            )
                        }
                        composable(Screen.Videos.route) {
                            VideosScreen(viewModel = viewModel)
                        }
                        composable(Screen.Chat.route) {
                            ChatScreen(viewModel = viewModel)
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(viewModel = viewModel)
                        }
                        composable(
                            route = "post_detail/{postId}",
                            arguments = listOf(navArgument("postId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val postId = backStackEntry.arguments?.getString("postId") ?: ""
                            PostDetailScreen(
                                postId = postId,
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
