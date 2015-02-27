package com.x256n.android.library.view

import com.x256n.android.library.controller.BaseActivityController
import android.view.View
import com.x256n.android.library.common.Utils
import android.os.Bundle
import com.x256n.android.library.controller
import com.x256n.android.library.controller.ActivityController
import android.app.Activity

/**
 * @author Aleksey Permyakov (13.02.2015)
 */
public abstract class BaseActivityView<C : Activity> {
    protected abstract val controller: C
    protected abstract val menuLayoutId: Int
    protected abstract val layoutId: Int
    protected abstract var view: View
    public fun <T : View> view(viewId: Int?): T? = Utils.view(view, viewId)
    public fun <T : View> inflate(viewId: Int): T? = controller.getLayoutInflater().inflate(viewId, null) as? T
}