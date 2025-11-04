package com.example.e_commerceapp.data.local

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.searchDataStore by preferencesDataStore(name = "search_preferences")
val Context.imageUrlDataStore by preferencesDataStore(name = "imageUrl_preferences")