package com.x256n.android.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liosha (12.04.2015)
 */
public class CustomAdapter<T> extends BaseAdapter {
    protected final LayoutInflater inflater;
    protected List<AdapterPair<T>> data = new ArrayList<>();
    protected int itemResourceId;
    protected int itemDropDownResourceId;
    protected IViewBinder<T> binder = new ViewBinder<T>();

    public CustomAdapter(Context context, int itemResourceId) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemResourceId = this.itemDropDownResourceId = itemResourceId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, itemResourceId);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, itemDropDownResourceId);
    }

    protected View createViewFromResource(int position, View convertView, ViewGroup parent, int resourceId) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(resourceId, parent, false);
        } else {
            view = convertView;
        }
        AdapterPair<T> adapterPair = data.get(position);
        if (adapterPair != null) {
            for (Integer viewId : adapterPair.views) {
                binder.setViewValue(view.findViewById(viewId), viewId, adapterPair.item, adapterPair.textRepresentation);
            }
        }
        return view;
    }

    /**
     * You should specify android:descendantFocusability="blocksDescendants"
     * to view container
     * @param itemResourceId
     */
    public void setItemResourceId(int itemResourceId) {
        this.itemResourceId = itemResourceId;
    }

    public void setItemDropDownResourceId(int itemDropDownResourceId) {
        this.itemDropDownResourceId = itemDropDownResourceId;
    }

    public void setBinder(IViewBinder<T> binder) {
        this.binder = binder;
    }

    public void addItem(AdapterPair<T> item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void setOnItemSelected(Spinner spinner, final IItemSelectedListener<T> onItemSelectedListener) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedListener.onItemSelected(parent, view, position, id, data.get(position).item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (onItemSelectedListener instanceof ItemSelectListener) {
                    ((ItemSelectListener<T>) onItemSelectedListener).onNothingSelected(parent);
                }
            }
        });
    }

    public static class AdapterPair<T> {
        public final T item;
        public final List<Integer> views = new ArrayList<>();
        public String textRepresentation = null;
        public boolean isSelectable = true;

        public AdapterPair(T item, Integer... view) {
            this(item, null, view);
        }

        public AdapterPair(T item, String textRepresentation, Integer... view) {
            this.item = item;
            this.textRepresentation = textRepresentation;
            this.views.addAll(Arrays.asList(view));
        }
    }

    public static interface IViewBinder<T> {
        boolean setViewValue(View view, Integer viewId, T data, String textRepresentation);
    }

    public static class ViewBinder<T> implements IViewBinder<T> {
        public boolean setViewValue(View view, Integer viewId, T data, String textRepresentation) {
            if (view instanceof TextView) {
                ((TextView) view).setText(textRepresentation == null ? "" : textRepresentation);
                return true;
            }
            return false;
        }
    }

    public static interface IItemSelectedListener<T> {
        void onItemSelected(AdapterView<?> parent, View view, int position, long id, T item);
    }

    public static abstract class ItemSelectListener<T> implements IItemSelectedListener<T> {
        void onNothingSelected(AdapterView<?> parent) {}
    }
}
