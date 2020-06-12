package com.teach.teach1907.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teach.data.VipBean;
import com.teach.data.VipListBean;
import com.teach.teach1907.R;
import com.teach.teach1907.design.BannerLayout;

import java.util.ArrayList;


public class VipAdapter extends RecyclerView.Adapter<VipAdapter.ViewHolder> {

    private Activity mContext;
    private   ArrayList<String> strings;
    private ArrayList<VipBean.ResultBean.LiveBeanX.LiveBean> list;
    ArrayList<VipListBean.ResultBean.ListBean> mList;
    int BANNER = 0;
    int LIVE = 1;
    int LIST = 2;

    public VipAdapter(Activity pContext, ArrayList<String> pStrings, ArrayList<VipBean.ResultBean.LiveBeanX.LiveBean> pList, ArrayList<VipListBean.ResultBean.ListBean> pList1) {
        mContext = pContext;
        strings = pStrings;
        list = pList;
        mList = pList1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @LayoutRes int layoutId = R.layout.layout_vip_rec;
        if (viewType == BANNER) {
            layoutId = R.layout.item_homepage_ad;
        } else if (viewType == LIVE) {
            layoutId = R.layout.layout_vip_live;
        } else if (viewType == LIST) {
            layoutId = R.layout.layout_vip_rec;
        }
        return new ViewHolder(LayoutInflater.from(mContext).inflate(layoutId, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
        if (itemViewType == BANNER) {
            holder.banner.attachActivity(mContext);
            if (strings.size() != 0) holder.banner.setViewUrls(strings);
        }
        if (itemViewType == LIVE) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.recyclerView.setLayoutManager(linearLayoutManager);
            holder.recyclerView.setAdapter(new VipLiveAdapter(mContext, list, this));
        }
        if (itemViewType == LIST) {
            int mPosition = position - 2;
            if (mPosition % 2 == 0) {
                Glide.with(mContext).load(mList.get(mPosition).getVip_thumb()).into(holder.img1);
                holder.name1.setText(mList.get(mPosition).getLesson_name());
                holder.study1.setText(mList.get(mPosition).getStudentnum()+"人学习");
                holder.good1.setText(mList.get(mPosition).getComment_rate()+"好评");
            } else if (mPosition % 2 == 1) {
                Glide.with(mContext).load(mList.get(mPosition).getVip_thumb()).into(holder.img2);
                holder.name2.setText(mList.get(mPosition).getLesson_name());
                holder.study2.setText(mList.get(mPosition).getStudentnum()+"人学习");
                holder.good2.setText(mList.get(mPosition).getComment_rate()+"好评");
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return BANNER;
        }
        if (position == 1) {
            return LIVE;
        }
        if (position == 2) {
            return LIST;
        }
        return LIST;
    }

    @Override
    public int getItemCount() {
        return mList.size() + 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private BannerLayout banner;
        private RecyclerView recyclerView;
        private ImageView img1;
        private ImageView img2;
        private TextView name1;
        private TextView study1;
        private TextView good1;
        private TextView name2;
        private TextView study2;
        private TextView good2;

        public ViewHolder(@NonNull View itemView, int type) {
            super(itemView);
            if (type == BANNER) {
                banner = itemView.findViewById(R.id.banner_main);
            } else if (type == LIVE) {
                recyclerView = itemView.findViewById(R.id.recyclerView);
            } else if (type == LIST) {
                img1 = itemView.findViewById(R.id.img1);
                img2 = itemView.findViewById(R.id.img2);
                name1 = itemView.findViewById(R.id.name1);
                name2 = itemView.findViewById(R.id.name2);
                study1 = itemView.findViewById(R.id.study1);
                study2 = itemView.findViewById(R.id.study2);
                good1 = itemView.findViewById(R.id.good1);
                good2 = itemView.findViewById(R.id.good2);
            }
        }
    }
}
