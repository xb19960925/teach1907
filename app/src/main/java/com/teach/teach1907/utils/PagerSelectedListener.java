package com.teach.teach1907.utils;

import androidx.viewpager.widget.ViewPager;

public  abstract  class PagerSelectedListener implements ViewPager.OnPageChangeListener {
   public void onPageScrollStateChanged(int state) {

    }
    void onPageScrolled(int position,  Float positionOffset, int positionOffsetPixels) {

    }
    public void onPageSelected(int position) {
        pageSelected(position);
    }
    protected abstract void pageSelected(int postion);

}
