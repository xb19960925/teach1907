package com.teach.teach1907.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.teach.data.GroupDetailEntity;
import com.teach.teach1907.R;
import com.teach.teach1907.interfaces.OnRecyclerItemClick;

import java.util.List;

public class GroupDetailPopAdapter extends RecyclerView.Adapter<GroupDetailPopAdapter.ViewHolder> {
    private Context mContext;
    private List<GroupDetailEntity.Tag.SelectsBean> mPopData;
    private List<String> mStrings;

    public GroupDetailPopAdapter(Context pContext, List<GroupDetailEntity.Tag.SelectsBean> pPopData, List<String> pStrings) {
        mContext = pContext;
        mPopData = pPopData;
        mStrings = pStrings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.group_detail_pop_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupDetailEntity.Tag.SelectsBean bean = mPopData.get(position);
        holder.popTab.setText(bean.getName());
        holder.popTab.setTextColor(ContextCompat.getColor(mContext, mStrings.contains(bean.getName()) ? R.color.white : R.color.black));
        holder.popTab.setBackground(ContextCompat.getDrawable(mContext,mStrings.contains(bean.getName())? R.drawable.shape_group_pop_item_bg : R.drawable.shape_group_pop_item_bg_unselected));
        holder.popTab.setOnClickListener(v -> {
            if (mOnRecyclerItemClick != null) mOnRecyclerItemClick.onItemClick(position);
        });
    }

    private OnRecyclerItemClick mOnRecyclerItemClick;

    public void setOnRecyclerItemClick(OnRecyclerItemClick pOnRecyclerItemClick) {
        mOnRecyclerItemClick = pOnRecyclerItemClick;
    }

    @Override
    public int getItemCount() {
        return mPopData != null ? mPopData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView popTab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popTab = itemView.findViewById(R.id.tabPopItem);
        }
    }
}

