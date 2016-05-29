package zju.edu.recommend.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zju.edu.recommend.activity.LoginActivity;
import zju.edu.recommend.activity.MainActivity;
import zju.edu.recommend.activity.R;
import zju.edu.recommend.entity.Student;
import zju.edu.recommend.entity.StudentDetail;
import zju.edu.recommend.util.Api;
import zju.edu.recommend.util.StudentUtil;

/**
 * Created by wry2767 on 2016/5/23.
 */
public class SettingFragment extends Fragment{


    @Bind(R.id.origin_password)
    EditText originPasswordEdit;

    @Bind(R.id.new_password)
    EditText newPasswordEdit;

    @Bind(R.id.re_password)
    EditText rePasswordEdit;

    private final OkHttpClient client = new OkHttpClient();
    private String studentId = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.fragment_setting,container,false);
        initViews(settingLayout);
        return settingLayout;
    }

    private void initViews(View settingLayout) {
        ButterKnife.bind(this, settingLayout);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @OnClick(R.id.update_student_btn)
    void onUpdateStudentPassword(View view){
        final String originPassword = originPasswordEdit.getText().toString();
        final String newPassword = newPasswordEdit.getText().toString();
        String rePassword = rePasswordEdit.getText().toString();
        SharedPreferences preferences = getActivity().getSharedPreferences("recommend", Context.MODE_PRIVATE);
        studentId = preferences.getString("studentId",null);
        if(originPassword == null || originPassword.trim().equals("")){
            Toast.makeText(getActivity(), "原密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(newPassword == null || newPassword.trim().equals("")){
            Toast.makeText(getActivity(), "新密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!newPassword.trim().equals(rePassword.trim())){
            Toast.makeText(getActivity(), "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    execute(originPassword,newPassword);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void execute(String originPassword,String newPassword){
        FormBody formBody = new FormBody.Builder()
                .add("studentId",studentId)
                .add("oldPassword", originPassword)
                .add("newPassword",newPassword)
                .build();
        Request request = new Request.Builder()
                .url(Api.URL + Api.UPDATE_STUDENT_PASSWORD)
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
                    String resultCode = response.body().string();
                    if(resultCode.equals("0")){
                        Intent intent = new Intent();
                        intent.putExtra("studentId",studentId);
                        intent.setClass(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Message message = null;
                        if (mHandler.obtainMessage() != null) {
                            message = mHandler.obtainMessage();
                        } else {
                            message = new Message();
                        }
                        message.arg1 = 1;
                        mHandler.sendMessage(message);
                    }

                }
            }
        });
    }


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 0){
                Toast.makeText(getActivity(), "没有网络哦", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "原密码不正确", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };
}
