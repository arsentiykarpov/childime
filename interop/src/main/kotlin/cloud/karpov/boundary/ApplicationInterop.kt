package cloud.karpov.boundary

import android.app.Application

interface ApplicationInterop<out APP: Application> {
  fun application(): APP
}
