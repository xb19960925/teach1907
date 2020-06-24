package com.teach.teach1907.fragment.course;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.teach.data.CourseBean;
import com.teach.data.CourseDetailInfo;
import com.teach.data.LessonComment;
import com.teach.frame.ICommonModel;
import com.teach.frame.constants.ConstantKey;
import com.teach.teach1907.R;
import com.teach.teach1907.adapter.CourseDescribeAdapter;
import com.teach.teach1907.adapter.CourseDescribesAdapter;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.interfaces.DataListener;

import java.util.List;

import butterknife.BindView;

public class EvaluateFragment extends BaseMvpFragment implements DataListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String discount;
    private CourseBean.ResultBean.ListsBean itemInfo;
    CourseDetailInfo courseInfo;
    private CourseDetailFragment parentFragment;
    public static EvaluateFragment getInstance(CourseDetailInfo courseInfo, CourseBean.ResultBean.ListsBean itemInfo, String title) {
        EvaluateFragment fragment = new EvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantKey.COURSE_COMMENT_DETAIL, courseInfo);
        bundle.putSerializable(ConstantKey.COURSE_COMMENT_ITEM, itemInfo);
        bundle.putString(ConstantKey.COURSE_LIST_HOMELIVE_ID, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            courseInfo= getArguments().getParcelable(ConstantKey.COURSE_COMMENT_DETAIL);
            itemInfo= getArguments().getParcelable(ConstantKey.COURSE_COMMENT_ITEM);
            discount= getArguments().getParcelable(ConstantKey.COURSE_LIST_HOMELIVE_ID);
        }
    }

    @Override
    public ICommonModel setModel() {
        return null;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_evaluate;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (parentFragment != null && parentFragment.nullLoadLayout != null) {
            if (isVisibleToUser) {
                parentFragment.nullLoadLayout.setEnableLoadMore(true);
                parentFragment.nullLoadLayout.setEnableRefresh(true);
            }
        }
    }
    @Override
    public void initView() {
        List<LessonComment> commentList = courseInfo.getComment_list();
        initRecyclerView(recyclerView,refreshLayout,this);
        recyclerView.setAdapter(new CourseDescribesAdapter(getContext(),commentList,courseInfo));
    }

    @Override
    public void initData() {

    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }


    @Override
    public void dataType(int mode) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }
}