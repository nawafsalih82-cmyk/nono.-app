package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stars
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.GoldBright
import com.example.ui.theme.RoseAccent

@Composable
fun RulesDialog(
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
        .testTag("rules_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp)
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Icon header
        Box(
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(EmeraldGreenPrimary.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Filled.HelpOutline,
            contentDescription = null,
            tint = EmeraldGreenPrimary,
            modifier = Modifier.size(32.dp)
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "طريقة اللعب",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "قواعد بسيطة وممتعة لاختبار معلوماتك:",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        RuleRow(
          icon = Icons.Filled.Stars,
          iconColor = GoldBright,
          title = "10 أسئلة لكل مسابقة",
          description = "تتكون كل جولة من 10 أسئلة ثقافية متنوعة بحسب المستوى المحدد."
        )

        Spacer(modifier = Modifier.height(14.dp))

        RuleRow(
          icon = Icons.Filled.CheckCircle,
          iconColor = EmeraldGreenPrimary,
          title = "أربعة خيارات وإجابة واحدة",
          description = "اختر الإجابة الصحيحة من بين الخيارات الأربعة المتاحة."
        )

        Spacer(modifier = Modifier.height(14.dp))

        RuleRow(
          icon = Icons.Filled.Lock,
          iconColor = RoseAccent,
          title = "تأكيد فوري وتثبيت الإجابة",
          description = "عند النقر على إجابة، تظهر الصحيحة بالأخضر والخاطئة بالأحمر وتُقفل الخيارات."
        )

        Spacer(modifier = Modifier.height(14.dp))

        RuleRow(
          icon = Icons.Filled.PlayArrow,
          iconColor = EmeraldGreenPrimary,
          title = "الانتقال وحساب النتيجة",
          description = "اضغط زر 'السؤال التالي' لمتابعة التحدي ومعرفة نتيجتك النهائية ونسبة نجاحك."
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreenPrimary),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("close_rules_button")
        ) {
          Text(
            text = "فهمت، لنبدأ!",
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
private fun RuleRow(
  icon: ImageVector,
  iconColor: Color,
  title: String,
  description: String
) {
  Row(
    verticalAlignment = Alignment.Top,
    modifier = Modifier.fillMaxWidth()
  ) {
    Box(
      modifier = Modifier
        .size(36.dp)
        .clip(CircleShape)
        .background(iconColor.copy(alpha = 0.15f)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = iconColor,
        modifier = Modifier.size(20.dp)
      )
    }

    Spacer(modifier = Modifier.width(12.dp))

    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineHeight = 18.sp
      )
    }
  }
}
