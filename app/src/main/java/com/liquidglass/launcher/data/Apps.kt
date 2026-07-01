package com.liquidglass.launcher.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.runtime.staticCompositionLocalOf

data class AppInfo(
    val label: String,
    val packageName: String,
    val activityName: String,
    val icon: Drawable
) {
    val id: String get() = "$packageName/$activityName"
}

class AppRepository(private val context: Context) {

    fun load(): List<AppInfo> {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        val resolved = pm.queryIntentActivities(intent, 0)
        return resolved.mapNotNull { info ->
            val ai = info.activityInfo ?: return@mapNotNull null
            AppInfo(
                label = info.loadLabel(pm).toString(),
                packageName = ai.packageName,
                activityName = ai.name,
                icon = info.loadIcon(pm)
            )
        }.sortedBy { it.label.lowercase() }
    }

    fun launch(app: AppInfo) {
        val launch = context.packageManager.getLaunchIntentForPackage(app.packageName)
            ?: Intent().apply {
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
                setClassName(app.packageName, app.activityName)
            }
        launch.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        )
        try {
            context.startActivity(launch)
        } catch (_: PackageManager.NameNotFoundException) {
        } catch (_: SecurityException) {
        }
    }
}

val LocalAppRepository = staticCompositionLocalOf<AppRepository> {
    error("AppRepository not provided")
}
