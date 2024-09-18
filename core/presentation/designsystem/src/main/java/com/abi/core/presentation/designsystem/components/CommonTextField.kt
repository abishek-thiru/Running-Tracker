package com.abi.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abi.core.presentation.designsystem.CheckIcon
import com.abi.core.presentation.designsystem.EmailIcon
import com.abi.core.presentation.designsystem.RunningTrackerTheme

@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    text: String,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    hint: String,
    title: String? = null,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    additionalInfo: String? = null,
    onValueChanged: (String) -> Unit

) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (title != null) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            } else if (additionalInfo != null) {
                Text(
                    text = additionalInfo,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }

        }
        Spacer(Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isFocused) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Transparent
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(start = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            TextField(
                value = text,
                onValueChange = onValueChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    }
                    .background(Color.Transparent),
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                singleLine = true
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (startIcon != null) {
                    Icon(
                        imageVector = startIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (text.isEmpty() && isFocused) {
                        Text(
                            text = hint,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.4f
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                if (endIcon != null) {
                    Icon(
                        imageVector = endIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun CommonTextFieldPreview() {
    RunningTrackerTheme {
        CommonTextField(
            text = "",
            startIcon = EmailIcon,
            endIcon = CheckIcon,
            hint = "test@example.com",
            title = "Email",
            additionalInfo = "Must be a valid Email",
            modifier = Modifier
                .fillMaxWidth(),
            onValueChanged = {

            }
        )
    }
}