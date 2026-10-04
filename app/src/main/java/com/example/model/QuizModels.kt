package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.GoldBright
import com.example.ui.theme.RoseAccent

enum class QuizLevel(
  val title: String,
  val description: String,
  val accentColor: Color
) {
  EASY(
    title = "سهل",
    description = "أسئلة عامة خفيفة ومناسبة للجميع",
    accentColor = EmeraldGreenPrimary
  ),
  MEDIUM(
    title = "متوسط",
    description = "ثقافة متنوعة في التاريخ والجغرافيا والعلوم",
    accentColor = GoldBright
  ),
  HARD(
    title = "صعب",
    description = "تحدي معرفي متقدم للمثقفين وأصحاب العزيمة",
    accentColor = RoseAccent
  )
}

data class Question(
  val id: Int,
  val text: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val tip: String = ""
)

enum class Screen {
  HOME,
  QUIZ,
  RESULT
}

data class QuizUiState(
  val currentScreen: Screen = Screen.HOME,
  val selectedLevel: QuizLevel = QuizLevel.EASY,
  val questions: List<Question> = emptyList(),
  val currentQuestionIndex: Int = 0,
  val selectedOptionIndex: Int? = null,
  val isAnswerConfirmed: Boolean = false,
  val currentScore: Int = 0,
  val correctAnswersCount: Int = 0,
  val wrongAnswersCount: Int = 0,
  val showLevelDialog: Boolean = false,
  val showRulesDialog: Boolean = false,
  val bestScoreEasy: Int = 0,
  val bestScoreMedium: Int = 0,
  val bestScoreHard: Int = 0
) {
  val totalQuestions: Int
    get() = questions.size

  val currentQuestion: Question?
    get() = questions.getOrNull(currentQuestionIndex)

  val isLastQuestion: Boolean
    get() = currentQuestionIndex >= totalQuestions - 1

  val successPercentage: Int
    get() = if (totalQuestions > 0) (correctAnswersCount * 100) / totalQuestions else 0
}
