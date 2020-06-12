package com.teach.teach1907.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.teach.data.CourseBean;
import com.teach.data.SpecialtyChooseEntity;
import com.teach.frame.ApiConfig;
import com.teach.frame.FrameApplication;
import com.teach.frame.ICommonView;
import com.teach.frame.LoadTypeConfig;
import com.teach.frame.constants.ConstantKey;
import com.teach.frame.utils.ParamHashMap;
import com.teach.teach1907.R;
import com.teach.teach1907.adapter.CourseAdapter;
import com.teach.teach1907.base.Application1907;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.model.FragmentModel;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class ProjectFragment extends BaseMvpFragment<FragmentModel> implements ICommonView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout sml;
    @BindView(R.id.video)
    ImageView video;
    @BindView(R.id.period)
    TextView period;
    private int mCourseType;
    private int page = 1;
    private ArrayList<CourseBean.ResultBean.ListsBean> mList = new ArrayList<>();
    private CourseAdapter mAdapter;


    public ProjectFragment(int type) {
        mCourseType = type;
    }
   /* public static ProjectFragment getInstance(int index) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("whichFragment", index);
        fragment.setArguments(bundle);
        return fragment;
    }*/

    @Override
    public FragmentModel setModel() {
        return new FragmentModel();
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_project;
    }

    @Override
    public void initView() {

        initRecyclerView(recyclerView, sml, mode -> {
            if (mode == LoadTypeConfig.REFRESH) {
                page = 1;
                mPresenter.getData(ApiConfig.GET_COURSE, LoadTypeConfig.REFRESH,page,mCourseType);
            } else {
                page++;
                mPresenter.getData(ApiConfig.GET_COURSE, LoadTypeConfig.MORE, page,mCourseType);
            }
        });
        mAdapter = new CourseAdapter(Application1907.get07ApplicationContext(), mList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        mPresenter.getData(ApiConfig.GET_COURSE, LoadTypeConfig.NORMAL, page,mCourseType);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        sml.finishLoadMore();
        sml.finishRefresh();
        List<CourseBean.ResultBean.ListsBean> beans = ((CourseBean) pD[0]).result.lists;
        if (beans == null) {
            period.setVisibility(View.VISIBLE);
            period.setText("当前没公开课");
            video.setVisibility(View.VISIBLE);
        }
        mList.addAll(beans);
        mAdapter.notifyDataSetChanged();

    }
}