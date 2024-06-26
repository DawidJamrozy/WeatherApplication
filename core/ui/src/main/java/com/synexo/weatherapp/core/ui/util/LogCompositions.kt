package com.synexo.weatherapp.core.ui.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import com.synexo.weatherapp.core.ui.BuildConfig

/**
 * An effect which longs the number compositions at the invoked point of the slot table.
 * Thanks to [objcode](https://github.com/objcode) for this code.
 *
 * This is an inline function to act as like a C-style #include to the host composable function.
 * That way we track it's compositions, not this function's compositions.
 *
 * @param tag Log tag used for [Log.d]
 */
@Composable
fun LogCompositions(tag: String, message: String) {
    if (EnableDebugCompositionLogs && BuildConfig.DEBUG) {
        val ref = remember { Ref(0) }
        SideEffect { ref.value++ }
        Log.d(tag, "Compositions: $message ${ref.value}")
    }
}

class Ref(var value: Int)

const val EnableDebugCompositionLogs = true
