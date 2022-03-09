package com.example.tetrainingandroid.ui.compose.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.ui.compose.ui.common.GradientButton
import com.example.tetrainingandroid.ui.compose.theme.TextGray

@Composable
fun LoginView(
    onLoginSuccess: () -> Unit = {},
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Scaffold(
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(Modifier.weight(4.5f)) {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.weight(3.5f),
                    )
                }
                Column(Modifier.weight(5.5f)) {
                    Text(
                        text = stringResource(id = R.string.username),
                        style = MaterialTheme.typography.subtitle1
                    )
                    TextField(
                        value = username,
                        onValueChange = {
                            username = it
                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.username_hint_text),
                                color = TextGray
                            )
                        }
                    )
                    Text(
                        text = stringResource(id = R.string.password),
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    TextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.password_hint_text),
                                color = TextGray
                            )
                        }
                    )
                    Text(
                        text = stringResource(id = R.string.forgot_password),
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier
                            .align(alignment = Alignment.End)
                            .padding(top = 16.dp)
                    )
                    GradientButton(
                        text = stringResource(id = R.string.login),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        shape = RoundedCornerShape(32.dp),
                        onClick = onLoginSuccess,
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.or_login_via))
                        Row(
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_facebook),
                                contentDescription = "facebook",
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.width(32.dp))
                            Image(
                                painter = painterResource(id = R.drawable.ic_google),
                                contentDescription = "google",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}