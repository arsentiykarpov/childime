package cloud.karpov

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater

class HomeFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

}
