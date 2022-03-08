package com.example.tetrainingandroid.ui.compose.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.ui.splash.LoginState

@Composable
fun SplashView(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val auth by viewModel.loginData.observeAsState(initial = null)
    if (auth is LoginState) {
        navController.navigate("login", navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .build())
    }
    Scaffold(
        content = {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Column(Modifier.weight(4.5f)) {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.weight(3.5f),
                    )
                }
                Column(Modifier.weight(5.5f), verticalArrangement = Arrangement.SpaceBetween) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .size(width = 32.dp, height = 32.dp),
                        color = colorResource(id = android.R.color.holo_blue_dark),
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.company_name),
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(bottom = dimensionResource(id = R.dimen.layout_margin_8dp))
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = stringResource(R.string.copyright),
                            fontSize = 14.sp,
                            color = colorResource(id = android.R.color.holo_blue_dark),
                            modifier = Modifier
                                .padding(bottom = dimensionResource(id = R.dimen.layout_margin_16dp))
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    )
}