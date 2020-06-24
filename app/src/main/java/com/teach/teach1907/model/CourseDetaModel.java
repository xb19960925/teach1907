package com.teach.teach1907.model;

import androidx.core.app.AppLaunchChecker;

import com.google.android.material.appbar.AppBarLayout;
import com.teach.frame.ApiConfig;
import com.teach.frame.ICommonModel;
import com.teach.frame.ICommonPresenter;
import com.teach.frame.NetManger;
import com.teach.teach1907.R;
import com.teach.teach1907.base.Application1907;

import java.lang.reflect.Method;
import java.util.Map;

public class CourseDetaModel implements ICommonModel {
    NetManger netManger = NetManger.getInstance();
    @Override
    public void getData(ICommonPresenter pPresenter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.COURSE_DETAIL_INFO:
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.edu_api)).getCourseDetail( (Map<String, Object>) params[0]),pPresenter,whichApi);
                break;
        }
    }
}
