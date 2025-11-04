package com.example.e_commerceapp.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.NavigateToProductFromStoryScreenDialog
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.viewmodels.ProfileAndStorySharedViewModel

@Composable
fun StoriesScreen(
    chosenIndex:Int = 0,
    rootNavController:NavController,
    onFinish: () -> Unit
) {
    val sharedStoriesViewModel: ProfileAndStorySharedViewModel = hiltViewModel(
        rootNavController.getViewModelStoreOwner(rootNavController.graph.id)
    )
    val storyProducts by sharedStoriesViewModel.storyProducts.collectAsState()
    var currentIndex by remember {
        mutableIntStateOf(chosenIndex)
    }
    val progress = remember {
        Animatable(0f)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(currentIndex) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(8000)
        )
        if (currentIndex < storyProducts.lastIndex) {
            currentIndex++
        } else {
            onFinish()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = storyProducts[currentIndex].productImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Spacer(modifier = Modifier.height(20.dp))
            LinearProgressIndicator(
                progress = { progress.value },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(5.dp),
                color = DarkBlue,
                trackColor = GrayishWhite.copy(alpha = 0.3f)
            )
        }
        Row (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable {
                    if (currentIndex > 0) {
                        currentIndex--
                    }
                }
            )
            Box (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {
                        showDialog = true
                    }
            )
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable {
                    if (currentIndex < storyProducts.lastIndex) {
                        currentIndex++
                    } else {
                        onFinish()
                    }
                }
            )
        }
    }
    if (showDialog){
        Dialog(onDismissRequest = { showDialog = false }) {
            NavigateToProductFromStoryScreenDialog(
                imageUrl = storyProducts[currentIndex].productImage
            ) {
                rootNavController.navigate(
                    Screen.ProductScreen.createRouteForKnownProduct(
                        storyProducts[currentIndex].productId,
                        0,
                        "none",
                        1
                    )
                )
                showDialog = false
            }
        }
    }
}