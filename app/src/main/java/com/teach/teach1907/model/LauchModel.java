package com.teach.teach1907.model;

import android.text.TextUtils;

import com.teach.frame.ApiConfig;
import com.teach.frame.ICommonModel;
import com.teach.frame.ICommonPresenter;
import com.teach.frame.NetManger;
import com.teach.frame.utils.ParamHashMap;
import com.teach.teach1907.R;
import com.teach.teach1907.base.Application1907;

public class LauchModel implements ICommonModel {
    private NetManger mManger = NetManger.getInstance();
    @Override
    public void getData(ICommonPresenter pPresenter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.ADVERT:
                ParamHashMap map = new ParamHashMap().add("w",params[1]).add("h",params[2]).add("positions_id", "APP_QD_01").add("is_show", 0);
                if (!TextUtils.isEmpty((String)params[0]))map.add("specialty_id",params[0]);
                mManger.netWork(mManger.getService(Application1907.get07ApplicationContext().getString(R.string.ad_openapi)).getAdvert(map),pPresenter,whichApi);
                break;
            case ApiConfig.SUBJECT:
                mManger.netWork(mManger.getService(Application1907.get07ApplicationContext().getString(R.string.edu_openapi)).getSubjectList(), pPresenter, whichApi);
                break;
        }
    }
}
