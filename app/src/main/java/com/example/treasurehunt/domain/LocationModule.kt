package com.example.treasurehunt.domain

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.example.treasurehunt.data.model.UserLocation
import com.google.android.gms.location.DeviceOrientationListener
import com.google.android.gms.location.DeviceOrientationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    @Provides
    fun providesDeviceOrientationRequest(): DeviceOrientationRequest =
        DeviceOrientationRequest.Builder(1000).build()

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    @Provides
    fun providesDeviceOrientationUpdates(
        @ApplicationContext context: Context,
        deviceOrientationRequest: DeviceOrientationRequest
    ): Flow<Float> = callbackFlow {
        // Return a flow of device orientation updates
        LocationServices.getFusedOrientationProviderClient(context)
            .let { fusedOrientationProviderClient ->
                val deviceOrientationListener = DeviceOrientationListener { orientation ->
                    trySendBlocking(orientation.headingDegrees)
                }
                fusedOrientationProviderClient.requestOrientationUpdates(
                    deviceOrientationRequest,
                    Executors.newFixedThreadPool(12),
                    deviceOrientationListener
                )

                awaitClose {
                    fusedOrientationProviderClient.removeOrientationUpdates(
                        deviceOrientationListener
                    )
                }
            }
    }


    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    @Provides
    fun providesLocationRequest(@ApplicationContext context: Context): LocationRequest =
        context.takeIf {
            it.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        }
            .let {
                when (it) {
                    is Context -> Priority.PRIORITY_BALANCED_POWER_ACCURACY
                    else -> Priority.PRIORITY_HIGH_ACCURACY
                }
            }
            .let { priority ->
                LocationRequest.Builder(priority, TimeUnit.SECONDS.toMillis(3)).build()
            }

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    @Provides
    fun provideLocationUpdates(
        @ApplicationContext context: Context, locationRequest: LocationRequest
    ): Flow<UserLocation> = callbackFlow {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.lastOrNull()?.let { location ->
                    try {
                        trySendBlocking(UserLocation(location.latitude, location.longitude))
                    } catch (e: Exception) {
                        // Log.e("LocationModule", "Error sending location", e)
                    }
                }
            }
        }

        LocationServices.getFusedLocationProviderClient(context)
            .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

        awaitClose {
            LocationServices.getFusedLocationProviderClient(context)
                .removeLocationUpdates(locationCallback)
        }

    }

}

