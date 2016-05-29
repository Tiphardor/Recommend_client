package zju.edu.recommend.entity;

import java.io.Serializable;

/**
 * Created by wry2767 on 2016/5/24.
 */
public class Student implements Serializable{
    private static final long serialVersionUID = 1L;
    private String studentId;
    private String studentName;
    private String major;
    private Float averageGrade;
    private Integer rate;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Float getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Float averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
