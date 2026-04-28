package com.chuckerteam.chucker.internal.ui.compose.screens.main.views

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chuckerteam.chucker.internal.data.repository.search.SearchFilter
import com.chuckerteam.chucker.internal.ui.compose.screens.main.search.MySearchBar
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyAppBar(
    modifier: Modifier = Modifier,
    onSearch: (SearchFilter) -> Unit = {},
    onOpenFilters: () -> Unit = {},
) {
    MySearchBar(
        onSearch = onSearch
    )
}

@AppPreview
@Composable
private fun Preview() {
    AppTheme {
        MyAppBar()
    }
}
