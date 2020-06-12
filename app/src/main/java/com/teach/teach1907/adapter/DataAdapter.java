package com.teach.teach1907.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class DataAdapter  extends FragmentPagerAdapter {
    private ArrayList<Fragment> mList;

    public DataAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> pList) {
        super(fm);
        mList = pList;
    }

    public DataAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Fragment> pList) {
        super(fm, behavior);
        mList = pList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
