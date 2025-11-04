package com.example.e_commerceapp.presentation.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.components.ColorfulButton
import com.example.e_commerceapp.presentation.components.ProfileSettingsTextFields
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.ProfileSettingsScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

@Composable
fun ProfileSettingsScreen() {
    val profileSettingsViewModel: ProfileSettingsScreenViewModel = hiltViewModel()
    val userImageUrl by profileSettingsViewModel.imageUrl.collectAsState()
    val userName by profileSettingsViewModel.userName.collectAsState()
    val userEmail by profileSettingsViewModel.userEmail.collectAsState()
    val userPhoneNumber by profileSettingsViewModel.userPhoneNumber.collectAsState()
    var mutableUserName by remember { mutableStateOf(userName) }
    var mutableUserEmail by remember { mutableStateOf(userEmail) }
    var mutableUserPhoneNumber by remember { mutableStateOf(userPhoneNumber) }
    var mutableUserImage by remember { mutableStateOf(userImageUrl?.toUri()) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> mutableUserImage = uri }
    LaunchedEffect(Unit) {
        launch { profileSettingsViewModel.getUserProfileById() }
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Settings",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Your Profile",
                fontFamily = raleWay,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.size(105.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(7.dp),
                    colors = CardColors(
                        Color.White,
                        Color.Black,
                        Color.White,
                        Color.Black
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = mutableUserImage,
                            contentDescription = null,
                            modifier = Modifier
                                .size(92.dp)
                                .clip(CircleShape)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            launcher.launch("image/*")
                        },
                    contentAlignment = Alignment.TopEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pen_button),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileSettingsTextFields(
                    value = mutableUserName,
                    hint = "Enter Your Name",
                    onValueChange = {
                        mutableUserName = it
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                ProfileSettingsTextFields(
                    value = mutableUserEmail,
                    hint = "Type Your Email",
                    onValueChange = {
                        mutableUserEmail = it
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                ProfileSettingsTextFields(
                    value = mutableUserPhoneNumber,
                    hint = "Type Your PhoneNumber",
                    onValueChange = {
                        mutableUserPhoneNumber = it
                    }
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            ColorfulButton(
                text = "Save Changes",
                width = 335.dp,
                height = 40.dp,
                backgroundColor = DarkBlue,
                textSize = 16.sp,
                textColor = GrayishWhite
            ) {
                coroutineScope.launch {
                    val imageUrl = mutableUserImage?.let {
                        profileSettingsViewModel.uploadImage(it, context)
                    }
                    profileSettingsViewModel.updateUserData(
                        mutableUserEmail,
                        mutableUserPhoneNumber,
                        mutableUserName,
                        imageUrl
                    )
                    Toast.makeText(
                        context,
                        "updated Successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}