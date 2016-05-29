package zju.edu.recommend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zju.edu.recommend.activity.R;
import zju.edu.recommend.entity.CourseType;

/**
 * Created by wry2767 on 2016/5/24.
 */
public class StudentCourseAdapter extends BaseAdapter {
    private List<CourseType> courseList;
    private Context context;

    public StudentCourseAdapter(List<CourseType> courseList,Context context){
        this.courseList = courseList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_card,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        CourseType courseType = (CourseType)getItem(position);
        if(courseType != null) {
            holder.courseName.setText(courseType.getCourseName());
            holder.credit.setText(courseType.getCourseCredit().toString());
            holder.grade.setText(courseType.getCourseGrade().toString());
        }
        return convertView;
    }

    public static class ViewHolder{

        @Bind(R.id.course_text)
        TextView courseName;

        @Bind(R.id.credit_text)
        TextView credit;

        @Bind(R.id.grade_text)
        TextView grade;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
