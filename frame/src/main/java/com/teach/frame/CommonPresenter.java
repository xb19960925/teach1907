package com.teach.frame;

import android.app.Activity;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;


public class CommonPresenter<V extends ICommonView, M extends ICommonModel> implements ICommonPresenter {
    private SoftReference<V> mView;
    private SoftReference<M> mModel;
    private List<Disposable> mDisposableList;
    private WeakReference<Activity> mActivityWeakReference;
    private LoadView mInstance;
    public CommonPresenter(V pView, M pModel) {
        mDisposableList = new ArrayList<>();
        mView = new SoftReference<>(pView);
        mModel = new SoftReference<>(pModel);
    }
    public void allowLoading(Activity pActivity) {
        mActivityWeakReference = new WeakReference<>(pActivity);
    }
    @Override
    public void onSuccess(int whichApi, Object... pD) {
        if (mView != null && mView.get() != null) mView.get().onSuccess(whichApi, pD);
        if (mInstance != null && mInstance.isShowing())mInstance.dismiss();
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        if (mView != null && mView.get() != null) mView.get().onFailed(whichApi, pThrowable);
        if (mInstance != null && mInstance.isShowing())mInstance.dismiss();
    }


    @Override
    public void getData(int whichApi, Object... pObjects) {
        if (mActivityWeakReference != null && mActivityWeakReference.get() != null && mActivityWeakReference.get() instanceof Activity) {
            Activity activity = mActivityWeakReference.get();
            if (!activity.isFinishing() && mInstance == null){
                mInstance = new LoadView(activity,null);
            }
            int load = -1;
            if (pObjects != null && pObjects.length != 0 && pObjects[0] instanceof Integer){
                load = (int) pObjects[0];
            }
            if (load != LoadTypeConfig.MORE && load != LoadTypeConfig.REFRESH && mInstance != null && !mInstance.isShowing()){
                mInstance.show();
            }
        }
        if (mModel != null && mModel.get() != null) mModel.get().getData(this, whichApi, pObjects);
    }

    @Override
    public void addObserver(Disposable pDisposable) {
        if (mDisposableList == null) return;
        mDisposableList.add(pDisposable);
    }


    public void clear() {
        for (Disposable dis:mDisposableList) {
            if (dis != null && !dis.isDisposed())dis.dispose();
        }
        if (mView != null) {
            mView.clear();
            mView = null;
        }
        if (mModel != null) {
            mModel.clear();
            mModel = null;
        }
        if (mInstance != null && mInstance.isShowing())mInstance.dismiss();
        if (mActivityWeakReference != null){
            mActivityWeakReference.clear();
            mActivityWeakReference = null;
        }
    }


}
