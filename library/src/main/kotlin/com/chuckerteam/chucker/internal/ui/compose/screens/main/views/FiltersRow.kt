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
import com.chuckerteam.design.system.components.FilterChipRow
import com.chuckerteam.design.system.components.FilterOption
import com.chuckerteam.design.system.components.icons.Filters
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FiltersRow(
    modifier: Modifier = Modifier,
) {
    val filterOptions = remember {
        listOf(
            FilterOption("settings", "", icon = {
                Icon(
                    imageVector = Icons.Default.Filters,
                    contentDescription = ""
                )
            }),
            FilterOption("all", "All"),
            FilterOption("network", "Network", icon = { /* Icon here */ }),
            FilterOption("database", "Database"),
            FilterOption("ui", "UI"),
            FilterOption("performance", "Performance"),
            FilterOption("security", "Security"),
            FilterOption("other", "Other")
        )
    }
    var selectedFilters by remember { mutableStateOf(setOf<String>("all")) }

    FilterChipRow(
        options = filterOptions,
        selectedIds = selectedFilters,
        onSelectionChange = { selectedFilters = it },
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
