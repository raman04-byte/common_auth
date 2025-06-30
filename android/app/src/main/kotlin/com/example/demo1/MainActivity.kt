package com.example.demo1

import android.content.Context
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.example.shared/auth"

    override fun configureFlutterEngine(flutterEngine: io.flutter.embedding.engine.FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            if (call.method == "writeToken") {
                val token = call.argument<String>("token")
                val prefs = getSharedPreferences("shared_auth_storage", Context.MODE_PRIVATE)
                prefs.edit().putString("auth_token", token).apply()
                result.success(true)
            } else {
                result.notImplemented()
            }
        }
    }
}
