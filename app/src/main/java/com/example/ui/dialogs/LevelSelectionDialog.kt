package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.QuizLevel
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.GoldBright
import com.example.ui.theme.RoseAccent

@Composable
fun LevelSelectionDialog(
  currentLevel: QuizLevel,
  onLevelSelected: (QuizLevel) -> Unit,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(28.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("level_selection_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "اختيار المستوى",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "حدد مستوى صعوبة الأسئلة المناسب لك:",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Three level cards
        LevelOptionItem(
          level = QuizLevel.EASY,
          isSelected = currentLevel == QuizLevel.EASY,
          icon = Icons.Filled.SentimentSatisfiedAlt,
          color = EmeraldGreenPrimary,
          onClick = { onLevelSelected(QuizLevel.EASY) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        LevelOptionItem(
          level = QuizLevel.MEDIUM,
          isSelected = currentLevel == QuizLevel.MEDIUM,
          icon = Icons.Filled.EmojiEvents,
          color = GoldBright,
          onClick = { onLevelSelected(QuizLevel.MEDIUM) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        LevelOptionItem(
          level = QuizLevel.HARD,
          isSelected = currentLevel == QuizLevel.HARD,
          icon = Icons.Filled.LocalFireDepartment,
          color = RoseAccent,
          onClick = { onLevelSelected(QuizLevel.HARD) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreenPrimary),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("confirm_level_button")
        ) {
          Text(
            text = "تم الاختيار",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
      }
    }
  }
}

@Composable
private fun LevelOptionItem(
  level: QuizLevel,
  isSelected: Boolean,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  color: Color,
  onClick: () -> Unit
) {
  val borderModifier = if (isSelected) {
    Modifier.border(2.dp, color, RoundedCornerShape(18.dp))
  } else {
    Modifier.border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), RoundedCornerShape(18.dp))
  }

  val backgroundColor = if (isSelected) {
    color.copy(alpha = 0.12f)
  } else {
    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
  }

  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(18.dp))
      .background(backgroundColor)
      .then(borderModifier)
      .clickable { onClick() }
      .padding(14.dp)
  ) {
    // Icon badge
    Box(
      modifier = Modifier
        .size(44.dp)
        .clip(CircleShape)
        .background(color.copy(alpha = 0.2f)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = color,
        modifier = Modifier.size(26.dp)
      )
    }

    Spacer(modifier = Modifier.width(12.dp))

    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = level.title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = if (isSelected) color else MaterialTheme.colorScheme.onSurface
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = level.description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    if (isSelected) {
      Box(
        modifier = Modifier
          .size(28.dp)
          .clip(CircleShape)
          .background(color),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Filled.Check,
          contentDescription = "تم التحديد",
          tint = Color.White,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}
