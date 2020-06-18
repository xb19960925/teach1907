package com.teach.teach1907.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teach.data.InformationBean;
import com.teach.teach1907.R;
import com.teach.teach1907.design.RoundImage;
import com.teach.teach1907.design.RoundOrCircleImage;
import com.teach.teach1907.interfaces.OnRecyclerItemClick;
import com.yiyatech.utils.newAdd.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DatumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<InformationBean.ResultBean> list;
    public static final int ITEM_TYPE=1,FOCUS_TYPE=2;
    public DatumAdapter(Context pContext, List<InformationBean.ResultBean> pList) {
        mContext = pContext;
        list = pList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.datum_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InformationBean.ResultBean bean = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.title.setText(bean.getGroup_name());
        //Glide.with(mContext).load(bean.getAvatar()).into(viewHolder.img);
        //GlideUtil.loadCornerImage(holder.ivThumb,entity.getAvatar(),null,6f);
        viewHolder.img.setType(1);
        ((ViewHolder) holder).img.setBorderRadius(10);
        GlideUtil.loadImage(viewHolder.img,bean.getAvatar());
        viewHolder.duce.setText(bean.getIntroduce());
        viewHolder.infoGz.setText(bean.getMember_num() + "关注");
        viewHolder.tvFocus.setText(bean.isFocus() ? "已关注" : "+关注");
        viewHolder.tvFocus.setSelected(bean.isFocus() ? true : false);
        viewHolder.tvFocus.setTextColor(bean.isFocus() ? ContextCompat.getColor(mContext,R.color.red) : ContextCompat.getColor(mContext,R.color.fontColorGray));
         viewHolder.tvFocus.setOnClickListener(view->{
            if (mOnRecyclerItemClick !=null)mOnRecyclerItemClick.onItemClick(position,FOCUS_TYPE);
        });
        holder.itemView.setOnClickListener(view->{
            if(mOnRecyclerItemClick != null)mOnRecyclerItemClick.onItemClick(position, ITEM_TYPE);
        });
    }
    private OnRecyclerItemClick mOnRecyclerItemClick;

    public void setOnRecyclerItemClick(OnRecyclerItemClick pOnRecyclerItemClick){
        mOnRecyclerItemClick = pOnRecyclerItemClick;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.infor_image)
        RoundOrCircleImage img;
        @BindView(R.id.infor_title)
        TextView title;
        @BindView(R.id.inf0_imag_tb)
        ImageView Tb;
        @BindView(R.id.info_gz)
        TextView infoGz;
        @BindView(R.id.infor_introduce)
        TextView duce;
        @BindView(R.id.tv_focus)
        TextView tvFocus;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
