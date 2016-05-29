package zju.edu.recommend.util;

import android.content.SharedPreferences;

import java.util.List;

import zju.edu.recommend.entity.CourseType;
import zju.edu.recommend.entity.Student;
import zju.edu.recommend.entity.StudentDetail;

/**
 * Created by wry2767 on 2016/5/24.
 */
public class StudentUtil {

    public static Student transToStudent(StudentDetail sd){
        Student student = new Student();
        student.setStudentId(sd.getStudentId());
        student.setStudentName(sd.getStudentName());
        student.setMajor(sd.getMajor());
        student.setAverageGrade(sd.getAverageGrade());
        student.setRate(sd.getRate());
        return student;
    }

    public static List<CourseType> addCourse(List<CourseType> degree,List<CourseType> compulsory){
        for(int i=0;i<compulsory.size();i++){
            degree.add(compulsory.get(i));
        }
        return degree;
    }
}
