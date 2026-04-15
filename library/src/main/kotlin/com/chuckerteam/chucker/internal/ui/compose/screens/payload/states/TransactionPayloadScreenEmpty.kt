package com.chuckerteam.chucker.internal.ui.compose.screens.payload.states

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.ui.compose.screens.payload.PayloadType
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@Composable
internal fun TransactionPayloadScreenEmpty(
    payloadType: PayloadType,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.chucker_empty_payload),
                contentDescription = stringResource(id = R.string.chucker_body_empty),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.chucker_color_primary)),
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.chucker_octa_grid))
                    .padding(bottom = dimensionResource(id = R.dimen.chucker_quad_grid))
            )
            Text(
                text = stringResource(
                    if (payloadType == PayloadType.RESPONSE) R.string.chucker_response_is_empty
                    else R.string.chucker_request_is_empty
                ),
                color = AppTheme.colorScheme.onBackground,
                style = AppTheme.typography.titleMedium
            )
        }
    }
}

@AppPreview
@Composable
private fun Preview(
    @PreviewParameter(StateEmptyPreviewProvider::class) state: PayloadType
) {
    AppTheme {
        TransactionPayloadScreenEmpty(state)
    }
}

private class StateEmptyPreviewProvider : PreviewParameterProvider<PayloadType> {
    override val values: Sequence<PayloadType> = sequenceOf(
        PayloadType.RESPONSE,
        PayloadType.REQUEST
    )
}
