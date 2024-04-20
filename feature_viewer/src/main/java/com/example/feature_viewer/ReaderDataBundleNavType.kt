package com.example.feature_viewer

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

class ReaderDataBundleNavType: NavType<ReaderDataBundle>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): ReaderDataBundle? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): ReaderDataBundle {
        return Gson().fromJson(value, ReaderDataBundle::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: ReaderDataBundle) {
        bundle.putParcelable(key, value)
    }
}