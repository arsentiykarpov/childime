package cloud.karpov.services

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class InputAccessibilityListenerService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        when (event.eventType) {

            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val text = event.text?.joinToString("") ?: ""
                val pkg = event.packageName ?: ""
                val id = event.source?.viewIdResourceName ?: ""

                Log.d("AccService", "Text changed: '$text' in $pkg id=$id")
            }

            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                val id = event.source?.viewIdResourceName
                Log.d("AccService", "Focused: $id")
            }
        }
    }

    override fun onInterrupt() { }
    
}

