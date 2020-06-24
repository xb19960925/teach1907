package com.teach.teach1907.fragment.course;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teach.data.CourseDetailInfo;
import com.teach.data.LessonComment;
import com.teach.frame.constants.ConstantKey;
import com.teach.teach1907.R;
import com.teach.teach1907.adapter.CourseDescribeAdapter;
import com.teach.teach1907.base.BaseMvpFragment;
import com.teach.teach1907.model.CourseDetaModel;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import razerdp.design.SlideFromBottomSharePopup;


public class BriefFragment extends BaseMvpFragment<CourseDetaModel> implements PlatformActionListener {
    @BindView(R.id.class_name)
    TextView className;
    @BindView(R.id.tv_learn_num)
    TextView tvLearnNum;
    @BindView(R.id.tv_like_num)
    TextView tvLikeNum;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.discount_group)
    Group discountGroup;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_discount_vip)
    TextView tvDiscountVip;
    @BindView(R.id.together_group)
    Group togetherGroup;
    @BindView(R.id.tv_together)
    TextView tvTogether;
    @BindView(R.id.tv_together_vip)
    TextView tvTogetherVip;
    @BindView(R.id.tv_discuss_num)
    TextView tvDiscussNum;
    @BindView(R.id.tv_discuss_rate)
    TextView tvDiscussRate;
    @BindView(R.id.recyclerView_desc)
    RecyclerView recyclerViewDesc;
    @BindView(R.id.tv_bottom_tip)
    TextView tvBottomTip;
    private CourseDetailInfo courseInfo;
    private String code_f;
    private String discount;
    private CourseDetailFragment parentFragment;

    public static BriefFragment getInstance(CourseDetailInfo courseInfo, String code_f, String discount) {
        BriefFragment fragment = new BriefFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantKey.COURSE_DESCRIBE, courseInfo);
        bundle.putString(ConstantKey.COURSE_DESCRIBE_STRING, code_f);
        bundle.putString("discount", discount);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseInfo = getArguments().getParcelable(ConstantKey.COURSE_DESCRIBE);
        code_f = getArguments().getString(ConstantKey.COURSE_DESCRIBE_STRING);
        discount = getArguments().getString("discount");
    }
    @Override
    public CourseDetaModel setModel() {
        return null;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_brief;
    }

    @Override
    public void initView() {
        parentFragment = (CourseDetailFragment) getParentFragment();
        className.setText(courseInfo.getLesson_name());
        tvLearnNum.setText(courseInfo.getStudentnum() + "人学习");
        tvLikeNum.setText(courseInfo.getRate() + "好评");
        price.setText("￥" + courseInfo.getPrice());
        if (!TextUtils.isEmpty(discount)) {
            discountGroup.setVisibility(View.VISIBLE);
            tvDiscountVip.setText(discount.contains(",") ? discount.split(",")[0] : discount);
        }
        if (courseInfo.getRelevant() != null && courseInfo.getRelevant().size() != 0) {
            togetherGroup.setVisibility(View.VISIBLE);
            if (courseInfo.getRelevant().size() >= 1) {
                tvTogetherVip.setText(courseInfo.getRelevant().get(0).getLesson_name());
            }
        }
        tvDiscussNum.setText(String.format("评价(%s)", courseInfo.getComment_num()));
        tvDiscussRate.setText(String.format("好评率(%s)", courseInfo.getRate()));
        parentFragment.mShareImage.setOnClickListener(v -> doShare());
        initRecyclerView(recyclerViewDesc,null,null);
        List<LessonComment> commentList = courseInfo.getComment_list();
        recyclerViewDesc.setAdapter(new CourseDescribeAdapter(getContext(),commentList));
    }
    private SlideFromBottomSharePopup sharePop;
    private void doShare() {
        if (sharePop == null) sharePop = new SlideFromBottomSharePopup(getActivity());
        sharePop.setBottomClickListener(pos -> {
            if (pos == sharePop.WECHAT_CLICK) {
                if (courseInfo == null) return;
                Platform.ShareParams shareParams = new Platform.ShareParams();
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                shareParams.setTitle(courseInfo.getLesson_name());
                shareParams.setText(courseInfo.getLesson_name() + "https://edu.zhulong.com/lesson/" + courseInfo.getLesson_id() + "-1.html");
                shareParams.setImageUrl(courseInfo.getThumb());
                shareParams.setUrl("https://edu.zhulong.com/lesson/" + courseInfo.getLesson_id() + "-1.html");
                Platform platform = ShareSDK.getPlatform(Wechat.NAME);
                platform.setPlatformActionListener(this);
                platform.share(shareParams);
            } else showToast("暂未开发");
            sharePop.dismiss();
        });
        sharePop.showPopupWindow();
    }

    @Override
    public void initData() {

    }


    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (parentFragment != null && parentFragment.nullLoadLayout != null) {
            if (isVisibleToUser) {
                parentFragment.nullLoadLayout.setEnableLoadMore(true);
                parentFragment.nullLoadLayout.setOnLoadMoreListener(refreshLayout -> {
                    parentFragment.nullLoadLayout.finishLoadMore();
                    parentFragment.viewPager.setCurrentItem(1);
                });
            } else parentFragment.nullLoadLayout.setEnableLoadMore(false);
        }
    }



    @OnClick(R.id.tv_more_discuss)
    public void onViewClicked() {
     //   parentFragment.viewPager.setCurrentItem(parentFragment.mResult.isHideLessonList() ? 2 :3);
    }

    @Override
    public void onComplete(Platform pPlatform, int pI, HashMap<String, Object> pHashMap) {

    }

    @Override
    public void onError(Platform pPlatform, int pI, Throwable pThrowable) {

    }

    @Override
    public void onCancel(Platform pPlatform, int pI) {

    }
}