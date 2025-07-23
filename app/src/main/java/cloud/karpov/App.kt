package cloud.karpov

import android.app.Application
import cloud.karpov.rest.RestClient
import dev.patrickgold.florisboard.FlorisApplication
import dev.patrickgold.florisboard.florisApplication
import java.lang.ref.WeakReference


public var appContext = WeakReference<App?>(null)
public var keyboardApp = WeakReference<FlorisApplication?>(null)
public var restClient = WeakReference<RestClient?>(null)

class App : Application(){

    var florisApp: FlorisApplication? = null
    override fun onCreate() {
        super.onCreate()
        appContext = WeakReference(this)
        florisApp = this.florisApplication()
        keyboardApp = WeakReference<FlorisApplication?>(florisApp)
        restClient = WeakReference<RestClient?>(RestClient())
    }

    fun restClient(): RestClient {
        if (restClient.get() == null) {
            restClient = WeakReference<RestClient?>(RestClient())
        }
      return restClient.get()!!
    }

}
