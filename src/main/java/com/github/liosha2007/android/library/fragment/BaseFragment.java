package com.github.liosha2007.android.library.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.liosha2007.android.library.common.Utils;
import org.apache.log4j.Logger;

/**
 * @author Aleksey Permyakov
 */
public class BaseFragment extends Fragment {
    private static final Logger LOGGER = Logger.getLogger(BaseFragment.class);
    protected View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int layoutId) {
        return (this.view = inflater.inflate(layoutId, container, false));
    }

    /**
     * Get View by ID
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T view(int id) {
        if (view == null){
            LOGGER.error("view is null");
            return null;
        }
        return (T) Utils.view(view, id);
    }

    /**
     * Fix for bug
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}
