package co.devhack.droidremotelogging.logging

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import co.devhack.droidremotelogging.domain.DeviceDetails
import co.devhack.droidremotelogging.domain.RemoteLog
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class TimberRemoteLogging(
    private val context: Context,
    private val deviceDetails: DeviceDetails
) : Timber.Tree() {

    companion object {
        private const val DEFAULT_LOG = "INFO"
        private const val SPLIT_CHARACTER = ","
        private const val PACKAGE_ENABLE = "ai.wordbox.remotelogging.enable"
        private const val LOG_TYPES = "ai.wordbox.remotelogging.logtypes"
    }

    private val timeFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS a zzz", Locale.getDefault())

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isEnableRemoteLogging()) {
            val logTypes = getLogTypes()
            if (!logTypes.contains(priority)) {
                return
            }
            val timestamp = System.currentTimeMillis()
            val time = timeFormat.format(Date(timestamp))
            val remoteLog = RemoteLog(priorityAsString(priority), tag, message, t?.message, time)
            FirestoreLogger.log(remoteLog, deviceDetails)
        }
    }

    private fun isEnableRemoteLogging(): Boolean {
        val appInfo = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )
        return appInfo.metaData.getBoolean(PACKAGE_ENABLE)
    }

    private fun getLogTypes(): List<Int> {
        val appInfo = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )
        return appInfo.metaData.getString(LOG_TYPES, DEFAULT_LOG)
            .split(SPLIT_CHARACTER)
            .map {
                getPriority(it.trim())
            }
    }

    private fun getPriority(logType: String): Int = when (logType) {
        "VERBOSE" -> Log.VERBOSE
        "DEBUG" -> Log.DEBUG
        "INFO" -> Log.INFO
        "WARN" -> Log.WARN
        "ERROR" -> Log.ERROR
        "ASSERT" -> Log.ASSERT
        else -> Log.INFO
    }

    private fun priorityAsString(priority: Int): String = when (priority) {
        Log.VERBOSE -> "VERBOSE"
        Log.DEBUG -> "DEBUG"
        Log.INFO -> "INFO"
        Log.WARN -> "WARN"
        Log.ERROR -> "ERROR"
        Log.ASSERT -> "ASSERT"
        else -> priority.toString()
    }
}
