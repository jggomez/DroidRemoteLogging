package co.devhack.droidremotelogging.domain

data class RemoteLog(
    var priority: String,
    var tag: String?,
    var message: String,
    var error: String?,
    val time: String
)

data class DeviceDetails(
    val deviceId: String,
    val osVersion: String = "",
    val manufacturer: String = "",
    val brand: String = "",
    val device: String = "",
    val model: String = "",
    val appVersionName: String = "",
    val appVersionCode: Int = 0
)
