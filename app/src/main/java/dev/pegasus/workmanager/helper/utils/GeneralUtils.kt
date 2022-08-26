package dev.pegasus.workmanager.helper.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar

object GeneralUtils {

    const val TAG = "MyTag"

    fun withDelay(delay: Long = 300, block: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(block, delay)
    }

    fun Activity.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.showToast(message: String) {
        this.view?.let {
            Toast.makeText(it.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun Fragment.showSnackBar(message: String) {
        this.view?.let {
            Snackbar.make(it.rootView, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    fun Fragment.showToast(stringId: Int) {
        this.view?.let {
            val message = it.resources.getString(stringId)
            Toast.makeText(it.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * TODO
     *     Used launchWhenResumed, bcz of screen rotation
     * @param fragment_id : Current Fragment's Id (from Nav Graph)
     * @param action : Action / Id of other fragment
     */

    fun Fragment.navigateTo(fragment_id: Int, action: Int) {
        lifecycleScope.launchWhenResumed {
            if (isCurrentDestination(fragment_id) && isAdded) {
                findNavController().navigate(action)
            }
        }
    }

    fun Fragment.navigateTo(fragment_id: Int, action: NavDirections) {
        lifecycleScope.launchWhenResumed {
            if (isCurrentDestination(fragment_id) && isAdded) {
                findNavController().navigate(action)
            }
        }
    }

    fun Fragment.popFrom(fragment_id: Int) {
        lifecycleScope.launchWhenResumed {
            if (isCurrentDestination(fragment_id) && isAdded) {
                findNavController().popBackStack()
            }
        }
    }

    private fun Fragment.isCurrentDestination(fragment_id: Int): Boolean {
        return findNavController().currentDestination?.id == fragment_id
    }

    fun ViewPager2.removeOverScroll() {
        (getChildAt(0) as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER
    }

    @Suppress("DEPRECATION")
    fun Activity.hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val controller = window.insetsController
            controller?.hide(WindowInsets.Type.ime())
            controller?.hide(WindowInsets.Type.systemBars())
            controller?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    @Suppress("DEPRECATION")
    fun Activity.showStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, true)
            val controller = window.insetsController
            controller?.show(WindowInsets.Type.systemBars())
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_VISIBLE)
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    @ColorInt
    fun Fragment.getColorFromAttr(@AttrRes attrColor: Int): Int {
        context?.let {
            val typedArray = it.theme.obtainStyledAttributes(intArrayOf(attrColor))
            val textColor = typedArray.getColor(0, 0)
            typedArray.recycle()
            return textColor
        } ?: return Color.BLACK
    }
}