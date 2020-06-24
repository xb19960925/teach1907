package com.teach.teach1907.fragment.course;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.teach.data.CourseDetailInfo;
import com.teach.frame.constants.ConstantKey;
import com.teach.teach1907.R;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.design.RichWebView;
import com.teach.teach1907.model.CourseDetaModel;

import butterknife.BindView;


public class ParticularsFragment extends BaseMvpFragment<CourseDetaModel> {
    @BindView(R.id.webView)
    RichWebView webView;
    @BindView(R.id.webRefreshLayout)
    SmartRefreshLayout webRefreshLayout;
    private CourseDetailInfo mCourseDetailInfo;

    public static ParticularsFragment getInstance(CourseDetailInfo courseInfo) {
        ParticularsFragment fragment = new ParticularsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantKey.COURSE_DESCRIBE, courseInfo);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourseDetailInfo = getArguments().getParcelable(ConstantKey.COURSE_DESCRIBE);
        }
    }
    @Override
    public CourseDetaModel setModel() {
        return null;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_particulars;
    }

    @Override
    public void initView() {
        webRefreshLayout.setEnableLoadMore(false);
        CourseDetailFragment parentFragment = (CourseDetailFragment) getParentFragment();
        webRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh();
            parentFragment.viewPager.setCurrentItem(0);
        });
        if (!TextUtils.isEmpty(mCourseDetailInfo.getZt_url())){
            webView.loadUrl(mCourseDetailInfo.getZt_url());
        } else {
            webView.loadDataWithBaseURL("about:blank",mCourseDetailInfo.getInfo(),"text/html", "UTF-8", "about:blank");
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }


}