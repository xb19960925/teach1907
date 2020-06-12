package com.teach.teach1907.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.teach.data.VipBean;
import com.teach.teach1907.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VipLiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<VipBean.ResultBean.LiveBeanX.LiveBean> list;
    private VipAdapter mAdapter;

    public VipLiveAdapter(Context pContext, ArrayList<VipBean.ResultBean.LiveBeanX.LiveBean> pList, VipAdapter pAdapter) {
        mContext = pContext;
        list = pList;
        mAdapter = pAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_main_live_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(mContext).load(list.get(position).getTeacher_photo()).apply(requestOptions).into(viewHolder.photo);
        viewHolder.activityName.setText(list.get(position).getActivityName());
        viewHolder.time.setText(list.get(position).getStart_date());

    }

    @Override
    public int getItemCount() {
        if(list.size()<=5){
            return list.size();
        }else {
            return 5;
        }

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
