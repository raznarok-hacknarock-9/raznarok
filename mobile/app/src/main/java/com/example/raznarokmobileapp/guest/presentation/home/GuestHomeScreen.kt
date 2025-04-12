package com.example.raznarokmobileapp.guest.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.raznarokapp.core.presentation.components.CustomTextField
import com.example.raznarokmobileapp.R
import com.example.raznarokmobileapp.core.presentation.components.UserList
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            CustomTextField(
                value = guestHomeState.searchQuery,
                icon = R.drawable.ic_location,
                label = R.string.location,
                onValueChange = {
                    onGuestHomeScreenEvent(GuestHomeScreenEvent.LocationChanged(it))
                }
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
                modifier = Modifier.onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        isDatePickerVisible = !isDatePickerVisible
                    }
                }
            )

            OutlinedButton(
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
                }
            ) {
                Text(
                    text = stringResource(R.string.search_for_hosts)
                )
            }

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
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

fun convertDatesToString(start: Long?, end: Long?): String {
    if (start == null || end == null) {
        return "Select Dates"
    }
    val startDate = start.toLocalDate()
    val startDateText = startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    val endDate = end.toLocalDate()
    val endDateText = endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    return "$startDateText - $endDateText"
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
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