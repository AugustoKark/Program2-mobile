package ar.edu.um.alumno.comprasAdmin

import ar.edu.um.alumno.compras.ExpandableCard
import ar.edu.um.alumno.compras.MisComprasViewModel


import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun MisComprasScreenAdmin(navController: NavController, viewModel: MisComprasViewModelAdmin = viewModel()) {
    val ventas by viewModel.ventas.collectAsState()
    val token = viewModel.settings.getString("jwtToken", "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Mis Compras Admin", style = MaterialTheme.typography.h4, color = Color(0xFF438ea5), fontWeight = FontWeight.Bold)
        LazyColumn {
            items(ventas) { venta ->
                ExpandableCardAdmin(venta, token, viewModel)
            }
        }
    }
}