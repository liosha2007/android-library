package com.x256n.android.library.controller

import com.x256n.android.library.view.ActivityView
import android.support.v4.app.FragmentActivity
import android.os.Bundle
import android.content.Intent
import com.x256n.android.library.common.Utils
import com.x256n.android.library.view.ActivityView
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity

/**
 * @author liosha (13.02.2015)
 */
public abstract class BaseActivityController<V, C> : SlidingFragmentActivity() {
    protected abstract val view: V

    public override fun onSaveInstanceState(storage: Bundle) {
        super.onSaveInstanceState(storage)
        storage.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE")
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    /**
     * @param newActivityAnimation new activity animation resource
     */
    public fun onBackPressed(newActivityAnimation: Int) {
        onBackPressed(newActivityAnimation, 0)
    }

    public fun onBackPressed(newActivityAnimation: Int, oldActivityAnimation: Int) {
        super.onBackPressed()
        overridePendingTransition(newActivityAnimation, oldActivityAnimation)
    }

    public fun run(clazz: Class<C>): Unit = run(clazz, null, 0, 0)

    public fun run(clazz: Class<C>, newActivityAnimation: Int, oldActivityAnimation: Int): Unit = run(clazz, null, newActivityAnimation, oldActivityAnimation)

    public fun run(clazz: Class<C>, bundle: Bundle): Unit = run(clazz, bundle, 0)

    public fun run(clazz: Class<C>, bundle: Bundle, newActivityAnimation: Int): Unit = run(clazz, bundle, newActivityAnimation, 0)

    public fun run(clazz: Class<C>, newActivityAnimation: Int): Unit = run(clazz, null, newActivityAnimation, 0)

    protected fun run(clazz: Class<C>, bundle: Bundle?, newActivityAnimation: Int, oldActivityAnimation: Int) {
        val intent = Intent(this, clazz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        overridePendingTransition(newActivityAnimation, oldActivityAnimation)
        Utils.deb("Activity " + clazz.getSimpleName() + " is started!")
    }
}


