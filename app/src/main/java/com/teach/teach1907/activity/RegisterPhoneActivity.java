package com.teach.teach1907.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.teach.data.BaseInfo;
import com.teach.frame.ApiConfig;
import com.teach.teach1907.R;
import com.teach.teach1907.base.BaseMvpActivity;
import com.teach.teach1907.design.CheckUserNameAndPwd;
import com.teach.teach1907.design.MyTextWatcher;
import com.teach.teach1907.model.RegisterModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.teach.teach1907.design.JumpConstant.JUMP_KEY;
import static com.teach.teach1907.design.JumpConstant.REGISTER_TO_LOGIN;

public class RegisterPhoneActivity extends BaseMvpActivity<RegisterModel> {


    @BindView(R.id.common_title)
    TextView common;
    @BindView(R.id.clearAccount)
    ImageView clearAccount;
    @BindView(R.id.accountContent)
    EditText accountContent;
    @BindView(R.id.visibleImage)
    ImageView visibleImage;
    @BindView(R.id.accountSecret)
    EditText accountSecret;
    @BindView(R.id.next_page)
    TextView nextPage;
    private String mPhoneNum;

    @Override
    public RegisterModel setModel() {
        return new RegisterModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_register_phone;
    }

    @Override
    public void setUpView() {
        accountContent.addTextChangedListener(new MyTextWatcher() {
            @Override
            //用户名
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                clearAccount.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                nextPage.setSelected(s.length() > 0 && accountSecret.getText().length() > 0);
            }
        });
        accountSecret.addTextChangedListener(new MyTextWatcher() {
            @Override
            //密码
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                visibleImage.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                nextPage.setSelected(s.length() > 0 && accountContent.getText().length() > 0);
            }
        });
    }

    @Override
    public void setUpData() {
        common.setText("创建账号");
        mPhoneNum = getIntent().getStringExtra("phoneNum");
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.NET_CHECK_USERNAME:
                BaseInfo baseInfo = (BaseInfo) pD[0];
                if (baseInfo.isSuccess()) {
                    mPresenter.getData(ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT, accountContent.getText().toString(), accountSecret.getText().toString(), mPhoneNum);
                } else if (baseInfo.errNo == 114) {
                    showToast("该用户名不可用");
                } else showToast(baseInfo.msg);
                break;
            case ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT:
                BaseInfo base = (BaseInfo) pD[0];
                if (base.errNo == 24 || base.errNo == 114 || base.isSuccess()) {
                    showToast("注册成功");
                    startActivity(new Intent(this, LoginActivity.class).putExtra(JUMP_KEY, REGISTER_TO_LOGIN));
                    finish();
                } else showToast(base.msg);
                break;
        }

    }

    @OnClick({R.id.common_img,R.id.clearAccount, R.id.visibleImage, R.id.next_page})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_img:
                finish();
                break;
            case R.id.clearAccount:
                accountContent.setText("");
                break;
            case R.id.visibleImage:
                //密码类型 隐藏回车
                accountSecret.setTransformationMethod(visibleImage.isSelected() ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
               //改变状态
                visibleImage.setSelected(!visibleImage.isSelected());
                break;
            case R.id.next_page:
                //检验用户名 密码
                if (CheckUserNameAndPwd.verificationUsername(this, accountContent.getText().toString(), accountSecret.getText().toString()))
                    mPresenter.getData(ApiConfig.NET_CHECK_USERNAME, accountContent.getText().toString());
                break;
        }
    }
}