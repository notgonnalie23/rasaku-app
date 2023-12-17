package app.capstone.rasaku.ui.screen.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.R
import app.capstone.rasaku.ui.component.ModalDialog
import app.capstone.rasaku.ui.theme.RasakuTheme
import app.capstone.rasaku.utils.isValidEmail
import app.capstone.rasaku.utils.isValidPassword

@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RegisterContent(
        onRegisterClick = { navigateToLogin() },
    )
}

@Composable
private fun RegisterContent(
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isConfirmPasswordError by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var isButtonEnable by remember { mutableStateOf(true) }
    var isShowDialog by remember { mutableStateOf(false)}

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeContent)
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = stringResource(R.string.register),
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(84.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it.trim()
                isEmailError = !email.isValidEmail()
            },
            label = { Text(stringResource(id = R.string.email)) },
            singleLine = true,
            isError = isEmailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it.trim()
                isPasswordError = !it.isValidPassword()
            },
            label = { Text(stringResource(id = R.string.password)) },
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Rounded.VisibilityOff
                        else Icons.Rounded.Visibility, contentDescription = null
                    )
                }
            },
            singleLine = true,
            isError = isPasswordError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it.trim()
                isConfirmPasswordError = confirmPassword !== password
            },
            label = { Text(stringResource(R.string.confirm_password)) },
            trailingIcon = {
                IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                    Icon(
                        imageVector = if (showConfirmPassword) Icons.Rounded.VisibilityOff
                        else Icons.Rounded.Visibility, contentDescription = null
                    )
                }
            },
            singleLine = true,
            isError = isConfirmPasswordError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go,
            ),
            keyboardActions = KeyboardActions(onGo = {
                isEmailError = !email.isValidEmail()
                isPasswordError = !password.isValidPassword()
                isConfirmPasswordError = confirmPassword !== password
                if (email.isValidEmail() && password.isValidPassword()) {
                    isButtonEnable = false
                    isShowDialog = true
                    isButtonEnable = true
                }
            }),
            visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(84.dp))

        Button(
            onClick = {
                isEmailError = !email.isValidEmail()
                isPasswordError = !password.isValidPassword()
                isConfirmPasswordError = confirmPassword !== password
                if (email.isValidEmail() && password.isValidPassword()) {
                    isButtonEnable = false
                    isShowDialog = true
                    isButtonEnable = true
                }
            },
            modifier = modifier.fillMaxWidth(),
        ) {
            if (isButtonEnable) Text(text = stringResource(R.string.register))
            else CircularProgressIndicator(modifier = modifier.size(14.dp))
        }
    }
    if (isShowDialog){
        ModalDialog(
            title = "Berhasil",
            body = "Akun Anda berhasil diregistrasi",
            positive = "OK",
            setShowDialog = {
                            isShowDialog = it
            },
            onPositiveClick = onRegisterClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview(){
    RasakuTheme {
        RegisterScreen({})
    }
}