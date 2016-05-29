package zju.edu.recommend.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zju.edu.recommend.entity.Student;
import zju.edu.recommend.entity.StudentDetail;
import zju.edu.recommend.util.Api;
import zju.edu.recommend.util.StudentUtil;

/**
 * Created by wry2767 on 2016/5/24.
 */
public class LoginActivity extends Activity {

    private final OkHttpClient client = new OkHttpClient();
    LinearLayout linearLayout;
    @Bind(R.id.student_id)
    EditText studentIdEdit;

    @Bind(R.id.student_password)
    EditText passwordEdit;

    @Bind(R.id.student_login_btn)
    Button onStudentLoginButton;

    private Handler mHandler = null;
    private String studentId = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Intent intent = getIntent();
        if(intent != null) {
            studentId = intent.getStringExtra("studentId");
        }
        ButterKnife.bind(this);
        mHandler = new MyHandler(this,onStudentLoginButton);
        if(studentId != null){
            studentIdEdit.setText(studentId);
            passwordEdit.setFocusable(true);
        }
    }

    @OnClick(R.id.student_login_btn)
    void onStudentLogin(final View view){
        final String studentId = studentIdEdit.getText().toString();
        final String password = passwordEdit.getText().toString();

        if(studentId == null || studentId.trim().equals("")){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password == null || password.trim().equals("")){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    execute(studentId,password);
                    onStudentLoginButton.setClickable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void execute(String studentId,String password) throws Exception{
        System.out.println(studentId);
        System.out.println(password);
        FormBody formBody = new FormBody.Builder()
                .add("studentId",studentId)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(Api.URL + Api.LOGIN)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = null;
                if (mHandler.obtainMessage() != null) {
                    message = mHandler.obtainMessage();
                } else {
                    message = new Message();
                }
                message.arg1 = 0;
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println(response.code());
                    String resultJson = response.body().string();
                    ObjectMapper mapper = new ObjectMapper();
                    StudentDetail studentDetail = mapper.readValue(resultJson, StudentDetail.class);
                    Message message = null;
                    if (mHandler.obtainMessage() != null) {
                        message = mHandler.obtainMessage();
                    } else {
                        message = new Message();
                    }
                    message.obj = studentDetail;
                    message.arg1 = 1;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    private static class MyHandler extends Handler {
        private final WeakReference<LoginActivity> mActivity;
        private final WeakReference<Button> onStudentLoginButton;

        public MyHandler(LoginActivity activity,Button onStudentLogin) {
            mActivity = new WeakReference<LoginActivity>(activity);
            onStudentLoginButton = new WeakReference<Button>(onStudentLogin);
        }

        @Override
        public void handleMessage(Message msg) {
            onStudentLoginButton.get().setClickable(true);
            if(msg.arg1 == 0){
                Toast.makeText(mActivity.get(), "没有网络哦", Toast.LENGTH_SHORT).show();
            }else {
                StudentDetail studentDetail = (StudentDetail) msg.obj;
                String resultCode = studentDetail.getResultCode();
                if (resultCode.equals("0")) {
                    String studentId = studentDetail.getStudentId();
                    SharedPreferences.Editor editor = mActivity.get().getSharedPreferences("recommend", MODE_PRIVATE).edit();
                    editor.putString("studentId", studentId);
                    editor.commit();
                    Student student = StudentUtil.transToStudent(studentDetail);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("resultCode", "0");
                    bundle.putSerializable("student", student);
                    bundle.putSerializable("compulsory", (Serializable) studentDetail.getCompulsory());
                    bundle.putSerializable("degree", (Serializable) studentDetail.getDegree());
                    intent.putExtra("bundle", bundle);
                    intent.setClass(mActivity.get(), MainActivity.class);
                    mActivity.get().startActivity(intent);
                } else if (resultCode.equals("9999")) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("resultCode", "9999");
                    bundle.putString("resultResult", "暂无此学生成绩");
                    intent.putExtra("buddle", bundle);
                    intent.setClass(mActivity.get(), MainActivity.class);
                    mActivity.get().startActivity(intent);
                } else if (resultCode.equals("9998")) {
                    Toast.makeText(mActivity.get(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
            super.handleMessage(msg);
        }
    }

}
