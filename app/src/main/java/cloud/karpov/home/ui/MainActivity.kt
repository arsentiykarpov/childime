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
    startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
    val imeManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imeManager.showInputMethodPicker()
  }

}
