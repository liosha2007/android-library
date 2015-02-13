package com.x256n.android.library.view

import com.x256n.android.library.controller.IActivityController
import android.os.Bundle

/**
 * @author liosha (13.02.2015)
 */
public trait IActivityView<T: IActivityController> {

    public fun setController(controller: T): IActivityView<T>

    public fun onCreate()
}