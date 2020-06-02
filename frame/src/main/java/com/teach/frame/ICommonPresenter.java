package com.teach.frame;

import io.reactivex.disposables.Disposable;


public interface ICommonPresenter<P> extends ICommonView {

    void getData(int whichApi,P... pPS);

    void addObserver(Disposable pDisposable);
}
