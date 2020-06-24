package com.teach.teach1907.model;

import androidx.viewpager.widget.ViewPager;

public abstract class PagerSelectedListener implements ViewPager.OnPageChangeListener {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageSelected(position);
    }



    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void pageSelected(int pPosition) {
    }
}
