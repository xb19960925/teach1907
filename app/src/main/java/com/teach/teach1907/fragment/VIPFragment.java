package com.teach.teach1907.fragment;

import android.widget.Adapter;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.teach.data.VipBean;
import com.teach.data.VipListBean;
import com.teach.frame.ApiConfig;
import com.teach.frame.ICommonView;
import com.teach.frame.LoadTypeConfig;
import com.teach.teach1907.R;
import com.teach.teach1907.adapter.VipAdapter;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.model.FragmentModel;
import com.teach.teach1907.model.VipModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VIPFragment extends BaseMvpFragment<VipModel> implements ICommonView {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private ArrayList<VipBean.ResultBean.LiveBeanX.LiveBean> lists= new ArrayList<>();
    private ArrayList<VipBean.ResultBean.LunbotuBean> banners=new ArrayList<>();
    private ArrayList<VipListBean.ResultBean.ListBean> mList=new ArrayList<>();
    ArrayList<String> strings = new ArrayList<>();
    private VipAdapter mAdapter;
    int page=1;
    private boolean mainList = false, banLive = false;
    @Override
    public VipModel setModel() {
        return new VipModel();
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_v_i_p;
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new VipAdapter(getActivity(),strings,lists,mList);
        recyclerView.setAdapter(mAdapter);
        mainList = false;
        banLive = false;
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getData(ApiConfig.VPI_LIST, LoadTypeConfig.MORE,page);
                mPresenter.getData(ApiConfig.VPI_BANNER, LoadTypeConfig.MORE);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mPresenter.getData(ApiConfig.VPI_LIST, LoadTypeConfig.REFRESH,page);
            }
        });
    }

    @Override
    public void initData() {
      mPresenter.getData(ApiConfig.VPI_BANNER, LoadTypeConfig.NORMAL);
        mPresenter.getData(ApiConfig.VPI_LIST, LoadTypeConfig.NORMAL,page);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.VPI_BANNER:
              strings.clear();lists.clear();mSmartRefreshLayout.finishRefresh();
                List<VipBean.ResultBean.LiveBeanX.LiveBean> beans=((VipBean)pD[0]).getResult().getLive().getLive();
                List<VipBean.ResultBean.LunbotuBean> banners=((VipBean)pD[0]).getResult().getLunbotu();
                for (int i = 0; i <banners.size() ; i++) {
                    strings.add(banners.get(i).getImg());
                }
                lists.addAll(beans);
                mainList = true;
                if (banLive) {
                    mAdapter.notifyDataSetChanged();
                    mainList = false;
                }
                break;
            case ApiConfig.VPI_LIST:
               int type=(int)((Object[])pD[1])[0];
               if(type==LoadTypeConfig.MORE)mSmartRefreshLayout.finishLoadMore();
               if(type==LoadTypeConfig.REFRESH)mList.clear();mSmartRefreshLayout.finishRefresh();
                List<VipListBean.ResultBean.ListBean> fos=((VipListBean)pD[0]).getResult().getList();
                mList.addAll(fos);
                banLive = true;
                if (mainList) {
                    mAdapter.notifyDataSetChanged();
                    banLive = false;
                }
                break;
        }

        mAdapter.notifyDataSetChanged();

    }
}