package com.teach.data;

import java.io.Serializable;
import java.util.List;

public class CourseBean implements Serializable {

    private static final long serialVersionUID = 4553503611770493560L;
    public int errNo;
    public ResultBean result;
    public int exeTime;

    public  class ResultBean implements Serializable{
        public List<ListsBean> lists;

        public  class ListsBean implements Serializable {


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
