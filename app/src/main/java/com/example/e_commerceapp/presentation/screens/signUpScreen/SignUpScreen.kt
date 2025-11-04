package com.example.e_commerceapp.presentation.screens.signUpScreen

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.presentation.components.ColorfulButton
import com.example.e_commerceapp.presentation.components.CustomGrayTextField
import com.example.e_commerceapp.presentation.components.CustomTextFieldWIthSlider
import com.example.e_commerceapp.presentation.components.LoadingProgressIcon
import com.example.e_commerceapp.presentation.theme.BlueishWhite
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.AuthViewModel
import com.example.e_commerceapp.presentation.viewmodels.SignUpViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun SignUpScreen(onNavigateToHelloScreen: () -> Unit) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    val userState = authViewModel.userState.collectAsState()
    val isLoading = authViewModel.isLoading.collectAsState()
    val customerId by signUpViewModel.customerId.collectAsState()
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("+20") }
    var userName by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }
    LaunchedEffect(userState.value) {
        userState.value?.onSuccess { userState ->
            signUpViewModel.createCustomerIdForPaymentMethods(email)
            Toast.makeText(
                context,
                "sign up successful!",
                Toast.LENGTH_LONG
            ).show()
            val imageUrl = imageUri?.let {
                signUpViewModel.uploadImage(it, context)
            }
            val fullPhoneNumber = countryCode + phoneNumber.toInt().toString()
            val user = User(
                userState.uid,
                email,
                userName,
                fullPhoneNumber,
                emptyList(),
                "user",
                emptyList(),
                emptyList(),
                imageUrl,
                "",
                customerId
            )
            signUpViewModel.createUserToFirebase(user)
            onNavigateToHelloScreen()
        }?.onFailure {
            Toast.makeText(
                context,
                "can't sign up, try again.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Image(
        painter = painterResource(
            id = R.drawable.open_bubble
        ),
        alignment = Alignment.TopCenter,
        modifier = Modifier.size(659.33.dp, 513.44.dp),
        contentDescription = null
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(140.dp))
        Text(
            text = "Create",
            fontSize = 50.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 30.dp)
        )
        Text(
            text = "Account",
            fontSize = 50.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 30.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        AsyncImage(
            model = imageUri ?: R.drawable.upload_photo,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 30.dp)
                .size(90.dp)
                .clip(CircleShape)
                .clickable { launcher.launch("image/*") },
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomGrayTextField(
                hintText = "Name",
                textValue = userName,
                width = 335.dp,
                height = 57.37.dp
            ) {
                userName = it
            }
            Spacer(modifier = Modifier.height(7.dp))
            CustomGrayTextField(
                hintText = "Email",
                textValue = email,
                width = 335.dp,
                height = 57.37.dp
            ) {
                email = it
            }
            Spacer(modifier = Modifier.height(7.dp))
            CustomGrayTextField(
                hintText = "Password",
                textValue = password,
                width = 335.dp,
                height = 57.37.dp
            ) {
                password = it
            }
            Spacer(modifier = Modifier.height(7.dp))
            CustomTextFieldWIthSlider(
                phoneNumber,
                countryCode,
                onValueChanged = { phoneNumber = it },
                onCountrySelected = { countryCode = it })
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ColorfulButton(
                text = "Done",
                335.dp,
                61.dp,
                backgroundColor = DarkBlue,
                textSize = 22.sp,
                textColor = BlueishWhite
            ) {
                authViewModel.signUpUser(email, password)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Cancel",
                fontSize = 15.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Light,
                modifier = Modifier.clickable {
                    // navigate back to previous screen
                }
            )
        }
        if (isLoading.value) {
            LoadingProgressIcon()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen() {}
}