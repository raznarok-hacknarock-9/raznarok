package com.example.raznarokmobileapp.guest.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.raznarokapp.core.presentation.components.CustomTextField
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.presentation.components.UserList
import com.example.raznarokmobileapp.guest.presentation.utils.convertDatesToString
import com.example.raznarokmobileapp.ui.theme.RaznarokMobileAppTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestHomeScreen(
    guestHomeState: GuestHomeState,
    onGuestHomeScreenEvent: (GuestHomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerState = rememberDateRangePickerState()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(R.dimen.padding_big))
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.find_local_hosts),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_big))
            )
            CustomTextField(
                value = guestHomeState.searchQuery,
                icon = R.drawable.ic_location,
                label = R.string.location,
                onValueChange = {
                    onGuestHomeScreenEvent(GuestHomeScreenEvent.LocationChanged(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
            CustomTextField(
                value = convertDatesToString(
                    datePickerState.selectedStartDateMillis,
                    datePickerState.selectedEndDateMillis
                ),
                icon = R.drawable.ic_date,
                label = R.string.dates,
                readOnly = true,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth().onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        isDatePickerVisible = !isDatePickerVisible
                    }
                }
            )

            Button(
                enabled = datePickerState.selectedStartDateMillis != null && datePickerState.selectedEndDateMillis != null,
                onClick = {
                    if (datePickerState.selectedStartDateMillis != null
                        && datePickerState.selectedEndDateMillis != null) {
                        onGuestHomeScreenEvent(
                            GuestHomeScreenEvent.FetchHosts(
                                datePickerState.selectedStartDateMillis!!,
                                datePickerState.selectedEndDateMillis!!
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.search_for_hosts),
                    modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
                )
            }

            AnimatedContent(
                targetState = guestHomeState.isFetchingHosts
            ) { isLoadingHosts ->
                if (isLoadingHosts) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp)
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = dimensionResource(R.dimen.padding_medium)
                            )
                    ) {
                        guestHomeState.searchedLocation?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.displaySmall
                            )
                        }
                        UserList(
                            users = guestHomeState.availableHosts,
                            onUserClick = {
                                onGuestHomeScreenEvent(
                                    GuestHomeScreenEvent.GoToHostProfile(it)
                                )
                            }
                        )
                    }
                }
            }
        }

        if (isDatePickerVisible) {
            DatePickerDialog(
                onDismissRequest = {
                    isDatePickerVisible = false
                },
                confirmButton = {
                    Button(
                        onClick = {
                            isDatePickerVisible = false
                        }
                    ) {
                        Text(text = stringResource(R.string.select))
                    }
                }
            ) {
                DateRangePicker(
                    state = datePickerState,
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    RaznarokMobileAppTheme {
        GuestHomeScreen(
            guestHomeState = GuestHomeState(),
            onGuestHomeScreenEvent = {}
        )
    }
}