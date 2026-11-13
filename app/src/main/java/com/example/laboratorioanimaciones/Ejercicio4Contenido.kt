package com.example.laboratorioanimaciones

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.sp

enum class EstadoPantalla {
    CARGANDO,
    CONTENIDO,
    ERROR
}

@Composable
fun Ejercicio4Contenido() {
    var estadoActual by rememberSaveable {
        mutableStateOf(EstadoPantalla.CARGANDO)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 40.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = "Cambio de contenido",
            fontSize = 26.sp
        )

        AnimatedContent(
            targetState = estadoActual,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 800
                    )
                ) togetherWith fadeOut(
                    animationSpec = tween(
                        durationMillis = 400
                    )
                )
            },
            label = "cambio de estado"
        ) { estado ->
            when (estado) {
                EstadoPantalla.CARGANDO -> {
                    MensajeEstado(
                        mensaje = "Cargando información...",
                        colorFondo = Color(0xFF2563EB)
                    )
                }

                EstadoPantalla.CONTENIDO -> {
                    MensajeEstado(
                        mensaje = "Contenido cargado correctamente",
                        colorFondo = Color(0xFF16A34A)
                    )
                }

                EstadoPantalla.ERROR -> {
                    MensajeEstado(
                        mensaje = "Ocurrió un error al cargar",
                        colorFondo = Color(0xFFDC2626)
                    )
                }
            }
        }

        Button(
            onClick = {
                estadoActual = when (estadoActual) {
                    EstadoPantalla.CARGANDO -> {
                        EstadoPantalla.CONTENIDO
                    }

                    EstadoPantalla.CONTENIDO -> {
                        EstadoPantalla.ERROR
                    }

                    EstadoPantalla.ERROR -> {
                        EstadoPantalla.CARGANDO
                    }
                }
            }
        ) {
            Text(text = "Cambiar estado")
        }
    }
}

@Composable
fun MensajeEstado(
    mensaje: String,
    colorFondo: Color
) {
    Surface(
        color = colorFondo,
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = mensaje,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(
                horizontal = 30.dp,
                vertical = 35.dp
            )
        )
    }
}