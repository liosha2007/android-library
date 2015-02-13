package com.x256n.android.library.view

import com.x256n.android.library.controller.ActivityController
import com.x256n.android.library.controller.IActivityController
import android.view.View
import com.x256n.android.library.common.Utils
import android.os.Bundle

/**
 * @author Aleksey Permyakov (13.02.2015)
 */
public abstract class ActivityView <T: IActivityController>(layoutId: Int): IActivityView<T> {
    val layoutId = layoutId
    protected abstract var controller: T
    protected abstract var view: View

    public override fun onCreate() {
        controller.setContentView(layoutId)
        view = controller.findViewById(android.R.id.content)
    }

    public fun <T: View> view(viewId: Int): T? = Utils.view(view, viewId)

    public fun <T: View> inflate(viewId: Int): T? = controller.getLayoutInflater().inflate(viewId, null) as? T

    public override fun setController(controller: T): IActivityView<T> {
        this.controller = controller
        return this
    }
}