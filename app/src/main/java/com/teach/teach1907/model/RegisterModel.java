package com.teach.teach1907.model;

import com.teach.frame.ApiConfig;
import com.teach.frame.FrameApplication;
import com.teach.frame.ICommonModel;
import com.teach.frame.ICommonPresenter;
import com.teach.frame.ICommonView;
import com.teach.frame.NetManger;
import com.teach.frame.secret.RsaUtil;
import com.teach.frame.utils.ParamHashMap;
import com.teach.teach1907.R;
import com.teach.teach1907.base.Application1907;

import java.lang.reflect.Method;

public class RegisterModel implements ICommonModel {
    NetManger netManger = NetManger.getInstance();
    @Override
    public void getData(ICommonPresenter pPresenter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.CHECK_PHONE_IS_USED:
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.passport_api)).checkPhoneIsUsed(params[0]),pPresenter,whichApi);
                break;
            case ApiConfig.REGISTER_PHONE:
                ParamHashMap add = new ParamHashMap().add("mobile", params[0]).add("code", params[1]);
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.passport_api)).checkVerifyCode(add),pPresenter,whichApi);
                break;
            case ApiConfig.SEND_REGISTER_VERIFY:
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.passport_api)).sendRegisterVerify(params[0]),pPresenter,whichApi);
                break;
            case ApiConfig.NET_CHECK_USERNAME:
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.passport)).checkName(params[0]),pPresenter,whichApi);
                break;
            case ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT:
                ParamHashMap param = new ParamHashMap().add("username", params[0]).add("password", RsaUtil.encryptByPublic((String) params[1]))
                        .add("tel", params[2]).add("specialty_id", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id())
                        .add("province_id", 0).add("city_id", 0).add("sex", 0).add("from_reg_name", 0).add("from_reg", 0);
                netManger.netWork(netManger.getService(Application1907.get07ApplicationContext().getString(R.string.passport_api)).registerCompleteWithSubject(param),pPresenter,whichApi);
                break;
        }
    }
}
