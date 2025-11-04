package com.example.e_commerceapp.presentation.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun DynamicChartForOrdersComponent(
    data: Map<String, Float>,
    categoryColors: Map<String, Color>,
    strokeWidth: Dp = 30.dp
) {
    val total = data.values.sum()
    val entries = data.entries.toList()
    if (total == 0f) return
    val animatedSweeps = entries.map { (_, value) ->
        animateFloatAsState(
            targetValue = (value / total) * 360f,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
            label = ""
        ).value
    }
    Box(
        modifier = Modifier
            .size(280.dp)
            .shadow(10.dp, CircleShape, clip = false)
            .background(Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(213.07.dp)) {
            var startAngle = -90f
            entries.forEachIndexed { index, entry ->
                val sweep = animatedSweeps[index]
                val color = categoryColors[entry.key] ?: Color.Gray
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                startAngle += sweep
            }
            drawCircle(
                color = Color.White,
                radius = size.minDimension / 3f
            )
        }
        Box(
            modifier = Modifier
                .size(150.dp)
                .shadow(10.dp, CircleShape, clip = false)
                .background(Color.White, CircleShape),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "$${total}",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp
                )
            }
        }
    }
}