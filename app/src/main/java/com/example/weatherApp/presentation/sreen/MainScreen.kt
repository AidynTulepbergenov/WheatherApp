package com.example.weatherApp.presentation.sreen

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.weatherApp.domain.constant.Const
import com.example.weatherApp.domain.model.LatLng
import com.example.weatherApp.presentation.MainController
import com.example.weatherApp.presentation.MainViewModel
import com.example.weatherApp.presentation.UiState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

//@Composable
//fun MainScreen(context: Context, currentLocation: LatLng, viewModel: MainViewModel) {
//    val uiState by viewModel.uiState.collectAsState()
//
//    val launcherMultiplePermissions = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions(),
//    ) { permissionMap ->
//        val areGranted = permissionMap.values.reduce { accepted, next ->
//            accepted && next
//        }
//        if (areGranted) {
//            locationRequired = true
//            startLocationUpdate()
//        } else {
//            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
//        }
//    }
//    LaunchedEffect(key1 = currentLocation, block = {
//        coroutineScope {
//            if (isAllPermissionGranted(context)) {
//                startLocationUpdate()
//            } else {
//                launcherMultiplePermissions.launch(Const.permissions)
//            }
//        }
//    })
//
//    LaunchedEffect(key1 = true, block = {
//        delay(500L)
//        fetchWeatherData(controller = viewModel, currentLocation = currentLocation)
//    })
//
//    val backGradient = Brush.linearGradient(
//        colors = listOf(Color(0xff33C7FF), Color(0xff00678E)),
//        start = Offset(1000f, -1000f),
//        end = Offset(1000f, 1000f),
//    )
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(backGradient),
//    ) {
//
//        Column(
//            modifier = Modifier
//                .verticalScroll(rememberScrollState())
//                .padding(top = 36.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            when (uiState) {
//                is UiState.LoadingState -> LoadingScreen()
//
//                is UiState.ErrorState -> {
//                    ErrorScreen(
//                        message = (uiState as UiState.ErrorState).message,
//                    )
//                }
//
//                is UiState.DataState -> {
//                    DataScreen(uiState as UiState.DataState)
//                }
//            }
//
//            FloatingActionButton(
//                modifier = Modifier.padding(bottom = 16.dp),
//                onClick = {
//                    fetchWeatherData(controller = viewModel, currentLocation = currentLocation)
//                },
//            ) {
//                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
//            }
//        }
//    }
//}
//
//private fun fetchWeatherData(controller: MainController, currentLocation: LatLng) {
//    controller.fetchData(currentLocation)
//}
//
//private fun isAllPermissionGranted(context: Context): Boolean = Const.permissions.all { permission ->
//    ContextCompat.checkSelfPermission(
//        context,
//        permission,
//    ) == PackageManager.PERMISSION_GRANTED
//}
