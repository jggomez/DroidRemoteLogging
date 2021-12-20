package co.devhack.droidremotelogging.logging

import android.util.Log
import co.devhack.droidremotelogging.domain.DeviceDetails
import co.devhack.droidremotelogging.domain.RemoteLog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirestoreLogger {

    private const val LOG_COLLECTION = "RemoteLogging"

    fun log(log: RemoteLog, deviceDetails: DeviceDetails) {
        val db = Firebase.firestore

        val deviceDocument = hashMapOf(
            "deviceid" to deviceDetails.deviceId,
            "osversion" to deviceDetails.osVersion,
            "manufacturer" to deviceDetails.manufacturer,
            "brand" to deviceDetails.brand,
            "device" to deviceDetails.device,
            "model" to deviceDetails.model,
            "appVersionName" to deviceDetails.appVersionName,
            "appVersionCode" to deviceDetails.appVersionCode,
        )

        val logDocument = hashMapOf(
            "priority" to log.priority,
            "tag" to log.tag,
            "message" to log.message,
            "error" to log.error,
            "time" to log.time,
            "devicedetails" to deviceDocument
        )

        db.collection(LOG_COLLECTION).add(logDocument)
            .addOnSuccessListener {
                Log.v("FirestoreLogger", "Successful registration in firestore")
            }.addOnFailureListener {
                Log.v("FirestoreLogger", "Remote registry error in firestore ${it.message}")
            }
    }
}
