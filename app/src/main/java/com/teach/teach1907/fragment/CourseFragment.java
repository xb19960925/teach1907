package com.teach.teach1907.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.teach.frame.ICommonView;
import com.teach.frame.constants.Constants;
import com.teach.teach1907.R;
import com.teach.teach1907.adapter.CourseVpAdapter;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.model.FragmentModel;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;


public class CourseFragment extends BaseMvpFragment<FragmentModel> implements ICommonView {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tab)
    CommonTabLayout tab;

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
        ArrayList<CustomTabEntity> data = new ArrayList<>();
        Collections.addAll(strings, "训练营", "精品课", "公开课");
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            //      course_type： 0 : 精品课 1 : 公开课 2 : 训练营
            fragments.add(new ProjectFragment(i));
            final int index = i;
            data.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return strings.get(index);
                }

                @Override
                public int getTabSelectedIcon() {
                    return 0;
                }

                @Override
                public int getTabUnselectedIcon() {
                    return 0;
                }
            });
        }
        tab.setTabData(data);
        vp.setAdapter(new CourseVpAdapter(getChildFragmentManager(), fragments));

    }

    @Override
    public void initData() {
        //TabLayout监听
        tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                //显示相应的item界面
                vp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //ViewPager监听
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //设置相应选中图标和颜色
                tab.setCurrentTab(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //设置默认第0个
        vp.setCurrentItem(0);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
    }
}