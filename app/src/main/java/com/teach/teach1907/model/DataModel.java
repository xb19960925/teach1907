package com.teach.teach1907.model;

import com.teach.frame.ApiConfig;
import com.teach.frame.FrameApplication;
import com.teach.frame.ICommonModel;
import com.teach.frame.ICommonPresenter;
import com.teach.frame.NetManger;
import com.teach.frame.utils.ParamHashMap;
import com.teach.teach1907.R;
import com.teach.teach1907.base.Application1907;

import java.lang.reflect.Method;
import java.util.Map;

public class DataModel implements ICommonModel {
    NetManger netManger = NetManger.getInstance();
    @Override
    public void getData(ICommonPresenter pPresenter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.GET_INFORMATION_INFO:
                ParamHashMap add = new ParamHashMap().add("type", 1).add("fid", FrameApplication.getFrameApplication().getSelectedInfo().getFid()).add("page", params[1]);
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.bbs_openapi)).getInformation(add),pPresenter,whichApi,(int)params[0]);
                break;
            case ApiConfig.GET_NEWBEST_INFO:
                ParamHashMap map = new ParamHashMap().add("page", params[1]).add("fid", FrameApplication.getFrameApplication().getSelectedInfo().getFid());
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.bbs_openapi)).getnewbest(map),pPresenter,whichApi,(int)params[0]);
                break;
            case ApiConfig.CLICK_CANCEL_FOCUS:
                ParamHashMap add1 = new ParamHashMap().add("group_id", params[0]).add("type", 1).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.bbs_api)).removeFocus(add1),pPresenter,whichApi,(int)params[1]);
                break;
            case ApiConfig.CLICK_TO_FOCUS:
                ParamHashMap add2 = new ParamHashMap().add("gid", params[0]).add("group_name", params[1]).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.bbs_api)).focus(add2),pPresenter,whichApi,(int)params[2]);
                break;
        }
    }
}
