package com.teach.teach1907.fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.teach.data.BannerLiveInfo;
import com.teach.data.BaseInfo;
import com.teach.data.IndexCommondEntity;
import com.teach.frame.ApiConfig;
import com.teach.frame.ICommonView;
import com.teach.frame.LoadTypeConfig;
import com.teach.teach1907.R;
import com.teach.teach1907.adapter.MainAdapter;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.interfaces.DataListener;
import com.teach.teach1907.model.MainModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.teach.frame.LoadTypeConfig.*;


public class MainFragment extends BaseMvpFragment<MainModel> implements DataListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout sml;
    private List<String> bannerData = new ArrayList<>();
    private List<BannerLiveInfo.Live> liveData = new ArrayList<>();
    private List<IndexCommondEntity> info = new ArrayList<>();
    private MainAdapter mAdapter;
    private int page = 0;


    @Override
    public MainModel setModel() {
        return new MainModel();
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {

        initRecyclerView(recyclerView, sml,this);
        mAdapter = new MainAdapter(info,bannerData,liveData,getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        mPresenter.allowLoading(getActivity());
        mPresenter.getData(ApiConfig.MAIN_BANNER, LoadTypeConfig.NORMAL);
        mPresenter.getData(ApiConfig.MAIN_LIST, LoadTypeConfig.NORMAL,page);
    }
    private boolean mainList = false, banLive = false;
    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.MAIN_LIST:
                int loadMode = (int) ((Object[]) pD[1])[0];
                BaseInfo<List<IndexCommondEntity>> baseInfo = (BaseInfo<List<IndexCommondEntity>>) pD[0];
                if (baseInfo.errNo==0) {
                    if (loadMode == LoadTypeConfig.MORE) sml.finishLoadMore();
                    if (loadMode == LoadTypeConfig.REFRESH) {
                        info.clear();
                        sml.finishRefresh();
                    }
                    info.addAll(baseInfo.result);
                    mainList = true;
                    if (banLive) {
                        mAdapter.notifyDataSetChanged();
                        mainList = false;
                    }
                } else showToast("列表加载错误");
                break;
            case ApiConfig.MAIN_BANNER:
                JsonObject jsonObject = (JsonObject) pD[0];
                try {
                    JSONObject object = new JSONObject(jsonObject.toString());
                    if (object.getString("errNo").equals("0")) {
                      int load = (int) ((Object[]) pD[1])[0];
                        if (load == LoadTypeConfig.REFRESH) {
                            bannerData.clear();
                            sml.finishRefresh();
                        }
                        String result = object.getString("result");
                        JSONObject resultObject = new JSONObject(result);
                        String live = resultObject.getString("live");
                        //如果该字段是以boolean类型返回的，有两种处理方式 1：写两个实体类，一个live类型是Boolean，一个是list 2：当这个字段为Boolean类型时，将其干掉
                        if (live.equals("true") || live.equals("false")) {
                            resultObject.remove("live");
                        }
                        result = resultObject.toString();
                        Gson gson = new Gson();
                        BannerLiveInfo info = gson.fromJson(result, BannerLiveInfo.class);
                        bannerData.clear();
                        for (BannerLiveInfo.Carousel data : info.Carousel) {

                            bannerData.add(data.thumb);
                        }
                        liveData.clear();
                   if(info.live!=null) liveData.addAll(info.live);
                        banLive = true;
                        if (mainList) {
                            mAdapter.notifyDataSetChanged();
                            banLive = false;
                        }
                    }
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
              break;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void dataType(int mode) {
        if (mode == LoadTypeConfig.REFRESH) {
            mainList = false;
            banLive = false;
            page=0;
            mPresenter.getData(ApiConfig.MAIN_LIST, LoadTypeConfig.REFRESH, page);
            mPresenter.getData(ApiConfig.MAIN_BANNER, LoadTypeConfig.REFRESH);
        } else {
            page++;
            mPresenter.getData(ApiConfig.MAIN_LIST, LoadTypeConfig.MORE, page);
        }
    }
}