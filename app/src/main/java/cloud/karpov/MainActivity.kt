package cloud.karpov

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import cloud.karpov.di.AuthModule
import cloud.karpov.domain.repository.AuthRepository
import cloud.karpov.navigation.AppNavigation
import cloud.karpov.ui.LoginScreen
import cloud.karpov.keyboardApp
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val context = keyboardApp.get()
    setContent {
      AppNavigation()
    }
  }

}
