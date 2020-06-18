package com.teach.teach1907.model;

import android.content.Context;

import com.teach.data.ThirdLoginData;
import com.teach.frame.ApiConfig;
import com.teach.frame.FrameApplication;
import com.teach.frame.ICommonModel;
import com.teach.frame.ICommonPresenter;
import com.teach.frame.NetManger;
import com.teach.frame.constants.ConstantKey;
import com.teach.frame.secret.RsaUtil;
import com.teach.frame.utils.ParamHashMap;
import com.teach.teach1907.R;
import com.teach.teach1907.base.Application1907;

import java.lang.reflect.Method;

public  class AccountModel implements ICommonModel {
    private NetManger mManger = NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();

    @Override
    public void getData(ICommonPresenter pPresenter, int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.SEND_VERIFY:
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_openapi_user)).getLoginVerify((String) params[0]), pPresenter, whichApi);
                break;
            case ApiConfig.VERIFY_LOGIN:
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_openapi_user)).loginByVerify(new ParamHashMap().add("mobile",params[0]).add("code",params[1])),pPresenter,whichApi);
                break;
            case ApiConfig.GET_HEADER_INFO:
                String uid = FrameApplication.getFrameApplication().getLoginInfo().getUid();
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_api)).getHeaderInfo(new ParamHashMap().add("zuid",uid).add("uid",uid)),pPresenter,whichApi);
                break;
            case ApiConfig.ACCOUNT_LOGIN:
                ParamHashMap add = new ParamHashMap().add("ZLSessionID", "").add("seccode", "").add("loginName", params[0])
                        .add("passwd", RsaUtil.encryptByPublic((String) params[1])).add("cookieday", "")
                        .add("fromUrl", "android").add("ignoreMobile", "0");
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_openapi)).AccountLogin(add),pPresenter,whichApi);
                break;
            case ApiConfig.GET_WE_CHAT_TOKEN:
                ParamHashMap wxParams = new ParamHashMap().add("appid", ConstantKey.WX_APP_ID).add("secret", ConstantKey.WX_APP_SECRET).add("code", params[0]).add("grant_type", "authorization_code");
                mManger.netWork(mManger.getService(mContext.getString(R.string.wx_oauth)).getWechatToken(wxParams),pPresenter,whichApi);
             break;
            case ApiConfig.POST_WE_CHAT_LOGIN_INFO:
                ThirdLoginData data = (ThirdLoginData) params[0];
                ParamHashMap mMap= new ParamHashMap().add("openid", data.openid).add("type", data.type).add("url", "android");
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_api)).loginByWechat(mMap),pPresenter,whichApi);
                break;
            case ApiConfig.BIND_ACCOUNT:
                String account = (String) params[0];
                String password = (String) params[1];
                ThirdLoginData thirdLoginData = (ThirdLoginData) params[2];
                ParamHashMap Param = new ParamHashMap().add("username", account).add("password", RsaUtil.encryptByPublic(password))
                        .add("openid", thirdLoginData.openid).add("t_token", thirdLoginData.token)
                        .add("utime", thirdLoginData.utime).add("type", thirdLoginData.type)
                        .add("url", "android").add("state", 1);
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_api)).bindThirdAccount(Param),pPresenter,whichApi);
                break;
        }
    }
}
