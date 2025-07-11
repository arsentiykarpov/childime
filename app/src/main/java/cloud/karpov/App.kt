package cloud.karpov

import android.app.Application
import java.lang.ref.WeakReference

public var appContext = WeakReference<App?>(null)
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    appContext = WeakReference(this)
  }
}