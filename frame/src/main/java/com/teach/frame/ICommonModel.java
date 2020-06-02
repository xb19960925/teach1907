package com.teach.frame;

public interface ICommonModel<T> {

    void getData(ICommonPresenter pPresenter,int whichApi,T... params);

}
