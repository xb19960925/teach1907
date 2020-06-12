package com.teach.teach1907.model;

import com.teach.frame.ApiConfig;
import com.teach.frame.FrameApplication;
import com.teach.frame.ICommonModel;
import com.teach.frame.ICommonPresenter;
import com.teach.frame.NetManger;
import com.teach.frame.constants.Constants;
import com.teach.frame.utils.ParamHashMap;
import com.teach.teach1907.R;
import com.teach.teach1907.base.Application1907;

import java.util.Map;

public class MainModel implements ICommonModel {
    NetManger netManger = NetManger.getInstance();
    @Override
    public void getData(ICommonPresenter pPresenter, int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.MAIN_BANNER:

                ParamHashMap live = new ParamHashMap().add("pro", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id()).add("more_live","1").add("is_new",1).add("new_banner",1);
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.edu_url)).getBannerLive(live), pPresenter, whichApi, (int) params[0]);
                break;
            case ApiConfig.MAIN_LIST:
                ParamHashMap add = new ParamHashMap().add("specialty_id", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id()).add("page", params[1]).add("limit", Constants.LIMIT_NUM).add("new_banner", 1);
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.edu_openapi)).getMainList(add), pPresenter, whichApi, (int) params[0]);
                break;
        }
    }
}
