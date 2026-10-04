package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuizUiState
import com.example.ui.components.LevelBadge
import com.example.ui.components.TrophyIllustration
import com.example.ui.theme.CorrectGreen
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.GoldBright
import com.example.ui.theme.RoseAccent
import com.example.ui.theme.WrongRed

@Composable
fun ResultScreen(
  uiState: QuizUiState,
  onRestartQuiz: () -> Unit,
  onReturnToHome: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Back button takes user back to Home
  BackHandler {
    onReturnToHome()
  }

  val celebrationMessage = when {
    uiState.currentScore >= 9 -> "ممتاز! أحسنت صنعًا 🌟"
    uiState.currentScore >= 7 -> "أحسنت! أداء رائع 👏"
    uiState.currentScore >= 5 -> "محاولة جيدة جدًا! 👍"
    else -> "محاولة مقدرة! واصل التحدي 💪"
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            Color(0xFFE8F5E9),
            Color(0xFFF7FAF7),
            Color(0xFFFFFFFF)
          )
        )
      )
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(10.dp))

      // Trophy Illustration
      TrophyIllustration(
        size = 110.dp,
        modifier = Modifier.padding(bottom = 8.dp)
      )

      // Celebration Title
      Text(
        text = "أحسنت!",
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        color = EmeraldGreenDark
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = celebrationMessage,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Level badge
      LevelBadge(level = uiState.selectedLevel)

      Spacer(modifier = Modifier.height(20.dp))

      // Score Highlight Card (e.g. 8 / 10)
      ElevatedCard(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("result_score_card")
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "النتيجة النهائية",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(8.dp))

          // Big Score Display: 8 / 10
          Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
          ) {
            Text(
              text = "${uiState.currentScore}",
              style = MaterialTheme.typography.displayMedium,
              fontWeight = FontWeight.Black,
              fontSize = 54.sp,
              color = EmeraldGreenPrimary
            )
            Text(
              text = " / ${uiState.totalQuestions}",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold,
              fontSize = 28.sp,
              color = Color(0xFF64748B),
              modifier = Modifier.padding(bottom = 6.dp)
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Success percentage chip
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(16.dp))
              .background(Color(0xFFFEF3C7))
              .border(1.dp, GoldBright.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
              .padding(horizontal = 16.dp, vertical = 6.dp)
          ) {
            Text(
              text = "نسبة النجاح: ${uiState.successPercentage}%",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF92400E)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Statistics Grid: Correct, Wrong, Success Rate
      Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("result_stats_card")
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Text(
            text = "تفاصيل الإجابات",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
          )

          // Correct answers row
          StatRow(
            icon = Icons.Filled.CheckCircle,
            iconColor = CorrectGreen,
            title = "عدد الإجابات الصحيحة",
            value = "${uiState.correctAnswersCount}",
            valueColor = CorrectGreen
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Wrong answers row
          StatRow(
            icon = Icons.Filled.Cancel,
            iconColor = WrongRed,
            title = "عدد الإجابات الخاطئة",
            value = "${uiState.wrongAnswersCount}",
            valueColor = WrongRed
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Success percentage row
          StatRow(
            icon = Icons.Filled.Percent,
            iconColor = GoldBright,
            title = "نسبة النجاح",
            value = "${uiState.successPercentage}%",
            valueColor = Color(0xFFB45309)
          )
        }
      }

      Spacer(modifier = Modifier.height(26.dp))

      // Button: "إعادة المسابقة" (Retry Quiz)
      Button(
        onClick = onRestartQuiz,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = EmeraldGreenPrimary,
          contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .testTag("restart_quiz_button")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "إعادة المسابقة",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Button: "العودة للرئيسية" (Return to Home)
      OutlinedButton(
        onClick = onReturnToHome,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.outlinedButtonColors(
          contentColor = EmeraldGreenDark
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
          brush = Brush.linearGradient(
            colors = listOf(EmeraldGreenPrimary, GoldBright)
          ),
          width = 1.8.dp
        ),
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp)
          .testTag("return_to_home_button")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = null,
            tint = EmeraldGreenPrimary,
            modifier = Modifier.size(22.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "العودة للرئيسية",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun StatRow(
  icon: ImageVector,
  iconColor: Color,
  title: String,
  value: String,
  valueColor: Color
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(Color(0xFFF8FAFC))
      .padding(horizontal = 14.dp, vertical = 12.dp)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = iconColor,
        modifier = Modifier.size(24.dp)
      )
      Spacer(modifier = Modifier.width(12.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF334155)
      )
    }

    Text(
      text = value,
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.ExtraBold,
      color = valueColor
    )
  }
}
