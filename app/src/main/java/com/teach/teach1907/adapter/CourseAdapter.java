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
import com.teach.data.CourseBean;
import com.teach.teach1907.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<CourseBean.ResultBean.ListsBean> mList;

    public CourseAdapter(Context pContext, ArrayList<CourseBean.ResultBean.ListsBean> pList) {
        mContext = pContext;
        mList = pList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_course_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(mList.get(position).thumb).into(holder.courseThumb);
        holder.courseName.setText(mList.get(position).lesson_name);
        holder.courseNum.setText(mList.get(position).studentnum+"人学习");
        holder.courseRate.setText("好评率"+mList.get(position).rate);
        holder.price.setText("￥"+mList.get(position).price);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.course_thumb)
        ImageView courseThumb;
        @BindView(R.id.course_name)
        TextView courseName;
        @BindView(R.id.course_num)
        TextView courseNum;
        @BindView(R.id.course_rate)
        TextView courseRate;
        @BindView(R.id.price)
        TextView price;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
