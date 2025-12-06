package cloud.karpov.home.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import cloud.karpov.keyboardApp
import cloud.karpov.navigation.AppNavigation

class MainActivity : AppCompatActivity() {

  @SuppressLint("ServiceCast")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val context = keyboardApp.get()
    setContent {
        AppNavigation()
    }
    openAccessibilitySettings()
    //startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
    //val imeManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    //imeManager.showInputMethodPicker()
  }
    private fun openAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        this.startActivity(intent)
    }

}
