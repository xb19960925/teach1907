package com.teach.teach1907.model;

import com.teach.frame.ApiConfig;
import com.teach.frame.FrameApplication;
import com.teach.frame.ICommonModel;
import com.teach.frame.ICommonPresenter;
import com.teach.frame.NetManger;
import com.teach.frame.utils.ParamHashMap;
import com.teach.teach1907.R;
import com.teach.teach1907.base.Application1907;

public class VipModel implements ICommonModel {
    NetManger mManger = NetManger.getInstance();
    @Override
    public void getData(ICommonPresenter pPresenter, int whichApi, Object[] params) {
        if(whichApi==ApiConfig.VPI_BANNER){
            ParamHashMap map = new ParamHashMap().add("specialty_id", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id()).add("sort", "2");
            mManger.netWork(mManger.getService(Application1907.get07ApplicationContext().getString(R.string.edu_openapi)).getBanner(map),pPresenter,whichApi,params[0]);
        }if(whichApi==ApiConfig.VPI_LIST){
            ParamHashMap map = new ParamHashMap().add("specialty_id", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id()).add("sort", "2").add("page", params[1]);
            mManger.netWork(mManger.getService(Application1907.get07ApplicationContext().getString(R.string.edu_openapi)).getApiList(map),pPresenter,whichApi,params[0]);
        }







    }
}
