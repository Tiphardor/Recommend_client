package zju.edu.recommend.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zju.edu.recommend.activity.R;
import zju.edu.recommend.adapter.StudentCourseAdapter;
import zju.edu.recommend.entity.CourseType;
import zju.edu.recommend.entity.Student;

/**
 * Created by wry2767 on 2016/5/23.
 */
public class MyGradeFragment  extends Fragment{

    @Bind(R.id.avegrade_textview)
    TextView averageGradeText;

    @Bind(R.id.rate_textview)
    TextView rateText;

    @Bind(R.id.degree_listView)
    ListView degreeListView;

    @Bind(R.id.compulsory_listView)
    ListView compulsoryListView;

    @Bind(R.id.scrollView)
    ScrollView mScrollView;

    private List<CourseType> compulsoryList = new ArrayList<>();
    private List<CourseType> degreeList = new ArrayList<>();
    private Student student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        System.out.println("resultCode------>"+bundle.getCharSequence("resultCode"));
        compulsoryList = (List<CourseType>)bundle.getSerializable("compulsory");
        degreeList = (List<CourseType>)bundle.getSerializable("degree");
        student = (Student)bundle.getSerializable("student");

        View gradeLayout = inflater.inflate(R.layout.fragment_mygrade,container,false);
        initViews(gradeLayout);
        setAveGradeAndRate(student);
        return gradeLayout;
    }

    private void initViews(View gradeLayout){
        ButterKnife.bind(this, gradeLayout);
        mScrollView.smoothScrollTo(0,0);
        StudentCourseAdapter degreeCourseAdapter = new StudentCourseAdapter(degreeList,getActivity());
        StudentCourseAdapter compulsoryCourseAdapter = new StudentCourseAdapter(compulsoryList,getActivity());
        degreeListView.addHeaderView(new View(getActivity()));
        degreeListView.addFooterView(new View(getActivity()));
        degreeListView.setAdapter(degreeCourseAdapter);

        compulsoryListView.addHeaderView(new View(getActivity()));
        compulsoryListView.addFooterView(new View(getActivity()));
        compulsoryListView.setAdapter(compulsoryCourseAdapter);
    }

    private void setAveGradeAndRate(Student student){
        averageGradeText.setText(student.getAverageGrade().toString());
        rateText.setText(student.getRate().toString());
    }
}
