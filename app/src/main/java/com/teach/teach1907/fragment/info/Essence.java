package com.teach.teach1907.fragment.info;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.teach.data.NewBestBean;
import com.teach.data.SpecialtyChooseEntity;
import com.teach.frame.ApiConfig;
import com.teach.frame.ICommonView;
import com.teach.frame.LoadTypeConfig;
import com.teach.frame.constants.ConstantKey;
import com.teach.frame.utils.ParamHashMap;
import com.teach.teach1907.R;
import com.teach.teach1907.adapter.EssenceAdapter;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.model.DataModel;
import com.teach.teach1907.model.FragmentModel;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Essence extends BaseMvpFragment<DataModel> implements ICommonView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout sml;
    private int page=1;
    private List<NewBestBean.ResultBean> list;
    private EssenceAdapter mAdapter;

    @Override
    public DataModel setModel() {
        return new DataModel();
    }

    @Override
    public int setLayout() {
        return R.layout.essence;
    }

    @Override
    public void initView() {

        initRecyclerView(recyclerView, sml, mode -> {
            if (mode == LoadTypeConfig.MORE) {
                page++;
                mPresenter.getData(ApiConfig.GET_NEWBEST_INFO, LoadTypeConfig.MORE, page);
            }
            if (mode == LoadTypeConfig.REFRESH) {
                page = 1;
                mPresenter.getData(ApiConfig.GET_NEWBEST_INFO, LoadTypeConfig.REFRESH, page);
            }
        });
        list = new ArrayList<>();
        mAdapter = new EssenceAdapter(getActivity(), list);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        mPresenter.getData(ApiConfig.GET_NEWBEST_INFO, LoadTypeConfig.NORMAL, page);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        int type=(int)((Object[])pD[1])[0];
        if(type==LoadTypeConfig.MORE){
            sml.finishLoadMore();
        }
        if (type == LoadTypeConfig.REFRESH) {
            list.clear();
            sml.finishRefresh();
        }
        List<NewBestBean.ResultBean> beans = ((NewBestBean) pD[0]).getResult();
        if(beans!=null)list.addAll(beans);
        mAdapter.notifyDataSetChanged();
    }
}