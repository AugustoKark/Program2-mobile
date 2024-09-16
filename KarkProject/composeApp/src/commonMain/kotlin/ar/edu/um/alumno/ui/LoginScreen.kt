package ar.edu.um.alumno.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import org.jetbrains.compose.resources.painterResource
import karkproject.composeapp.generated.resources.Res
import karkproject.composeapp.generated.resources.header





@Composable
fun LoginScreen() {

    Box(
        Modifier
        .fillMaxSize()
        .padding(16.dp)){
            Login(Modifier.align(Alignment.Center))

        }
}


@Composable
fun Login(modifier: Modifier) {
    Column (modifier = modifier){
        HeaderImage()
    }

}

@Composable
fun HeaderImage() {
    Image(
        painter = painterResource( Res.drawable.header),
        contentDescription = "Header"

    )
}


