package com.example.weatherApp.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.weatherApp.domain.constant.Const.Companion.backGradient
import com.example.weatherApp.domain.constant.Const.Companion.permissions
import com.example.weatherApp.domain.model.LatLng
import com.example.weatherApp.presentation.sreen.DataScreen
import com.example.weatherApp.presentation.sreen.ErrorScreen
import com.example.weatherApp.presentation.sreen.LoadingScreen
import com.example.weatherApp.presentation.sreen.SearchWidget
import com.example.weatherApp.presentation.theme.ui.WheatherAppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.coroutineScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationRequired: Boolean = false

    //    private val enterPreference = WeatherPreference(context = this)
    private val viewModel by viewModel<MainViewModel>()

    override fun onResume() {
        super.onResume()
        if (locationRequired) startLocationUpdate()
    }

    override fun onPause() {
        super.onPause()
        locationCallback.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLocation()

        setContent {
            WheatherAppTheme {
                var currentLocation by remember {
                    mutableStateOf(LatLng(lat = 0.0, lng = 0.0))
                }

                locationCallback = object : LocationCallback() {
                    override fun onLocationResult(p0: LocationResult) {
                        super.onLocationResult(p0)
                        for (location in p0.locations) {
                            currentLocation = LatLng(
                                lat = location.latitude,
                                lng = location.longitude,
                            )
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen(
                        context = this@MainActivity,
                        currentLocation = currentLocation,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }

    @Composable
    fun MainScreen(context: Context, currentLocation: LatLng, viewModel: MainViewModel) {
        val uiState by viewModel.uiState.collectAsState()

        val launcherMultiplePermissions = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissionMap ->
            val areGranted = permissionMap.values.reduce { accepted, next ->
                accepted && next
            }
            if (areGranted) {
                locationRequired = true
                startLocationUpdate()
            } else {
                Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
        LaunchedEffect(key1 = currentLocation, block = {
            coroutineScope {
                if (isAllPermissionGranted(context)) {
                    startLocationUpdate()
                } else {
                    launcherMultiplePermissions.launch(permissions)
                }
            }
        })

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backGradient),
        ) {

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SearchWidget(controller = viewModel)

                when (uiState) {
                    is UiState.EmptyState -> Unit
                    is UiState.LoadingState -> LoadingScreen()

                    is UiState.ErrorState -> {
                        ErrorScreen(
                            message = (uiState as UiState.ErrorState).message,
                        )
                    }

                    is UiState.DataState -> {
                        DataScreen(uiState as UiState.DataState)
                    }
                }

                MainScreenFooter(currentLocation = currentLocation)
            }
        }
    }

    @Composable
    private fun MainScreenFooter(currentLocation: LatLng) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CommonButton(text = "Current Location Weather") {
                fetchWeatherData(
                    controller = viewModel,
                    currentLocation = currentLocation,
                )
            }
        }
    }

    @Composable
    fun CommonButton(
        text: String,
        content: @Composable RowScope.() -> Unit = {
            Text(
                text = text,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
            )
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.Unspecified,
            )

        },
        onClick: () -> Unit,
    ) {
        Button(
            onClick = onClick,
            content = content,
            contentPadding = ButtonDefaults.ContentPadding,
        )
    }

    private fun fetchWeatherData(controller: MainController, currentLocation: LatLng) {
        controller.fetchData(currentLocation)
    }

    private fun isAllPermissionGranted(context: Context): Boolean = permissions.all { permission ->
        ContextCompat.checkSelfPermission(
            context,
            permission,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {
        locationCallback.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100,
            ).setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100).build()

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper(),
            )
        }
    }
}