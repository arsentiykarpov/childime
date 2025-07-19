package clound.karpov.interop

import java.lang.ref.WeakReference
import android.app.Application
import android.content.Context

class InteropContextWrapper {
    var app: WeakReference<Application> = WeakReference<Application>(null)
    var context: WeakReference<Context> = WeakReference<Context>(null)

    fun getOrPut(application: Application, context: Context): Application {
        if (app.get() == null && context is Application) {
            this.app = WeakReference<Application>(application)
            this.context = WeakReference<Context>(context)
            application.onCreate()
            return app.get()!!
        } else if (app.get() != null) {
            return app.get()!!
        } else {
            throw IllegalStateException("not App context")
        }
    }

    fun getApplication(): Application {
        return app.get()!!
    }

    fun getContext(): Context {
        return context.get()!!
    }

}


