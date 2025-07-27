package com.example.network

import org.json.JSONObject
import retrofit2.Response


object NetworkResponseParser {

    /**
     * Parses the response from a network call and returns a user-friendly error message.
     *
     * @param response The Retrofit response object.
     * @return A string containing the error message or a default message if parsing fails.
     */
    fun getErrorMessage(response: Response<*>): String {
        val errorBody = response.errorBody()?.string()
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