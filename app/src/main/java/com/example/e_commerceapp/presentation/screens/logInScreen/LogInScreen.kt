package com.example.e_commerceapp.presentation.screens.logInScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.components.ColorfulButton
import com.example.e_commerceapp.presentation.components.CustomGrayTextField
import com.example.e_commerceapp.presentation.theme.BlueishWhite
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.AuthViewModel

@Composable
fun LogInScreen(onNavigateToPasswordScreen:(String)->Unit){
    var email by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val viewModel : AuthViewModel = hiltViewModel()
    Image(
        painter = painterResource(
            id = R.drawable.bubbles
        ),
        alignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize(),
        contentDescription = null
    )
    Column {
        Spacer(modifier = Modifier.height(450.dp))
        Text(
            text = "Login",
            fontSize = 50.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 30.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Good to see you back!",
            fontSize = 19.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(start = 30.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomGrayTextField(
                hintText = "Email",
                textValue = email,
                width = 335.dp,
                height =  57.37.dp
            ){
                email = it
            }
            Spacer(modifier = Modifier.height(30.dp))
            ColorfulButton(text = "Next",
                335.dp,
                61.dp,
                backgroundColor = DarkBlue,
                textSize = 22.sp,
                textColor = BlueishWhite
            ){
                viewModel.validateEmail(email,
                    onNavigate = {
                        onNavigateToPasswordScreen(email)
                    },
                    onError = {
                        Toast.makeText(context,it,Toast.LENGTH_LONG).show()
                    })
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Cancel",
                fontSize = 15.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Light
            )
        }
    }
}
