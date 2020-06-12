package com.teach.data;

import java.util.List;

public class CourseBean {

    public int errNo;
    public ResultBean result;
    public int exeTime;

    public  class ResultBean {
        public List<ListsBean> lists;

        public  class ListsBean {


            public String id;
            public String lesson_id;
            public String lesson_name;
            public String type;
            public String price;
            public String vip_price;
            public int show_vip_tag;
            public String thumb;
            public String specialty_id;
            public String studentnum;
            public String m_specialty_id;
            public String rank;
            public String comment_htmls;
            public String rate;
            public int vip_tag_status;
            public List<String> comment_html;

        }
    }
}
