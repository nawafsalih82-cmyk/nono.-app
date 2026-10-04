package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.Screen
import com.example.ui.HomeScreen
import com.example.ui.QuizScreen
import com.example.ui.ResultScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        // Enforce RTL for proper modern Arabic layout
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
          QuizApp()
        }
      }
    }
  }
}

@Composable
fun QuizApp(
  quizViewModel: QuizViewModel = viewModel()
) {
  val uiState by quizViewModel.uiState.collectAsStateWithLifecycle()

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    contentWindowInsets = WindowInsets.safeDrawing
  ) { innerPadding ->
    val contentModifier = Modifier.padding(innerPadding)

    when (uiState.currentScreen) {
      Screen.HOME -> {
        HomeScreen(
          uiState = uiState,
          onStartQuiz = { quizViewModel.startQuiz() },
          onOpenLevelDialog = { quizViewModel.setShowLevelDialog(true) },
          onOpenRulesDialog = { quizViewModel.setShowRulesDialog(true) },
          onSelectLevel = { level -> quizViewModel.selectLevel(level) },
          onDismissLevelDialog = { quizViewModel.setShowLevelDialog(false) },
          onDismissRulesDialog = { quizViewModel.setShowRulesDialog(false) },
          modifier = contentModifier
        )
      }

      Screen.QUIZ -> {
        QuizScreen(
          uiState = uiState,
          onOptionSelected = { index -> quizViewModel.onOptionSelected(index) },
          onNextQuestion = { quizViewModel.onNextQuestion() },
          onQuitQuiz = { quizViewModel.returnToHome() },
          modifier = contentModifier
        )
      }

      Screen.RESULT -> {
        ResultScreen(
          uiState = uiState,
          onRestartQuiz = { quizViewModel.restartQuiz() },
          onReturnToHome = { quizViewModel.returnToHome() },
          modifier = contentModifier
        )
      }
    }
  }
}
