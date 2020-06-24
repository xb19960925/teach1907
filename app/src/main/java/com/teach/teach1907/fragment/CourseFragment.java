package com.teach.teach1907.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.teach.frame.ICommonView;
import com.teach.teach1907.R;
import com.teach.teach1907.adapter.CourseVpAdapter;
import com.teach.teach1907.adapter.MyFragmentAdapter;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.model.FragmentModel;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;


public class CourseFragment extends BaseMvpFragment<FragmentModel> implements ICommonView {


    @BindView(R.id.slide_layout)
    SlidingTabLayout slideLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    public static final int TRAINTAB = 3;
    public static final int BESTTAB = 1;
    public static final int PUBLICTAB = 2;
    private MyFragmentAdapter mMyFragmentAdapter;

    @Override
    public FragmentModel setModel() {
        return new FragmentModel();
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_course;
    }

    @Override
    public void initView() {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        Collections.addAll(strings, "训练营", "精品课", "公开课");
        Collections.addAll(fragments,ProjectFragment.getInstance(TRAINTAB),ProjectFragment.getInstance(BESTTAB),ProjectFragment.getInstance(PUBLICTAB));
        mMyFragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(), fragments, strings);
        viewPager.setAdapter(mMyFragmentAdapter);
        slideLayout.setViewPager(viewPager);
    }

    @Override
    public void initData() {

    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
    }
}