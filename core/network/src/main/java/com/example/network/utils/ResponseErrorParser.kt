package com.example.network.utils

import org.json.JSONObject


object ResponseErrorParser {

     fun parseMessage(errorBody: String?): String {
        if (!errorBody.isNullOrBlank()) {
            try {
                // Assuming the error body is in JSON format
                val jsonObject = JSONObject(errorBody)
                return jsonObject.optString("error", "Unknown error occurred")
            } catch (e: Exception) {
                return "Error parsing error response: ${e.message}"
            }
        }
        return "Unknown error occurred"
    }
}