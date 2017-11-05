package com.assignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignment.R;
import com.assignment.models.SlidingItemMenu;

import java.util.List;

/**
 * Summary: Common adapter for sliding drawer and Point 1 of scenario 1
 */

public class SlidingMenuRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private List<SlidingItemMenu> menuList;
    private Context mContext;
    private int flag;

    public SlidingMenuRecyclerViewAdapter(List<SlidingItemMenu> menuList, Context context, int flag) {
        this.menuList = menuList;
        this.mContext = context;
        this.flag = flag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (flag == 0) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_sliding_item, parent, false);
                ItemViewHolder viewHolder = new ItemViewHolder(v);
                return viewHolder;
            }
        }
        if(flag==1)
        {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scenario1_point_1_item, parent, false);
                ItemViewHolder viewHolder = new ItemViewHolder(v);
                return viewHolder;
            }
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                if (holder instanceof ItemViewHolder) {
                    ItemViewHolder item_holder = (ItemViewHolder) holder;
                    SlidingItemMenu item = getItem(position);
                    item_holder.title.setText(item.title);
                }
                break;
        }
    }


    private SlidingItemMenu getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return (menuList.size());
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.row_title);
        }
    }
}