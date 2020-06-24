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

import java.util.HashMap;
import java.util.Map;

public class FragmentModel implements ICommonModel {
    NetManger netManger = NetManger.getInstance();

    @Override
    public void getData(ICommonPresenter pPresenter, int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.GET_COURSE:
                ParamHashMap map = new ParamHashMap().add("page", params[1]).add("course_type", params[2]).add("limit", Constants.LIMIT_NUM).add("specialty_id",  FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id());
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.edu_openapi)).getList(map), pPresenter, whichApi,(int)params[0]);
            break;

        }
    }
}
