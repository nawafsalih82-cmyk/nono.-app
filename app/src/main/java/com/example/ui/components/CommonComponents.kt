package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuizLevel
import com.example.ui.theme.GoldBright
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.OnGoldContainer

@Composable
fun TrophyIllustration(
  modifier: Modifier = Modifier,
  size: Dp = 100.dp
) {
  val infiniteTransition = rememberInfiniteTransition(label = "trophy_pulse")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 0.96f,
    targetValue = 1.04f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "trophy_scale"
  )

  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier.scale(pulseScale)
  ) {
    // Outer golden glow circle
    Box(
      modifier = Modifier
        .size(size * 1.25f)
        .clip(CircleShape)
        .background(
          Brush.radialGradient(
            colors = listOf(
              Color(0xFFFEF3C7).copy(alpha = 0.85f),
              Color(0xFFFDE68A).copy(alpha = 0.4f),
              Color.Transparent
            )
          )
        )
    )

    // Inner badge container
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .size(size)
        .clip(CircleShape)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFFFFBEB),
              Color(0xFFFEF3C7)
            )
          )
        )
        .border(
          width = 3.dp,
          brush = Brush.linearGradient(
            colors = listOf(Color(0xFFF59E0B), Color(0xFFD97706), Color(0xFFB45309))
          ),
          shape = CircleShape
        )
    ) {
      Icon(
        imageVector = Icons.Filled.EmojiEvents,
        contentDescription = "أيقونة الكأس الذهبي",
        tint = GoldBright,
        modifier = Modifier.size(size * 0.58f)
      )
    }

    // Sparkle star top-left
    Box(
      modifier = Modifier
        .size(24.dp)
        .align(Alignment.TopStart)
        .clip(CircleShape)
        .background(Color(0xFFFEF08A))
        .border(1.dp, GoldBright, CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = null,
        tint = Color(0xFFD97706),
        modifier = Modifier.size(16.dp)
      )
    }
  }
}

@Composable
fun LevelBadge(
  level: QuizLevel,
  modifier: Modifier = Modifier
) {
  val (bgColor, textColor) = when (level) {
    QuizLevel.EASY -> Color(0xFFDCFCE7) to Color(0xFF15803D)
    QuizLevel.MEDIUM -> GoldContainer to OnGoldContainer
    QuizLevel.HARD -> Color(0xFFFFE4E6) to Color(0xFFBE123C)
  }

  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .clip(RoundedCornerShape(20.dp))
      .background(bgColor)
      .border(1.dp, textColor.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
      .padding(horizontal = 12.dp, vertical = 6.dp)
  ) {
    Box(
      modifier = Modifier
        .size(8.dp)
        .clip(CircleShape)
        .background(textColor)
    )
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = level.title,
      style = MaterialTheme.typography.labelMedium,
      color = textColor,
      fontWeight = FontWeight.Bold,
      fontSize = 13.sp
    )
  }
}
