package com.github.liosha2007.android.library.view;

import android.os.Bundle;
import android.view.View;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.controller.BaseController;
import org.apache.log4j.Logger;

/**
 * @author Aleksey Permyakov
 */
public abstract class BaseView<T extends BaseController> {
    private static final Logger LOGGER = Logger.getLogger(BaseView.class);
    protected View view;
    protected T controller;
    private int layoutId;

    public BaseView(int layoutId) {
        this.layoutId = layoutId;
    }

    public void onCreate() {
        controller.setContentView(layoutId);
        view = controller.findViewById(android.R.id.content);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return (this.view = inflater.inflate(this.layoutId, container, false));
//    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        controller.onViewCreated(savedInstanceState);
//        onViewCreated(view);
//    }

    /**
     * Get View by ID
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T view(int id) {
        if (view == null) {
            LOGGER.error("view is null");
            return null;
        }
        return (T) Utils.view(view, id);
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (controller == null) {
//            Utils.err("Error: Fragment created before controller!");
//        } else {
//            if (isVisibleToUser) {
//                controller.onShow();
//            } else {
//                controller.onHide();
//            }
//        }
//    }

    /**
     * Will be called when application should save data
     * @param storage to save application data
     */
    public void onSaveData(Bundle storage) {
        storage.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
    }

    /**
     * Will be called when application should restore data
     * @param storage to restore data
     */
    public void onRestoreData(Bundle storage) { }

    public void setController(T controller) {
        this.controller = controller;
    }
}
