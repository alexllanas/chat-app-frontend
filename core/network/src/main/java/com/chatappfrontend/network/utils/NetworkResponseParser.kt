package com.chatappfrontend.network.utils

import android.util.Log
import org.json.JSONObject


object NetworkResponseParser {

     fun getErrorMessage(errorBody: String?): String {
        if (!errorBody.isNullOrBlank()) {
            try {
                val jsonObject = JSONObject(errorBody)
                Log.d("NetworkResponseParser", "Parsed error body: $jsonObject")
                return jsonObject.optString("error", "Unknown error occurred")
            } catch (e: Exception) {
                Log.e("NetworkResponseParser", "Error parsing error body: ${e.message}")
                return "Exception thrown while parsing response: ${e.message}"
            }
        }
        return "Unknown error occurred"
    }
}