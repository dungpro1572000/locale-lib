package com.dungz.locale.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dungz.locale.R
import com.dungz.locale.model.LanguageItem
import com.dungz.locale.provider.LocalLanguageCode
import com.dungz.locale.provider.LocalLocaleManager

/**
 * Language selection screen that displays available languages
 * and allows users to select their preferred language.
 *
 * @param onBackClick Callback when back/close is clicked
 * @param modifier Modifier for the screen
 */
@Composable
fun LanguageSelectionScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val localeManager = LocalLocaleManager.current
    val currentLanguageCode = LocalLanguageCode.current
    val availableLanguages = localeManager.availableLanguages

    var selectedLanguageCode by remember { mutableStateOf(currentLanguageCode) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            LanguageSelectionHeader(
                onSaveClick = {
                    localeManager.setLocale(selectedLanguageCode)
                    onBackClick()
                }
            )

            // Divider between header and content
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Language list
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    items = availableLanguages,
                    key = { _, item -> item.code }
                ) { index, languageItem ->
                    LanguageListItem(
                        languageItem = languageItem,
                        isSelected = selectedLanguageCode == languageItem.code,
                        onClick = { selectedLanguageCode = languageItem.code }
                    )

                    // Divider between items (not after the last item)
                    if (index < availableLanguages.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 72.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                }
            }
        }
    }
}

/**
 * Header component with title and save button.
 */
@Composable
private fun LanguageSelectionHeader(
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.locale_select_language),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        TextButton(onClick = onSaveClick) {
            Text(
                text = stringResource(R.string.locale_save),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
        }
    }
}

/**
 * Individual language item in the list.
 */
@Composable
private fun LanguageListItem(
    languageItem: LanguageItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Flag
        Image(
            painter = painterResource(id = languageItem.flag),
            contentDescription = stringResource(languageItem.name),
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Language name
        Text(
            text = stringResource(languageItem.name),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )

        // Radio button
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}
