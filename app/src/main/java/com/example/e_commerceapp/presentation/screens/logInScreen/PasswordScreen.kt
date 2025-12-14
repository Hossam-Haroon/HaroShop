package com.example.e_commerceapp.presentation.screens.logInScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.components.ColorfulButton
import com.example.e_commerceapp.presentation.components.CustomGrayFieldForPassword
import com.example.e_commerceapp.presentation.components.CustomGrayTextField
import com.example.e_commerceapp.presentation.theme.BlueishWhite
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.AuthViewModel
import com.example.e_commerceapp.presentation.viewmodels.PasswordViewModel

@Composable
fun PasswordScreen(email:String, onNavigateToProfileScreen:()->Unit){
    val authViewModel : AuthViewModel = hiltViewModel()
    val passwordViewModel : PasswordViewModel = hiltViewModel()
    val userName by passwordViewModel.userName.collectAsState()
    val context = LocalContext.current
    var password by remember {
        mutableStateOf("")
    }
    LaunchedEffect(email) {
        passwordViewModel.fetchUserNameUsingEmail(email = email)
    }
    Image(
        painter = painterResource(
            id = R.drawable.bubble2
        ),
        alignment = Alignment.TopCenter,
        modifier = Modifier.size(533.91.dp,550.34.dp),
        contentDescription = null
    )
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.height(160.dp))
        Box (modifier = Modifier
            .size(120.dp)
            .clip(CircleShape),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.ellipse),
                modifier = Modifier.size(120.dp),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = null,
                modifier = Modifier
                    .size(91.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Hello, $userName!!",
            fontSize = 28.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Type your password",
            fontSize = 19.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(40.dp))
        CustomGrayFieldForPassword(
            hintText = "Password",
            textValue = password,
            width = 335.dp,
            height =  57.37.dp
        ){
            password = it
        }
        Spacer(modifier = Modifier.height(20.dp))
        ColorfulButton(
            text = "Done",
            335.dp,
            61.dp,
            backgroundColor = DarkBlue,
            textSize = 22.sp,
            textColor = BlueishWhite
        ){
            authViewModel.validatePassword(password, onNavigate = {
                authViewModel.logInUser(email,password)
                onNavigateToProfileScreen()
            }, onError = {
                Toast.makeText(context,it,Toast.LENGTH_LONG).show()
            })
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Cancel",
            fontSize = 15.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Light
        )
    }
}