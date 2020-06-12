package com.teach.teach1907.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.teach.data.BaseInfo;
import com.teach.data.LoginInfo;
import com.teach.data.PersonHeader;
import com.teach.frame.ApiConfig;
import com.teach.frame.ICommonModel;
import com.teach.frame.constants.ConstantKey;
import com.teach.teach1907.R;
import com.teach.teach1907.base.BaseMvpActivity;
import com.teach.teach1907.model.AccountModel;
import com.teach.teach1907.view.LoginView;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.teach.teach1907.design.JumpConstant.JUMP_KEY;
import static com.teach.teach1907.design.JumpConstant.REGISTER_TO_LOGIN;
import static com.teach.teach1907.design.JumpConstant.SPLASH_TO_LOGIN;
import static com.teach.teach1907.design.JumpConstant.SUB_TO_LOGIN;

public class LoginActivity extends BaseMvpActivity implements LoginView.LoginViewCallBack {

    @BindView(R.id.login_view)
    LoginView mLoginView;
    private Disposable mSubscribe;
    private String phoneNum;
    private String mFromType;
    @Override
    public ICommonModel setModel() {
        return new AccountModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void setUpView() {
        mLoginView.setLoginViewCallBack(this);
    }

    @Override
    public void setUpData() {
        mFromType = getIntent().getStringExtra(JUMP_KEY);
        mLoginView.setLoginViewCallBack(this);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.SEND_VERIFY:
                BaseInfo<String> info = (BaseInfo<String>) pD[0];
                if (info.isSuccess()){
                    showToast(info.result);
                    goTime();
                } else showToast("验证码发送太频繁，请稍后重试");
                break;
            case ApiConfig.VERIFY_LOGIN:
            case ApiConfig.ACCOUNT_LOGIN:
                BaseInfo<LoginInfo> baseInfo = (BaseInfo<LoginInfo>) pD[0];
                if(baseInfo.isSuccess()) {
                    LoginInfo loginInfo = baseInfo.result;
                    if (!TextUtils.isEmpty(phoneNum)) loginInfo.login_name = phoneNum;
                    mApplication.setLoginInfo(loginInfo);
                    mPresenter.getData(ApiConfig.GET_HEADER_INFO);
                }else {
                    showToast("账号密码错误");
                }
                break;
            case ApiConfig.GET_HEADER_INFO:
                PersonHeader personHeader = ((BaseInfo<PersonHeader>) pD[0]).result;
                mApplication.getLoginInfo().personHeader = personHeader;
                SharedPrefrenceUtils.putObject(this, ConstantKey.LOGIN_INFO, mApplication.getLoginInfo());
                jump();
                break;
        }
    }

    private long time = 60l;

    private void goTime() {
        mSubscribe = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(goTime -> {
            mLoginView.getVerifyCode.setText(time - goTime + "s");
            if (time - goTime < 1) doPre();  if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
        });
    }

    private void doPre() {

        mLoginView.getVerifyCode.setText("获取验证码");
    }

    @Override
    public void sendVerifyCode(String phoneNum) {
        this.phoneNum = phoneNum;
        //验证码
        mPresenter.getData(ApiConfig.SEND_VERIFY, phoneNum);
    }

    @Override
    public void loginPress(int type, String userName, String pwd) {
        doPre();
        //手机号登录
        if (mLoginView.mCurrentLoginType == mLoginView.VERIFY_TYPE)
            mPresenter.getData(ApiConfig.VERIFY_LOGIN, userName, pwd);
        //账号登录
        if(mLoginView.mCurrentLoginType==mLoginView.ACCOUNT_TYPE)
            mPresenter.getData(ApiConfig.ACCOUNT_LOGIN, userName, pwd);;
    }

    private void jump() {
        if (mFromType.equals(SPLASH_TO_LOGIN) || mFromType.equals(SUB_TO_LOGIN))
            startActivity(new Intent(this,HomeActivity.class));
        finish();
    }

    @OnClick({R.id.close_login, R.id.register_press, R.id.forgot_pwd, R.id.login_by_qq, R.id.login_by_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_login:
                if (!TextUtils.isEmpty(mFromType)&&(mFromType.equals(SUB_TO_LOGIN) || mFromType.equals(SPLASH_TO_LOGIN)||mFromType.equals(REGISTER_TO_LOGIN))){
                    startActivity(new Intent(this,HomeActivity.class));
                }
                finish();
                break;
            case R.id.register_press:
                //跳转到注册页面
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.forgot_pwd:
                break;
            case R.id.login_by_qq:
                break;
            case R.id.login_by_wx:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        doPre();
    }
}