package zju.edu.recommend.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import zju.edu.recommend.activity.fragment.InfoFragment;
import zju.edu.recommend.activity.fragment.MyGradeFragment;
import zju.edu.recommend.activity.fragment.SettingFragment;
import zju.edu.recommend.entity.Student;

public class MainActivity extends BaseActivity {

    @Bind(R.id.id_viewpager)
    ViewPager mViewPager;

    @Bind(R.id.radioGroup)
    RadioGroup mGroup;

    @Bind(R.id.id_lv)
    ListView mListView;

    @Bind(R.id.student_id_bar)
    TextView studentIdBar;

    @Bind(R.id.student_name_bar)
    TextView studentNameBar;

    @Bind(R.id.student_major_bar)
    TextView studentMajorBar;

    private FragmentPagerAdapter mAdpter;
    private List<Fragment> mFragments = new ArrayList<>();

    private Bundle bundle;

    private String strs[] = new String[]{"我的成绩","修改密码","应用说明"};
    private int imgIds[] = new int[]{R.drawable.mygrade,R.drawable.setting,R.drawable.infomation};
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        student = (Student)bundle.getSerializable("student");
        initViews();
        initStudentInfo(student);
        initEvent();
        initViewPages();
    }

    private void initStudentInfo(Student student){
        studentIdBar.setText(student.getStudentId());
        studentNameBar.setText(student.getStudentName());
        studentMajorBar.setText(student.getMajor());
    }

    private void initViews(){
        ButterKnife.bind(this);

        List<Map<String,Object>> listItems = new ArrayList<>();
        for(int i=0;i<strs.length;i++){
            Map<String ,Object> listItem = new HashMap<>();
            listItem.put("Img",imgIds[i]);
            listItem.put("text",strs[i]);
            listItems.add(listItem);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems,
                R.layout.simpleitem,new String[]{"Img","text"},new int[]{R.id.Img,R.id.textView});
        mListView.setAdapter(simpleAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        getmDrawerLayout().closeDrawer(Gravity.LEFT);
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case 1:
                        getmDrawerLayout().closeDrawer(Gravity.LEFT);
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case 2:
                        getmDrawerLayout().closeDrawer(Gravity.LEFT);
                        mViewPager.setCurrentItem(2, false);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void initEvent() {
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.id_tab_grade:
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.id_tab_setting:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.id_tab_info:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    default:
                        break;
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mGroup.check(R.id.id_tab_grade);
                        break;
                    case 1:
                        mGroup.check(R.id.id_tab_setting);
                        break;
                    case 2:
                        mGroup.check(R.id.id_tab_info);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViewPages() {
        MyGradeFragment myGradeFragment = new MyGradeFragment();
        myGradeFragment.setArguments(bundle);
        SettingFragment settingFragment = new SettingFragment();
        InfoFragment infoFragment = new InfoFragment();

        mFragments.add(myGradeFragment);
        mFragments.add(settingFragment);
        mFragments.add(infoFragment);

        mAdpter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(mAdpter);
    }
}
