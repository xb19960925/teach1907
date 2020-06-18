package com.teach.teach1907.fragment.info;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.teach.data.BaseInfo;
import com.teach.data.InformationBean;
import com.teach.data.NewBestBean;
import com.teach.data.SpecialtyChooseEntity;
import com.teach.frame.ApiConfig;
import com.teach.frame.FrameApplication;
import com.teach.frame.ICommonView;
import com.teach.frame.LoadTypeConfig;
import com.teach.frame.constants.ConstantKey;
import com.teach.frame.utils.ParamHashMap;
import com.teach.teach1907.R;
import com.teach.teach1907.activity.HomeActivity;
import com.teach.teach1907.activity.LoginActivity;
import com.teach.teach1907.adapter.DatumAdapter;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.interfaces.OnRecyclerItemClick;
import com.teach.teach1907.model.DataModel;
import com.teach.teach1907.model.FragmentModel;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.teach.teach1907.adapter.DatumAdapter.FOCUS_TYPE;
import static com.teach.teach1907.adapter.DatumAdapter.ITEM_TYPE;
import static com.teach.teach1907.design.JumpConstant.DATAGROUPFRAGMENT_TO_LOGIN;
import static com.teach.teach1907.design.JumpConstant.JUMP_KEY;


public class Datum extends BaseMvpFragment<DataModel> implements ICommonView, OnRecyclerItemClick {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout sml;
    private int page = 1;
    private List<InformationBean.ResultBean> list;
    private DatumAdapter mAdapter;

    @Override
    public DataModel setModel() {
        return new DataModel();
    }

    @Override
    public int setLayout() {
        return R.layout.datum;
    }

    @Override
    public void initView() {

        initRecyclerView(recyclerView, sml, mode -> {
            if (mode == LoadTypeConfig.MORE) {
                page++;
                mPresenter.getData(ApiConfig.GET_INFORMATION_INFO, LoadTypeConfig.MORE,page);
            }
            if (mode == LoadTypeConfig.REFRESH) {
               page = 1;
                mPresenter.getData(ApiConfig.GET_INFORMATION_INFO, LoadTypeConfig.REFRESH,page);
            }
        });
        list = new ArrayList<>();
        mAdapter = new DatumAdapter(getActivity(), list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerItemClick(this);
    }

    @Override
    public void initData() {
        mPresenter.getData(ApiConfig.GET_INFORMATION_INFO, LoadTypeConfig.NORMAL,page);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.GET_INFORMATION_INFO:
        int type=(int)((Object[])pD[1])[0];
        if(type==LoadTypeConfig.MORE){
            sml.finishLoadMore();
        }
        if (type == LoadTypeConfig.REFRESH) {
            list.clear();
            sml.finishRefresh();
        }
        List<InformationBean.ResultBean> info = ((InformationBean) pD[0]).getResult();
        if(info!=null) list.addAll(info);
        mAdapter.notifyDataSetChanged();

        break;
            case ApiConfig.CLICK_CANCEL_FOCUS:
                BaseInfo base = (BaseInfo) pD[0];
                int clickPos = (int)((Object[])pD[1])[0];
                if (base.isSuccess()){
                    showToast("取消成功");
                    list.get(clickPos).setIs_ftop(0);
                    mAdapter.notifyItemChanged(clickPos);
                }
                break;
            case ApiConfig.CLICK_TO_FOCUS:
                BaseInfo bases = ( BaseInfo) pD[0];
                int clickJoinPos = (int)((Object[])pD[1])[0];
                if (bases.isSuccess()){
                    showToast("关注成功");
                    list.get(clickJoinPos).setIs_ftop(1);
                    mAdapter.notifyItemChanged(clickJoinPos);
                }
                break;
        }
    }

    @Override
    public void onItemClick(int position, Object[] pObjects) {
        if (pObjects != null && pObjects.length != 0){
            switch ((int)pObjects[0]){
                case ITEM_TYPE:
                    HomeActivity activity = (HomeActivity) getActivity();
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstantKey.DATUM_TO_DETAIL_GID,list.get(position).getGid());
                    activity.mProjectController.navigate(R.id.dataGroupDetailFragment,bundle);
                    break;
                case FOCUS_TYPE:
                   boolean login = FrameApplication.getFrameApplication().isLogin();
                    if(login) {
                        InformationBean.ResultBean bean = list.get(position);
                        if (bean.isFocus()) {//已经关注，取消关注
                            mPresenter.getData(ApiConfig.CLICK_CANCEL_FOCUS, bean.getGid(), position);//绿码
                        } else {//没有关注，点击关注
                            mPresenter.getData(ApiConfig.CLICK_TO_FOCUS, bean.getGid(), bean.getGroup_name(), position);
                        }
                    }else {
                            startActivity(new Intent(getContext(), LoginActivity.class).putExtra(JUMP_KEY,DATAGROUPFRAGMENT_TO_LOGIN));

                        }

                        break;
                    }
            }
        }
    }
