package com.synexo.weatherapp.util

import com.synexo.weatherapp.R

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): Int
}

class LocationPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): Int {
        return if(isPermanentlyDeclined) {
            R.string.permission_dialog_permanently_decline
        } else {
            R.string.permission_dialog_explanation
        }
    }
}