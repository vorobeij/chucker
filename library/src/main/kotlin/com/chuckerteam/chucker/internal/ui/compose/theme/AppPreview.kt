package com.chuckerteam.chucker.internal.ui.compose.theme
import android.content.res.Configuration
import androidx.annotation.RestrictTo
import androidx.compose.ui.tooling.preview.Preview

@RestrictTo(RestrictTo.Scope.TESTS)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, device = "spec:width=1080px,height=2000px,dpi=440,orientation=portrait", backgroundColor = 0xFFFFFFFF, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, device = "spec:width=1080px,height=2000px,dpi=440,orientation=portrait", backgroundColor = 0xFF0E0E0E, showBackground = true)
internal annotation class AppPreview
