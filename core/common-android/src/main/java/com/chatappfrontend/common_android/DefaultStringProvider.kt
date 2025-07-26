package com.example.common_android

import android.content.Context
import com.chatappfrontend.common_android.StringProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultStringProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : StringProvider {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}