package com.example.raznarokmobileapp.core.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.vertexai.type.HarmBlockThreshold
import com.google.firebase.vertexai.type.HarmCategory
import com.google.firebase.vertexai.type.ImagePart
import com.google.firebase.vertexai.type.SafetySetting
import com.google.firebase.vertexai.type.content
import com.google.firebase.vertexai.type.generationConfig
import com.google.firebase.vertexai.vertexAI
import java.io.ByteArrayOutputStream

class GeminiRepository {
    private val generativeModel = Firebase.vertexAI.generativeModel(
        "gemini-2.0-flash",
        generationConfig = generationConfig {
            temperature = 2f
            topP = 0.9f
        },
        safetySettings = listOf(
            SafetySetting(HarmCategory.HARASSMENT, HarmBlockThreshold.LOW_AND_ABOVE),
            SafetySetting(HarmCategory.HATE_SPEECH, HarmBlockThreshold.LOW_AND_ABOVE),
            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, HarmBlockThreshold.LOW_AND_ABOVE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, HarmBlockThreshold.LOW_AND_ABOVE),
        )
    )

    suspend fun writeStory(): String? {
        val prompt = "Write a short and funny story (less than 100 characters)"
        val result = generativeModel.generateContent(prompt).text
        return result
    }
}