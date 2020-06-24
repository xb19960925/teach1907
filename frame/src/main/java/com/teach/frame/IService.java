package com.teach.frame;

import com.google.gson.JsonObject;
import com.teach.data.BaseInfo;
import com.teach.data.CourseBean;
import com.teach.data.CourseDetailInfo;
import com.teach.data.GroupDetailEntity;
import com.teach.data.IndexCommondEntity;
import com.teach.data.DatumBean;
import com.teach.data.LoginInfo;
import com.teach.data.MainAdEntity;
import com.teach.data.EssenceBean;
import com.teach.data.PersonHeader;
import com.teach.data.SpecialtyChooseEntity;
import com.teach.data.TestInfo;
import com.teach.data.VipBean;
import com.teach.data.VipListBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface IService {
    @GET("/")
    Observable<TestInfo> getTestData(@QueryMap Map<String, Object> params, @Query("page_id") int pageId);

    @GET("ad/getAd")
    Observable<BaseInfo<MainAdEntity>> getAdvert(@QueryMap Map<String,Object> pMap);

    @GET("lesson/getAllspecialty")
    Observable<BaseInfo<List<SpecialtyChooseEntity>>> getSubjectList();


    @GET("loginByMobileCode")
    Observable<BaseInfo<String>> getLoginVerify(@Query("mobile") String mobile);
//登录
    @GET("loginByMobileCode")
    Observable<BaseInfo<LoginInfo>> loginByVerify(@QueryMap Map<String, Object> params);

    @POST("user/userLoginNewAuth")
    @FormUrlEncoded
    Observable<BaseInfo<LoginInfo>> AccountLogin(@FieldMap Map<String, Object> params);

    @POST("getUserHeaderForMobile")
    @FormUrlEncoded
    Observable<BaseInfo<PersonHeader>> getHeaderInfo(@FieldMap Map<String,Object> params);
//微信登录
    @GET("access_token")
    Observable<JsonObject> getWechatToken(@QueryMap Map<String,Object> parmas);

    @POST("thirdlogin")
    @FormUrlEncoded
    Observable<BaseInfo<LoginInfo>> loginByWechat(@FieldMap Map<String,Object> params);

    @POST("newThirdbind")
    @FormUrlEncoded
    Observable<BaseInfo> bindThirdAccount(@FieldMap Map<String,Object> params);
    //课程
    @POST("lesson/getLessonListForApi/")
    @FormUrlEncoded
    Observable<CourseBean> getList(@FieldMap Map<String,Object> params);
//首页
    @GET("openapi/lesson/getCarouselphoto")
    Observable<JsonObject> getBannerLive(@QueryMap Map<String,Object> params);

    @POST("lesson/getIndexCommend")
    Observable<BaseInfo<List<IndexCommondEntity>>> getMainList(@QueryMap Map<String,Object> params);
    //资料
    @GET("group/getGroupList")
    Observable<DatumBean>getInformation(@QueryMap Map<String,Object>params);

    @GET("group/getThreadEssence")
    Observable<EssenceBean>getnewbest(@QueryMap Map<String,Object>params);
//VIP
    @GET("lesson/get_new_vip")
    Observable<VipBean> getBanner(@QueryMap Map<String, Object> params);

    @POST("lesson/getVipSmallLessonList")
    @FormUrlEncoded
    Observable<VipListBean> getApiList(@FieldMap Map<String,Object> params);
    //关注
    @POST("removeGroup")
    @FormUrlEncoded
    Observable<BaseInfo> removeFocus(@FieldMap Map<String,Object> params);

    @POST("joingroup")
    @FormUrlEncoded
    Observable<BaseInfo> focus( @FieldMap Map<String,Object> params);
    //注册
    @POST("checkMobileIsUse")
    @FormUrlEncoded
    Observable<BaseInfo> checkPhoneIsUsed(@Field("mobile")Object mobile);

    @POST("checkMobileCode")
    @FormUrlEncoded
    Observable<BaseInfo> checkVerifyCode(@FieldMap Map<String,Object> params);

    @POST("sendMobileCode")
    @FormUrlEncoded
    Observable<BaseInfo> sendRegisterVerify(@Field("mobile")Object mobile);
    //用户名
    @POST("user/usernameIsExist")
    @FormUrlEncoded
    Observable<BaseInfo> checkName(@Field("username")Object mobile);

    @POST("userRegForSimple")
    @FormUrlEncoded
    Observable<BaseInfo> registerCompleteWithSubject(@FieldMap Map<String,Object> params);
    // 详情
    @GET("group/getGroupThreadList")
    Observable<BaseInfo<GroupDetailEntity>> getGroupDetail(@Query("gid") Object object);

    @GET("group/getGroupThreadList")
    Observable<JsonObject> getGroupDetailFooterData( @QueryMap Map<String,Object> params);
    //课程详情
    @POST("getNewLessonDetail")
    @FormUrlEncoded
    Observable<BaseInfo<CourseDetailInfo>> getCourseDetail(@FieldMap Map<String, Object> pMap);
}

