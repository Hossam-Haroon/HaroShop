package com.example.e_commerceapp.presentation.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

@Composable
fun ImageSearchScreen(
    onSearchByLabel: (String) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        uri?.let {
            processImageLabeling(context, it, onSearchByLabel)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        imageUri?.let {
            AsyncImage(
                model = it,
                contentDescription = "Selected image",
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { pickImageLauncher.launch("image/*") }) {
            Text("Choose Image")
        }
    }
}
fun processImageLabeling(
    context: Context,
    imageUri: Uri,
    onLabelDetected: (String) -> Unit
) {
    val image = InputImage.fromFilePath(context, imageUri)
    val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

    labeler.process(image)
        .addOnSuccessListener { labels ->
            if (labels.isNotEmpty()) {
                val topLabel = labels.maxByOrNull { it.confidence }?.text ?: ""
                Log.d("ML", "Detected label: $topLabel")
                onLabelDetected(topLabel.lowercase())
            } else {
                Log.d("ML", "No label detected")
            }
        }
        .addOnFailureListener {
            Log.e("ML", "Labeling failed", it)
        }
}

