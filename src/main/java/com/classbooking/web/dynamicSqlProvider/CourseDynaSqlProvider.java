package com.classbooking.web.dynamicSqlProvider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class CourseDynaSqlProvider {

    public String selectWithParam(Map<String,Object> param){
        return new SQL(){
            {
                SELECT("*");
                FROM("class_info");
                if(param.get("type") !=null){
                    WHERE(" class_type = #{type}");
                }
                if(param.get("teacherEmail") !=null){
                    WHERE(" teacher_email = #{teacherEmail}");
                }
            }
        }.toString();

    }
}
