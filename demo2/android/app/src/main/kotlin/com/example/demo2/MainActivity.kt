package com.example.demo2

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.example.shared/auth"

    override fun configureFlutterEngine(flutterEngine: io.flutter.embedding.engine.FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            if (call.method == "readToken") {
                try {
                    val contextDemo1 = createPackageContext("com.example.demo1", Context.CONTEXT_IGNORE_SECURITY)
                    val prefs = contextDemo1.getSharedPreferences("shared_auth_storage", Context.MODE_PRIVATE)
                    val token = prefs.getString("auth_token", null)
                    result.success(token)
                } catch (e: PackageManager.NameNotFoundException) {
                    result.error("ContextError", "App not found", null)
                }
            } else {
                result.notImplemented()
            }
        }
    }
}
