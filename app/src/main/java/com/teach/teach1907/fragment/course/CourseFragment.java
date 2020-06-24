package com.teach.teach1907.fragment.course;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teach.data.CourseDetailInfo;
import com.teach.data.LessonChapterEntity;
import com.teach.frame.ICommonModel;
import com.teach.frame.constants.ConstantKey;
import com.teach.teach1907.R;
import com.teach.teach1907.adapter.CourseListChildAdapter;
import com.teach.teach1907.adapter.CourseListGroupAdapter;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.interfaces.OnRecyclerItemClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseFragment extends BaseMvpFragment implements OnRecyclerItemClick, CourseListGroupAdapter.OnCourseGroupListClick{
    @BindView(R.id.courseListRecycle)
    RecyclerView courseListRecycle;
    private CourseDetailInfo courseInfo;
    private String code_f;
    private String homeLiveId;
    private List<LessonChapterEntity> mCatalogueList;
    private CourseListChildAdapter mCourseListChildAdapter;
    private CourseListGroupAdapter mAdapter;
    List<String> mMergeList = new ArrayList<>();
    public static CourseFragment getInstance(CourseDetailInfo courseInfo, String code_f, String homeLiveId) {
        CourseFragment fragment = new CourseFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantKey.COURSE_LIST, courseInfo);
        bundle.putString(ConstantKey.COURSE_LIST_CODE_F, code_f);
        bundle.putString(ConstantKey.COURSE_LIST_HOMELIVE_ID, homeLiveId);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseInfo = getArguments().getParcelable(ConstantKey.COURSE_LIST);
            code_f = getArguments().getString(ConstantKey.COURSE_LIST_CODE_F);
            homeLiveId = getArguments().getString(ConstantKey.COURSE_LIST_HOMELIVE_ID);
            mCatalogueList = courseInfo.getCatalogue().getCatalogueList();
        }
    }

    @Override
    public ICommonModel setModel() {
        return null;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_cours;
    }

    @Override
    public void initView() {
        initRecyclerView(courseListRecycle, null, null);
        if (mCatalogueList.size() > 1){
            mAdapter = new CourseListGroupAdapter(getContext(), mCatalogueList, mMergeList);
            courseListRecycle.setAdapter(mAdapter);
            mAdapter.setOnRecyclerItemClick(this);
            mAdapter.setOnCourseGroupListClick(this);
        } else if (mCatalogueList.size() == 1){
            mCourseListChildAdapter = new CourseListChildAdapter(getContext(), mCatalogueList.get(0).getPartList());
            courseListRecycle.setAdapter(mCourseListChildAdapter);
            mCourseListChildAdapter.setOnRecyclerItemClick((pos, pObjects) -> {
                showLog("pos:"+pos+","+((int)pObjects[0]== CourseListChildAdapter.ITEM_TYPE?"播放":"下载"));
            });
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }

    /**
     * 当有分组时，控制适配器中recycleview的条目点击逻辑
     * @param fatherPos 子条目所对应父条目的位置
     * @param childPos 子条目的位置
     * @param clickType 点击类型
     */
    @Override
    public void onItemClick(int fatherPos, int childPos, int clickType) {
        showLog("fatherPos:"+fatherPos+",childPos:"+childPos+","+(clickType == CourseListChildAdapter.ITEM_TYPE?"播放":"下载"));

    }
//控制组条目点击时展开和合并组内子recycleview成员
    @Override
    public void onItemClick(int position, Object[] pObjects) {
        String name = mCatalogueList.get(position).getCatalogueName();
        if (mMergeList.contains(name))mMergeList.remove(name);
        else mMergeList.add(name);
        mAdapter.notifyDataSetChanged();
    }
}