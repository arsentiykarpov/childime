package cloud.karpov.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import dev.patrickgold.florisboard.FlorisApplicationReference
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen() {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    val context = FlorisApplicationReference.get()
    val editorInstance by context!!.editorInstance

    val content = editorInstance.activeContentFlow.collectAsState()
    MainScope().launch {
    content.apply {
      output = this.value.text
    }
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    )
    {
        TextField(
            value = input,
            onValueChange = { input = it },
            enabled = true,
            readOnly = false,
            label = { Text("Ввод") })

        TextField(
            value = output,
            onValueChange = { output = it },
            enabled = true,
            readOnly = false,
            label = { Text("Backend answer") })
    }
}
