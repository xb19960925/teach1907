package com.teach.teach1907.view.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teach.data.BaseInfo;
import com.teach.data.SpecialtyChooseEntity;
import com.teach.frame.ApiConfig;
import com.teach.frame.constants.ConstantKey;
import com.teach.teach1907.R;
import com.teach.teach1907.adapter.SubjectAdapter;
import com.teach.teach1907.base.BaseMvpActivity;
import com.teach.teach1907.model.LauchModel;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubjectActivity extends BaseMvpActivity<LauchModel> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.common_title)
    TextView commonTitle;
    private List<SpecialtyChooseEntity> mListData = new ArrayList<>();
    private SubjectAdapter adapter;

    @Override
    public LauchModel setModel() {
        return new LauchModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_subject;
    }

    @Override
    public void setUpView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        commonTitle.setText(getString(R.string.select_subject));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubjectAdapter(this, mListData);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setUpData() {
        if (SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST) != null) {
            mListData.addAll(SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST));
            adapter.notifyDataSetChanged();
        } else
            mPresenter.getData(ApiConfig.SUBJECT);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.SUBJECT:
                BaseInfo<List<SpecialtyChooseEntity>> info = (BaseInfo<List<SpecialtyChooseEntity>>) pD[0];
                mListData.addAll(info.result);
                adapter.notifyDataSetChanged();
                SharedPrefrenceUtils.putSerializableList(this,ConstantKey.SUBJECT_LIST,mListData);
                break;
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPrefrenceUtils.putObject(this,ConstantKey.SUBJECT_SELECT,mApplication.getSelectedInfo());
    }
    @OnClick(R.id.common_img)
    public void onViewClicked() {
        finish();
    }
}