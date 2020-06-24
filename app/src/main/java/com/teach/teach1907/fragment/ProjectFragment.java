package com.teach.teach1907.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.teach.teach1907.activity.HomeActivity;
import com.teach.teach1907.adapter.CourseAdapter;
import com.teach.teach1907.base.Application1907;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.interfaces.DataListener;
import com.teach.teach1907.interfaces.OnRecyclerItemClick;
import com.teach.teach1907.model.FragmentModel;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class ProjectFragment extends BaseMvpFragment<FragmentModel> implements DataListener {
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


    public static ProjectFragment getInstance(int index) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("whichFragment", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourseType = (int) getArguments().get("whichFragment");
        }
    }

    @Override
    public FragmentModel setModel() {
        return new FragmentModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity a = (HomeActivity) getActivity();
        if (a.isBackFromDetail) dataType(LoadTypeConfig.REFRESH);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_project;
    }

    @Override
    public void initView() {
        initRecyclerView(recyclerView, sml, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        mAdapter = new CourseAdapter(Application1907.get07ApplicationContext(), mList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerItemClick((position, pObjects) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("itemInfo", mList.get(position));
            bundle.putString("record", "0");
            bundle.putString("f", "1004");
            // NavHostFragment.findNavController(this).navigate(R.id.home_to_course_detail,bundle);
            HomeActivity activity = (HomeActivity) getActivity();
            activity.mProjectController.navigate(R.id.courseDetailFragment, bundle);
        });
    }

    @Override
    public void initData() {
        mPresenter.allowLoading(getActivity());
        mPresenter.getData(ApiConfig.GET_COURSE, LoadTypeConfig.NORMAL, page, mCourseType);
    }

    @Override
    public void dataType(int mode) {
        if (mode == LoadTypeConfig.REFRESH) {
            mPresenter.getData(ApiConfig.GET_COURSE, LoadTypeConfig.REFRESH, 1, mCourseType);
        } else {
            page++;
            mPresenter.getData(ApiConfig.GET_COURSE, LoadTypeConfig.MORE, page, mCourseType);
        }
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.GET_COURSE:
                List<CourseBean.ResultBean.ListsBean> beans = ((CourseBean) pD[0]).result.lists;
                int type = (int) ((Object[]) pD[1])[0];
                if (type == LoadTypeConfig.REFRESH) {
                    mList.clear();
                    sml.finishRefresh();
                }
                if (type == LoadTypeConfig.MORE) {
                    sml.finishLoadMore();
                }
                mList.addAll(beans);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }
}