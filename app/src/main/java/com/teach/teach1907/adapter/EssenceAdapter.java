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
import com.teach.data.NewBestBean;
import com.teach.teach1907.R;
import com.yiyatech.utils.newAdd.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EssenceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NewBestBean.ResultBean> list;
    private String n = "";
    private String y = "";
    private String r = "";

    public EssenceAdapter(Context pContext, List<NewBestBean.ResultBean> pList) {
        mContext = pContext;
        list = pList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.essence_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewBestBean.ResultBean bean = list.get(position);
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.title.setText(bean.getTitle());
        Glide.with(mContext).load(bean.getPic()).into(viewHolder.Pic);
        viewHolder.ViewNum.setText(bean.getTid()+"人");
        viewHolder.num.setText(bean.getView_num()+"跟帖");
        if (!TextUtils.isEmpty(list.get(position).getCreate_time()))viewHolder.id.setText(TimeUtil.formatLiveTime(Long.parseLong(list.get(position).getCreate_time())));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_view_num)
        TextView ViewNum;
        @BindView(R.id.tv_reply_num)
        TextView num;
        @BindView(R.id.img_pic)
        ImageView Pic;
        @BindView(R.id.tv_gid)
        TextView id;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
