package com.teach.teach1907.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class CourseVpAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list;

    public CourseVpAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> pList) {
        super(fm);
        list = pList;
    }

    public CourseVpAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Fragment> pList) {
        super(fm, behavior);
        list = pList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
