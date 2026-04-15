package com.chuckerteam.chucker.internal.ui.compose.screens.main

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.chuckerteam.chucker.R
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@Composable
internal fun ChuckerTutorialSection(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val readmeUrl = "https://github.com/ChuckerTeam/chucker/blob/develop/README.md"

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.chucker_setup),
            style = AppTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.chucker_network_tutorial),
            style = AppTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        val annotatedString = buildAnnotatedString {
            val fullText = stringResource(R.string.chucker_check_readme)
            val linkText = "README.md"
            val startIndex = fullText.indexOf(linkText)
            append(fullText)
            if (startIndex >= 0) {
                addStyle(
                    style = SpanStyle(
                        color = AppTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    ),
                    start = startIndex,
                    end = startIndex + linkText.length
                )
                addStringAnnotation(
                    tag = "URL",
                    annotation = readmeUrl,
                    start = startIndex,
                    end = startIndex + linkText.length
                )
            }
        }

        ClickableText(
            text = annotatedString,
            style = AppTheme.typography.bodyMedium.copy(color = AppTheme.colorScheme.onSurface),
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, annotation.item.toUri()))
                        } catch (_: Exception) {}
                    }
            },
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@AppPreview
@Composable
private fun ChuckerTutorialSectionPreview() {
    AppTheme {
        ChuckerTutorialSection()
    }
}
