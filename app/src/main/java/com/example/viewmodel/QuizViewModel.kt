package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.data.QuizQuestionsData
import com.example.model.QuizLevel
import com.example.model.QuizUiState
import com.example.model.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel(application: Application) : AndroidViewModel(application) {

  private val prefs = application.getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)

  private val _uiState = MutableStateFlow(
    QuizUiState(
      bestScoreEasy = prefs.getInt("best_easy", 0),
      bestScoreMedium = prefs.getInt("best_medium", 0),
      bestScoreHard = prefs.getInt("best_hard", 0)
    )
  )
  val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

  fun selectLevel(level: QuizLevel) {
    _uiState.update { it.copy(selectedLevel = level, showLevelDialog = false) }
  }

  fun setShowLevelDialog(show: Boolean) {
    _uiState.update { it.copy(showLevelDialog = show) }
  }

  fun setShowRulesDialog(show: Boolean) {
    _uiState.update { it.copy(showRulesDialog = show) }
  }

  fun startQuiz() {
    val level = _uiState.value.selectedLevel
    val questions = QuizQuestionsData.getQuestionsForLevel(level)
    _uiState.update {
      it.copy(
        currentScreen = Screen.QUIZ,
        questions = questions,
        currentQuestionIndex = 0,
        selectedOptionIndex = null,
        isAnswerConfirmed = false,
        currentScore = 0,
        correctAnswersCount = 0,
        wrongAnswersCount = 0
      )
    }
  }

  fun onOptionSelected(index: Int) {
    val state = _uiState.value
    // Prevent re-selection once answer is confirmed
    if (state.isAnswerConfirmed) return

    val currentQ = state.currentQuestion ?: return
    val isCorrect = (index == currentQ.correctOptionIndex)

    val newCorrect = if (isCorrect) state.correctAnswersCount + 1 else state.correctAnswersCount
    val newWrong = if (!isCorrect) state.wrongAnswersCount + 1 else state.wrongAnswersCount
    val newScore = if (isCorrect) state.currentScore + 1 else state.currentScore

    _uiState.update {
      it.copy(
        selectedOptionIndex = index,
        isAnswerConfirmed = true,
        correctAnswersCount = newCorrect,
        wrongAnswersCount = newWrong,
        currentScore = newScore
      )
    }
  }

  fun onNextQuestion() {
    val state = _uiState.value
    if (!state.isAnswerConfirmed) return

    if (state.isLastQuestion) {
      // Quiz Finished - check and save high scores
      val finalScore = state.currentScore
      val level = state.selectedLevel
      saveHighScoreIfNeeded(level, finalScore)

      _uiState.update {
        it.copy(
          currentScreen = Screen.RESULT,
          bestScoreEasy = prefs.getInt("best_easy", 0),
          bestScoreMedium = prefs.getInt("best_medium", 0),
          bestScoreHard = prefs.getInt("best_hard", 0)
        )
      }
    } else {
      _uiState.update {
        it.copy(
          currentQuestionIndex = it.currentQuestionIndex + 1,
          selectedOptionIndex = null,
          isAnswerConfirmed = false
        )
      }
    }
  }

  fun restartQuiz() {
    startQuiz()
  }

  fun returnToHome() {
    _uiState.update {
      it.copy(
        currentScreen = Screen.HOME,
        selectedOptionIndex = null,
        isAnswerConfirmed = false
      )
    }
  }

  private fun saveHighScoreIfNeeded(level: QuizLevel, score: Int) {
    val key = when (level) {
      QuizLevel.EASY -> "best_easy"
      QuizLevel.MEDIUM -> "best_medium"
      QuizLevel.HARD -> "best_hard"
    }
    val currentBest = prefs.getInt(key, 0)
    if (score > currentBest) {
      prefs.edit().putInt(key, score).apply()
    }
  }
}
