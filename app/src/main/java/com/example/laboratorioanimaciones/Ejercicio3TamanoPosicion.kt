package com.example.laboratorioanimaciones

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun Ejercicio3TamanoPosicion() {
    var cuadroMovido by rememberSaveable {
        mutableStateOf(false)
    }

    val tamanoCuadro by animateDpAsState(
        targetValue = if (cuadroMovido) 140.dp else 70.dp,
        animationSpec = tween(durationMillis = 900),
        label = "tamaño del cuadro"
    )

    val posicionX by animateDpAsState(
        targetValue = if (cuadroMovido) 160.dp else 10.dp,
        animationSpec = tween(durationMillis = 900),
        label = "posición horizontal"
    )

    val posicionY by animateDpAsState(
        targetValue = if (cuadroMovido) 150.dp else 10.dp,
        animationSpec = tween(durationMillis = 900),
        label = "posición vertical"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Animación de tamaño y posición")

        Box(
            modifier = Modifier
                .width(320.dp)
                .height(320.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset(
                        x = posicionX,
                        y = posicionY
                    )
                    .size(tamanoCuadro)
                    .background(
                        color = Color(0xFF7C3AED),
                        shape = RoundedCornerShape(18.dp)
                    )
            )
        }

        Button(
            onClick = {
                cuadroMovido = !cuadroMovido
            }
        ) {
            Text(
                text = if (cuadroMovido) {
                    "Regresar cuadro"
                } else {
                    "Mover y aumentar"
                }
            )
        }
    }
}