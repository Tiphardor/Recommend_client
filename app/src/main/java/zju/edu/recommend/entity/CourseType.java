package zju.edu.recommend.entity;

import java.io.Serializable;

/**
 * Created by wry2767 on 2016/5/24.
 */
public class CourseType  implements Serializable{
    private static final long serialVersionUID = 1L;
    private String courseName;
    private String courseCode;
    private Float courseCredit;
    private Float courseScale;
    private Float courseGrade;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Float getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(Float courseCredit) {
        this.courseCredit = courseCredit;
    }

    public Float getCourseScale() {
        return courseScale;
    }

    public void setCourseScale(Float courseScale) {
        this.courseScale = courseScale;
    }

    public Float getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(Float courseGrade) {
        this.courseGrade = courseGrade;
    }

    @Override
    public String toString() {
        return "CourseType{" +
                "courseName='" + courseName + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseCredit=" + courseCredit +
                ", courseScale=" + courseScale +
                ", courseGrade=" + courseGrade +
                '}';
    }
}
