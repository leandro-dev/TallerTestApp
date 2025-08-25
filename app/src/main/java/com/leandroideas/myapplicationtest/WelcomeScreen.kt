package com.leandroideas.myapplicationtest

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.leandroideas.myapplicationtest.ui.theme.MyApplicationTestTheme

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Welcome",
    )
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    MyApplicationTestTheme {
        WelcomeScreen()
    }
}