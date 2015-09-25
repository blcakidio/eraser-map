package com.mapzen.erasermap.view

import android.preference.Preference
import com.mapzen.erasermap.BuildConfig
import com.mapzen.erasermap.PrivateMapsTestRunner
import com.mapzen.erasermap.model.AndroidAppSettings
import com.mapzen.erasermap.view.SettingsActivity.SettingsFragment
import com.mapzen.valhalla.Router
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.setupActivity
import org.robolectric.RuntimeEnvironment.application
import org.robolectric.annotation.Config

@RunWith(PrivateMapsTestRunner::class)
@Config(constants = BuildConfig::class, sdk=intArrayOf(21))
public class SettingsActivityTest {
    val settingsActivity = setupActivity(javaClass<SettingsActivity>())
    val settingsFragment = settingsActivity.getFragmentManager()
            .findFragmentById(android.R.id.content) as SettingsFragment

    fun findPreference(key: String): Preference = settingsFragment.findPreference(key)
    fun getString(resId: Int): String = application.getString(resId)

    @Test fun shouldNotBeNull() {
        assertThat(settingsActivity).isNotNull()
        assertThat(settingsFragment).isNotNull()
    }

    @Test fun onCreate_shouldSetDistanceUnitsSummaryDefaultValue() {
        settingsFragment.onCreate(null)
        assertThat(findPreference(AndroidAppSettings.KEY_DISTANCE_UNITS).getSummary())
                .isEqualTo("Miles")
    }

    @Test fun onCreate_shouldSetDistanceUnitsSummaryStoredValue() {
        settingsFragment.settings?.distanceUnits = Router.DistanceUnits.KILOMETERS
        settingsFragment.onCreate(null)
        assertThat(findPreference(AndroidAppSettings.KEY_DISTANCE_UNITS).getSummary())
                .isEqualTo("Kilometers")
    }

    @Test fun onPreferenceChange_shouldUpdateDistanceUnitsSummary() {
        val distanceUnitsPref = findPreference(AndroidAppSettings.KEY_DISTANCE_UNITS)

        settingsFragment.onPreferenceChange(distanceUnitsPref,
                Router.DistanceUnits.MILES.toString())
        assertThat(distanceUnitsPref.getSummary()).isEqualTo("Miles")

        settingsFragment.onPreferenceChange(distanceUnitsPref,
                Router.DistanceUnits.KILOMETERS.toString())
        assertThat(distanceUnitsPref.getSummary()).isEqualTo("Kilometers")
    }
}