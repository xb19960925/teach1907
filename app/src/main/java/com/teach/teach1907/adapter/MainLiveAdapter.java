package com.teach.teach1907.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.teach.data.BannerLiveInfo;
import com.teach.teach1907.R;
import com.yiyatech.utils.newAdd.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainLiveAdapter extends RecyclerView.Adapter<MainLiveAdapter.ViewHolder> {
    private Context mContext;
    private List<BannerLiveInfo.Live> liveData;


    public MainLiveAdapter(Context pContext, List<BannerLiveInfo.Live> pLiveData) {
        mContext = pContext;
        liveData = pLiveData;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_main_live_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(mContext).load(liveData.get(position).teacher_photo).apply(requestOptions).into(holder.photo);
        holder.activityName.setText(liveData.get(position).activityName);
        if (!TextUtils.isEmpty(liveData.get(position).startTime))holder.time.setText(TimeUtil.formatLiveTime(Long.parseLong(liveData.get(position).startTime)));
    }

    @Override
    public int getItemCount() {
        return liveData.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo)
        ImageView photo;
        @BindView(R.id.activityName)
        TextView activityName;
        @BindView(R.id.time)
        TextView time;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
