package com.chuckerteam.chucker.internal.ui.compose.screens.main.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chuckerteam.design.system.components.ChipType
import com.chuckerteam.design.system.components.FilterChipRow
import com.chuckerteam.design.system.components.FilterOption
import com.chuckerteam.design.system.components.icons.Filters
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FiltersRow(
    modifier: Modifier = Modifier,
    onOpenFilters: () -> Unit = {},
) {
    val filterOptions = remember {
        listOf(
            FilterOption(
                id = "settings",
                label = "Filters",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Filters,
                        contentDescription = ""
                    )
                },
                type = ChipType.ASSIST
            ),
            FilterOption("get", "GET"),
            FilterOption("post", "POST"),
            FilterOption("status_200", "200"),
        )
    }
    var selectedFilters by remember { mutableStateOf(setOf<String>("all")) }

    FilterChipRow(
        options = filterOptions,
        selectedIds = selectedFilters,
        onSelectionChange = { selectedFilters = it },
        onAssistClick = {
            when (it) {
                "settings" -> onOpenFilters()
            }
        },
        contentPadding = PaddingValues(horizontal = 8.dp)
    )
}


@AppPreview
@Composable
private fun Preview() {
    AppTheme {
        FiltersRow()
    }
}
