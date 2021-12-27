package com.ns.jetapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ns.jetapp.R
import com.ns.jetapp.ui.ActionButton
import com.ns.jetapp.ui.BackgroundImage
import com.ns.jetapp.ui.InputField
import com.ns.jetapp.ui.theme.JetAppTheme

@Composable
fun UserSignIn() {
    BackgroundImage(painter = painterResource(id = R.drawable.background)) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (header, body) = createRefs()

            HeaderContent(
                modifier = Modifier.constrainAs(header) {
                    top.linkTo(parent.top, 56.dp)
                    centerHorizontallyTo(parent)
                }
            )

            BodyContent(
                modifier = Modifier.constrainAs(body) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                }.fillMaxWidth(0.8f)
            )
        }
    }
}


@Composable
fun BodyContent(
    modifier: Modifier = Modifier
) {
    var emailValue by remember { mutableStateOf("") }
    val newEmailValue: (String) -> Unit = { emailValue = it }

    var passwordValue by remember { mutableStateOf("") }
    val newPasswordValue: (String) -> Unit = { passwordValue = it }

    var showPassword by remember { mutableStateOf(false) }
    val toggleShowPassword: () -> Unit = { showPassword = !showPassword }

    val colors = MaterialTheme.colors

    ConstraintLayout(modifier = modifier) {

        val (emailRef, passwordRef, signInRef, signUpRef) = createRefs()

        // Email editText
        InputField(
            text = emailValue,
            onTextChange = newEmailValue,
            label = "Email",
            textColor = colors.primary,
            placeholder = { Text(text = "Email") },
            modifier = Modifier
                .constrainAs(emailRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(passwordRef.top)
                }
                .fillMaxWidth()
        )

        // Password edit text
        InputField(
            text = passwordValue,
            onTextChange = newPasswordValue,
            label = "Password",
            textColor = colors.primary,
            placeholder = { Text(text = "Password") },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { toggleShowPassword() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_remove_red_eye_24),
                        contentDescription = "hide or show password",
                        tint = if (showPassword) colors.primary.copy(alpha = 0.2f) else colors.primary,
                    )
                }
            },
            modifier = Modifier.constrainAs(passwordRef) {
                top.linkTo(emailRef.bottom, 16.dp)
            }.fillMaxWidth()
        )

        // Sign In button
        ActionButton(
            onClick = {},
            color = colors.secondary,
            text = "SIGN IN",
            textColor = colors.onSecondary,
            padding = PaddingValues(16.dp),
            modifier = Modifier.constrainAs(signInRef) {
                top.linkTo(passwordRef.bottom, 32.dp)
            }.fillMaxWidth()
        )


        // Sign Up Button
        ActionButton(
            onClick = {},
            color = Color.Transparent,
            text = "SIGN UP",
            textColor = colors.primary,
            padding = PaddingValues(16.dp),
            modifier = Modifier.constrainAs(signUpRef) {
                top.linkTo(signInRef.bottom, 16.dp)
            }.fillMaxWidth()
        )

    }
}


@Composable
fun HeaderContent(
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_outline_account_circle_24),
            contentDescription = null,
            tint = colors.primary,
            modifier = Modifier.size(80.dp)
        )


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "SIGN IN",
            fontSize = 36.sp,
            fontWeight = FontWeight.Medium,
            color = colors.primary.copy(alpha = 0.8f)
        )
    }
}


@Preview
@Composable
fun PreviewUserSignIn() {
    JetAppTheme {
        UserSignIn()
    }
}