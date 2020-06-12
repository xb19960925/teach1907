package com.teach.teach1907.activity;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.teach.data.SpecialtyChooseEntity;
import com.teach.frame.ICommonView;
import com.teach.teach1907.R;
import com.teach.teach1907.base.BaseMvpActivity;
import com.teach.teach1907.model.CommonHomeModel;

public class HomeActivity extends BaseMvpActivity<CommonHomeModel> implements NavController.OnDestinationChangedListener {

    public NavController mProjectController;

    @Override
    public CommonHomeModel setModel() {
        return new CommonHomeModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void setUpData() {
        mProjectController = Navigation.findNavController(this, R.id.project_fragment_control);
        mProjectController.addOnDestinationChangedListener(this);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }


    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        String label = destination.getLabel().toString();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}