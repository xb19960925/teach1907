package com.teach.teach1907.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teach.data.SpecialtyChooseEntity;
import com.teach.teach1907.R;
import com.teach.teach1907.design.RoundImage;
import com.yiyatech.utils.newAdd.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    private Context mContext;
    private List<SpecialtyChooseEntity> mListData;

    public SubjectAdapter(Context mContext, List<SpecialtyChooseEntity> mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.subject_child_view, null));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecialtyChooseEntity entity = mListData.get(position);
        GlideUtil.loadImage(holder.leftRoundImage,entity.getIcon());
        holder.itemTitle.setText(entity.getBigspecialty());
        holder.itemRecyclerview.setLayoutManager(new GridLayoutManager(mContext,4));
        holder.itemRecyclerview.setAdapter(new SubjectChildAdapter(entity.getData(),mContext,this));
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.left_round_image)
        RoundImage leftRoundImage;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_recyclerview)
        RecyclerView itemRecyclerview;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
