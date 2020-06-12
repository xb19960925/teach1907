package com.teach.teach1907.fragment;

import android.graphics.Color;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.TextView;

import com.teach.frame.ICommonView;
import com.teach.teach1907.R;
import com.teach.teach1907.adapter.DataAdapter;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.fragment.info.Datum;
import com.teach.teach1907.fragment.info.Essence;
import com.teach.teach1907.model.FragmentModel;

import java.util.ArrayList;

import butterknife.BindView;

public class DataFragment extends BaseMvpFragment<FragmentModel> implements ICommonView {

    @BindView(R.id.tv_zl)
    TextView tvZl;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    public FragmentModel setModel() {
        return new FragmentModel();
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_data;
    }

    @Override
    public void initView() {
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new Datum());
        list.add(new Essence());
        DataAdapter adapter = new DataAdapter(getChildFragmentManager(),list);
        viewPager.setAdapter(adapter);
        tvZl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        tvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tvZl.setEnabled(false);
                    tvNew.setEnabled(true);
                    tvZl.setTextColor(Color.BLACK);
                    tvNew.setTextColor(Color.GRAY);
                } else {
                    tvZl.setEnabled(true);
                    tvNew.setEnabled(false);
                    tvNew.setTextColor(Color.BLACK);
                    tvZl.setTextColor(Color.GRAY);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }
}