package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuizUiState
import com.example.ui.components.LevelBadge
import com.example.ui.theme.CorrectGreen
import com.example.ui.theme.CorrectGreenLight
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.GoldBright
import com.example.ui.theme.WrongRed
import com.example.ui.theme.WrongRedLight

@Composable
fun QuizScreen(
  uiState: QuizUiState,
  onOptionSelected: (Int) -> Unit,
  onNextQuestion: () -> Unit,
  onQuitQuiz: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showExitConfirmDialog by remember { mutableStateOf(false) }

  // Handle system back button
  BackHandler {
    showExitConfirmDialog = true
  }

  val currentQuestion = uiState.currentQuestion

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAF9))
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Top Navigation and Header Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        IconButton(
          onClick = { showExitConfirmDialog = true },
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), CircleShape)
            .testTag("back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "العودة للرئيسية",
            tint = Color(0xFF334155)
          )
        }

        // Question Progress: "1 / 10"
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
          Text(
            text = "السؤال ${uiState.currentQuestionIndex + 1} / ${uiState.totalQuestions}",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = EmeraldGreenDark
          )
        }

        // Current Level Badge
        LevelBadge(level = uiState.selectedLevel)
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Linear Progress Bar
      val progress = if (uiState.totalQuestions > 0) {
        (uiState.currentQuestionIndex + 1).toFloat() / uiState.totalQuestions.toFloat()
      } else 0f

      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
          .fillMaxWidth()
          .height(8.dp)
          .clip(RoundedCornerShape(4.dp)),
        color = EmeraldGreenPrimary,
        trackColor = Color(0xFFE2E8F0)
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Current Points Badge
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFFEF3C7))
            .border(1.dp, GoldBright.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Icon(
            imageVector = Icons.Filled.EmojiEvents,
            contentDescription = null,
            tint = GoldBright,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "النقاط: ${uiState.currentScore}",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF92400E)
          )
        }

        Text(
          text = "${uiState.totalQuestions - (uiState.currentQuestionIndex + 1)} متبقية",
          style = MaterialTheme.typography.bodySmall,
          color = Color(0xFF64748B)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Large Question Card
      ElevatedCard(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("question_card")
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(EmeraldGreenPrimary.copy(alpha = 0.12f))
              .padding(horizontal = 12.dp, vertical = 5.dp)
          ) {
            Text(
              text = "سؤال عام",
              style = MaterialTheme.typography.labelMedium,
              color = EmeraldGreenPrimary,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = currentQuestion?.text ?: "",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 21.sp,
            lineHeight = 32.sp,
            color = Color(0xFF1E293B),
            textAlign = TextAlign.Center
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Four Answer Option Cards
      val optionPrefixes = listOf("أ", "ب", "ج", "د")
      currentQuestion?.options?.forEachIndexed { index, optionText ->
        val prefix = optionPrefixes.getOrElse(index) { "${index + 1}" }
        val isSelected = uiState.selectedOptionIndex == index
        val isCorrect = index == currentQuestion.correctOptionIndex
        val isConfirmed = uiState.isAnswerConfirmed

        OptionCard(
          text = optionText,
          prefix = prefix,
          isSelected = isSelected,
          isCorrect = isCorrect,
          isConfirmed = isConfirmed,
          onClick = {
            if (!isConfirmed) {
              onOptionSelected(index)
            }
          },
          testTag = "option_button_$index"
        )

        Spacer(modifier = Modifier.height(12.dp))
      }

      // Educational tip card if answered
      AnimatedVisibility(
        visible = uiState.isAnswerConfirmed && (currentQuestion?.tip?.isNotEmpty() == true),
        enter = fadeIn(animationSpec = tween(300))
      ) {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
          border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF86EFAC))
          ),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Filled.Lightbulb,
              contentDescription = null,
              tint = GoldBright,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "معلومة مفيدة",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = EmeraldGreenDark
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = currentQuestion?.tip ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF334155),
                lineHeight = 18.sp
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Button: "السؤال التالي"
      Button(
        onClick = onNextQuestion,
        enabled = uiState.isAnswerConfirmed,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = EmeraldGreenPrimary,
          disabledContainerColor = Color(0xFFCBD5E1),
          contentColor = Color.White,
          disabledContentColor = Color(0xFF94A3B8)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .testTag("next_question_button")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Text(
            text = if (uiState.isLastQuestion) "إنهاء المسابقة وعرض النتيجة" else "السؤال التالي",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.width(8.dp))
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            modifier = Modifier.size(22.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }

  // Confirmation dialog when user clicks back during the quiz
  if (showExitConfirmDialog) {
    AlertDialog(
      onDismissRequest = { showExitConfirmDialog = false },
      title = {
        Text(text = "مغادرة المسابقة؟", fontWeight = FontWeight.Bold)
      },
      text = {
        Text(text = "هل أنت متأكد من رغبتك في الخروج؟ ستفقد تقدمك في هذه الجولة.")
      },
      confirmButton = {
        TextButton(
          onClick = {
            showExitConfirmDialog = false
            onQuitQuiz()
          }
        ) {
          Text(text = "نعم، خروج", color = WrongRed, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = { showExitConfirmDialog = false }) {
          Text(text = "متابعة اللعب", color = EmeraldGreenPrimary, fontWeight = FontWeight.Bold)
        }
      }
    )
  }
}

@Composable
private fun OptionCard(
  text: String,
  prefix: String,
  isSelected: Boolean,
  isCorrect: Boolean,
  isConfirmed: Boolean,
  onClick: () -> Unit,
  testTag: String
) {
  // Determine styling based on state
  val targetBgColor: Color
  val targetBorderColor: Color
  val targetTextColor: Color
  val prefixBgColor: Color
  val prefixTextColor: Color

  if (isConfirmed) {
    when {
      isCorrect -> {
        // Correct answer: vibrant green
        targetBgColor = CorrectGreen
        targetBorderColor = CorrectGreen
        targetTextColor = Color.White
        prefixBgColor = Color.White.copy(alpha = 0.25f)
        prefixTextColor = Color.White
      }
      isSelected && !isCorrect -> {
        // Wrong answer selected: vibrant red
        targetBgColor = WrongRed
        targetBorderColor = WrongRed
        targetTextColor = Color.White
        prefixBgColor = Color.White.copy(alpha = 0.25f)
        prefixTextColor = Color.White
      }
      else -> {
        // Other non-selected options
        targetBgColor = Color.White.copy(alpha = 0.7f)
        targetBorderColor = Color(0xFFE2E8F0)
        targetTextColor = Color(0xFF94A3B8)
        prefixBgColor = Color(0xFFF1F5F9)
        prefixTextColor = Color(0xFF94A3B8)
      }
    }
  } else {
    // Normal unconfirmed state
    targetBgColor = Color.White
    targetBorderColor = Color(0xFFE2E8F0)
    targetTextColor = Color(0xFF1E293B)
    prefixBgColor = EmeraldGreenPrimary.copy(alpha = 0.1f)
    prefixTextColor = EmeraldGreenPrimary
  }

  val animatedBgColor by animateColorAsState(
    targetValue = targetBgColor,
    animationSpec = tween(durationMillis = 250),
    label = "option_bg"
  )

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = animatedBgColor),
    elevation = CardDefaults.cardElevation(defaultElevation = if (isConfirmed && (isCorrect || isSelected)) 4.dp else 1.dp),
    modifier = Modifier
      .fillMaxWidth()
      .border(2.dp, targetBorderColor, RoundedCornerShape(20.dp))
      .clip(RoundedCornerShape(20.dp))
      .clickable(enabled = !isConfirmed) { onClick() }
      .testTag(testTag)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Prefix Badge (أ، ب، ج، د)
      Box(
        modifier = Modifier
          .size(38.dp)
          .clip(CircleShape)
          .background(prefixBgColor),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = prefix,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = prefixTextColor
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      // Option Text
      Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        color = targetTextColor,
        modifier = Modifier.weight(1f)
      )

      // Status Icon when confirmed
      if (isConfirmed) {
        if (isCorrect) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(Color.White.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Filled.Check,
              contentDescription = "إجابة صحيحة",
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
          }
        } else if (isSelected) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(Color.White.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Filled.Close,
              contentDescription = "إجابة خاطئة",
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }
    }
  }
}
