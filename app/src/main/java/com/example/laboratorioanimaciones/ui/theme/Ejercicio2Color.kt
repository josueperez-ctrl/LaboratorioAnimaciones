package com.example.laboratorioanimaciones

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Ejercicio2Color() {
    var usarVerde by rememberSaveable { mutableStateOf(false) }

    val colorAnimado by animateColorAsState(
        targetValue = if (usarVerde) {
            Color(0xFF16A34A)
        } else {
            Color(0xFF2563EB)
        },
        animationSpec = tween(durationMillis = 1000),
        label = "color del cuadro"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 32.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .background(
                    color = colorAnimado,
                    shape = RoundedCornerShape(24.dp)
                )
        )

        Button(onClick = { usarVerde = !usarVerde }) {
            Text(
                text = if (usarVerde) {
                    "Cambiar a azul"
                } else {
                    "Cambiar a verde"
                }
            )
        }
    }
}