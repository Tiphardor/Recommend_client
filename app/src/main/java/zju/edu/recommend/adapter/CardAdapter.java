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
import zju.edu.recommend.entity.Course;

/**
 * Created by wry2767 on 2016/5/23.
 */
public class CardAdapter extends BaseAdapter {

    private List<Course> courseList;
    private Context context;

    public CardAdapter(List<Course> courseList,Context context){
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
        Course course = (Course)getItem(position);
        if(course != null) {
            holder.courseName.setText(course.getCourseName());
            holder.credit.setText(course.getCredit().toString());
            holder.grade.setText(course.getGrade().toString());
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
            ButterKnife.bind(this,view);
        }
    }
}
