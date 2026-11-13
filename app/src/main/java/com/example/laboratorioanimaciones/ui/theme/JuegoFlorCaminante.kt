package com.example.laboratorioanimaciones

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun JuegoFlorCaminante() {
    var carrilFlor by rememberSaveable {
        mutableStateOf(1)
    }

    var carrilGota by rememberSaveable {
        mutableStateOf(0)
    }

    var puntos by rememberSaveable {
        mutableStateOf(0)
    }

    var gotaVisible by rememberSaveable {
        mutableStateOf(true)
    }

    var mensaje by rememberSaveable {
        mutableStateOf("Camina hasta la gota de agua")
    }

    val scope = rememberCoroutineScope()

    val posiciones = listOf(
        5.dp,
        105.dp,
        205.dp
    )

    val posicionFlorX by animateDpAsState(
        targetValue = posiciones[carrilFlor],
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "movimiento de la flor"
    )

    val tamanoFlor by animateDpAsState(
        targetValue = if (carrilFlor == carrilGota) {
            100.dp
        } else {
            88.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "tamaño de la flor"
    )

    val colorCielo by animateColorAsState(
        targetValue = if (puntos % 20 == 0) {
            Color(0xFFBAE6FD)
        } else {
            Color(0xFFFDE68A)
        },
        animationSpec = tween(durationMillis = 1000),
        label = "color del cielo"
    )

    val animacionInfinita = rememberInfiniteTransition(
        label = "animación de la flor"
    )

    val movimientoPiernas by animacionInfinita.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 280),
            repeatMode = RepeatMode.Reverse
        ),
        label = "movimiento de piernas"
    )

    val movimientoVertical by animacionInfinita.animateFloat(
        initialValue = 0f,
        targetValue = -5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 280),
            repeatMode = RepeatMode.Reverse
        ),
        label = "salto al caminar"
    )

    val rotacionPetalos by animacionInfinita.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotación de pétalos"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "LA AVENTURA DE FLORITA",
            fontSize = 24.sp,
            color = Color(0xFF166534)
        )

        AnimatedContent(
            targetState = puntos,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(500)
                ) togetherWith fadeOut(
                    animationSpec = tween(300)
                )
            },
            label = "puntuación"
        ) { puntuacion ->
            Text(
                text = "Gotas recogidas: ${puntuacion / 10}",
                fontSize = 20.sp
            )
        }

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(320.dp)
                .background(
                    color = colorCielo,
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    width = 3.dp,
                    color = Color(0xFF15803D),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            // Suelo del jardín
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(95.dp)
                    .background(
                        color = Color(0xFF4ADE80),
                        shape = RoundedCornerShape(
                            bottomStart = 22.dp,
                            bottomEnd = 22.dp
                        )
                    )
            )

            // Sol
            Box(
                modifier = Modifier
                    .offset(x = 225.dp, y = 20.dp)
                    .size(45.dp)
                    .background(
                        color = Color(0xFFFACC15),
                        shape = RoundedCornerShape(50)
                    )
            )

            // Gota de agua
            AnimatedVisibility(
                visible = gotaVisible,
                modifier = Modifier.offset(
                    x = posiciones[carrilGota] + 25.dp,
                    y = 120.dp
                ),
                enter = fadeIn(
                    animationSpec = tween(500)
                ) + scaleIn(),
                exit = fadeOut(
                    animationSpec = tween(400)
                ) + scaleOut()
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            color = Color(0xFF0284C7),
                            shape = RoundedCornerShape(
                                topStart = 50.dp,
                                topEnd = 50.dp,
                                bottomStart = 50.dp,
                                bottomEnd = 10.dp
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💧",
                        fontSize = 22.sp
                    )
                }
            }

            // Flor caminante
            FlorCaminante(
                movimientoPiernas = movimientoPiernas,
                rotacionPetalos = rotacionPetalos,
                modifier = Modifier
                    .offset(
                        x = posicionFlorX,
                        y = 205.dp + movimientoVertical.dp
                    )
                    .size(tamanoFlor)
            )
        }

        AnimatedContent(
            targetState = mensaje,
            transitionSpec = {
                fadeIn(tween(400)) togetherWith fadeOut(tween(250))
            },
            label = "mensaje del juego"
        ) { mensajeActual ->
            Text(
                text = mensajeActual,
                fontSize = 16.sp
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    carrilFlor = (carrilFlor - 1).coerceAtLeast(0)
                    mensaje = "Florita está caminando"
                }
            ) {
                Text(
                    text = "←",
                    fontSize = 22.sp
                )
            }

            Button(
                enabled = gotaVisible,
                onClick = {
                    if (carrilFlor == carrilGota) {
                        puntos += 10
                        mensaje = "¡Florita tomó agua!"
                        gotaVisible = false

                        scope.launch {
                            delay(700)

                            carrilGota = (
                                    carrilGota + Random.nextInt(1, 3)
                                    ) % 3

                            gotaVisible = true
                            mensaje = "Busca la siguiente gota"
                        }
                    } else {
                        mensaje = "Camina hasta donde está la gota"
                    }
                }
            ) {
                Text(text = "Tomar agua")
            }

            Button(
                onClick = {
                    carrilFlor = (carrilFlor + 1).coerceAtMost(2)
                    mensaje = "Florita está caminando"
                }
            ) {
                Text(
                    text = "→",
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Composable
fun FlorCaminante(
    movimientoPiernas: Float,
    rotacionPetalos: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val centroX = size.width / 2f
        val cabezaY = size.height * 0.28f
        val finalTalloY = size.height * 0.72f

        val grosorTallo = 5.dp.toPx()
        val largoPaso = 10.dp.toPx()

        // Tallo
        drawLine(
            color = Color(0xFF15803D),
            start = Offset(
                x = centroX,
                y = cabezaY + 12.dp.toPx()
            ),
            end = Offset(
                x = centroX,
                y = finalTalloY
            ),
            strokeWidth = grosorTallo,
            cap = StrokeCap.Round
        )

        // Brazos
        drawLine(
            color = Color(0xFF15803D),
            start = Offset(
                x = centroX,
                y = size.height * 0.52f
            ),
            end = Offset(
                x = centroX - 18.dp.toPx(),
                y = size.height * 0.60f
            ),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color(0xFF15803D),
            start = Offset(
                x = centroX,
                y = size.height * 0.52f
            ),
            end = Offset(
                x = centroX + 18.dp.toPx(),
                y = size.height * 0.60f
            ),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Pierna izquierda
        drawLine(
            color = Color(0xFF166534),
            start = Offset(
                x = centroX,
                y = finalTalloY
            ),
            end = Offset(
                x = centroX - 12.dp.toPx() +
                        movimientoPiernas * largoPaso,
                y = size.height * 0.93f
            ),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Pierna derecha
        drawLine(
            color = Color(0xFF166534),
            start = Offset(
                x = centroX,
                y = finalTalloY
            ),
            end = Offset(
                x = centroX + 12.dp.toPx() -
                        movimientoPiernas * largoPaso,
                y = size.height * 0.93f
            ),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Pétalos giratorios
        repeat(6) { indice ->
            val angulo = Math.toRadians(
                indice * 60.0 + rotacionPetalos
            )

            val distancia = 19.dp.toPx()

            val petaloX = centroX +
                    cos(angulo).toFloat() * distancia

            val petaloY = cabezaY +
                    sin(angulo).toFloat() * distancia

            drawCircle(
                color = Color(0xFFF472B6),
                radius = 12.dp.toPx(),
                center = Offset(
                    x = petaloX,
                    y = petaloY
                )
            )
        }

        // Centro de la flor
        drawCircle(
            color = Color(0xFFFACC15),
            radius = 16.dp.toPx(),
            center = Offset(
                x = centroX,
                y = cabezaY
            )
        )

        // Ojos
        drawCircle(
            color = Color.Black,
            radius = 2.dp.toPx(),
            center = Offset(
                x = centroX - 6.dp.toPx(),
                y = cabezaY - 2.dp.toPx()
            )
        )

        drawCircle(
            color = Color.Black,
            radius = 2.dp.toPx(),
            center = Offset(
                x = centroX + 6.dp.toPx(),
                y = cabezaY - 2.dp.toPx()
            )
        )

        // Sonrisa
        drawLine(
            color = Color(0xFF92400E),
            start = Offset(
                x = centroX - 5.dp.toPx(),
                y = cabezaY + 6.dp.toPx()
            ),
            end = Offset(
                x = centroX + 5.dp.toPx(),
                y = cabezaY + 6.dp.toPx()
            ),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}