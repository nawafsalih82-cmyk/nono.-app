package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Tune
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuizLevel
import com.example.model.QuizUiState
import com.example.ui.components.LevelBadge
import com.example.ui.components.TrophyIllustration
import com.example.ui.dialogs.LevelSelectionDialog
import com.example.ui.dialogs.RulesDialog
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.GoldBright
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.OnGoldContainer

@Composable
fun HomeScreen(
  uiState: QuizUiState,
  onStartQuiz: () -> Unit,
  onOpenLevelDialog: () -> Unit,
  onOpenRulesDialog: () -> Unit,
  onSelectLevel: (QuizLevel) -> Unit,
  onDismissLevelDialog: () -> Unit,
  onDismissRulesDialog: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

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
        .verticalScroll(scrollState)
        .padding(horizontal = 24.dp, vertical = 20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(16.dp))

      // Elegant Trophy Illustration with golden glow
      TrophyIllustration(
        size = 110.dp,
        modifier = Modifier.padding(bottom = 12.dp)
      )

      // Big Title: "مسابقة عامة"
      Text(
        text = "مسابقة عامة",
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 34.sp,
        color = EmeraldGreenDark,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "اختبر معلوماتك العامة وثقافتك في جو من المتعة والتحدي",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
      )

      Spacer(modifier = Modifier.height(24.dp))

      // Current Level Card
      ElevatedCard(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onOpenLevelDialog() }
          .testTag("current_level_card")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(uiState.selectedLevel.accentColor.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Filled.Tune,
                contentDescription = null,
                tint = uiState.selectedLevel.accentColor,
                modifier = Modifier.size(24.dp)
              )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
              Text(
                text = "المستوى الحالي",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = uiState.selectedLevel.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = uiState.selectedLevel.accentColor
              )
            }
          }

          LevelBadge(level = uiState.selectedLevel)
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Big Green Button: "ابدأ المسابقة"
      Button(
        onClick = onStartQuiz,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = EmeraldGreenPrimary,
          contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp, pressedElevation = 2.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(58.dp)
          .testTag("start_quiz_button")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(28.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "ابدأ المسابقة",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Button: "اختيار المستوى"
      OutlinedButton(
        onClick = onOpenLevelDialog,
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
          .testTag("choose_level_button")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Filled.Tune,
            contentDescription = null,
            tint = GoldBright,
            modifier = Modifier.size(22.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "اختيار المستوى (${uiState.selectedLevel.title})",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Button: "طريقة اللعب"
      OutlinedButton(
        onClick = onOpenRulesDialog,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.outlinedButtonColors(
          contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp)
          .testTag("how_to_play_button")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Filled.HelpOutline,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "طريقة اللعب",
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }

      Spacer(modifier = Modifier.height(30.dp))

      // Best Scores Section (🏆 أفضل النتائج)
      HighScoresCard(uiState = uiState)

      Spacer(modifier = Modifier.height(20.dp))
    }
  }

  // Dialogs
  if (uiState.showLevelDialog) {
    LevelSelectionDialog(
      currentLevel = uiState.selectedLevel,
      onLevelSelected = onSelectLevel,
      onDismiss = onDismissLevelDialog
    )
  }

  if (uiState.showRulesDialog) {
    RulesDialog(onDismiss = onDismissRulesDialog)
  }
}

@Composable
private fun HighScoresCard(uiState: QuizUiState) {
  Card(
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("high_scores_card")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 12.dp)
      ) {
        Icon(
          imageVector = Icons.Filled.EmojiEvents,
          contentDescription = null,
          tint = GoldBright,
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "أفضل النتائج المسجلة",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        ScorePill(title = "سهل", score = uiState.bestScoreEasy, color = EmeraldGreenPrimary)
        ScorePill(title = "متوسط", score = uiState.bestScoreMedium, color = GoldBright)
        ScorePill(title = "صعب", score = uiState.bestScoreHard, color = Color(0xFFE11D48))
      }
    }
  }
}

@Composable
private fun ScorePill(
  title: String,
  score: Int,
  color: Color
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(16.dp))
      .background(color.copy(alpha = 0.1f))
      .border(1.dp, color.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
      .padding(horizontal = 14.dp, vertical = 10.dp)
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall,
        color = color,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = "$score / 10",
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.ExtraBold,
        color = color
      )
    }
  }
}
