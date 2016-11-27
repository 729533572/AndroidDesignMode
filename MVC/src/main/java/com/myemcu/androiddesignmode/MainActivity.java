package com.myemcu.androiddesignmode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.myemcu.androiddesignmode.bean.UserInfo;
import com.myemcu.androiddesignmode.net.UserLoginNet;

public class MainActivity extends AppCompatActivity {

    private EditText et_user_name,et_user_password;
    private Button btn_login;
    private ProgressDialog dialog; // 进度条对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();    // 实例化控件

        dialog = new ProgressDialog(this);  // 创建进度条对话框

        SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
        String user_name = sp.getString("user_name", null);
        et_user_name.setText(user_name);

        // 监听登陆按钮
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_login(view);
            }
        });
    }

    // 处理用户登录
    private void user_login(View view) {

        // 获取输入
        final String userName = et_user_name.getText().toString();
        final String passWord = et_user_password.getText().toString();

        // 传递输入
        final UserInfo userInfo = new UserInfo();
        userInfo.username = userName;
        userInfo.password = passWord;

        // 校验输入(仅判空)
        boolean isNull = checkUserInfo(userInfo);  // 校验错误输入
        if (!isNull) {  // 不为空
            dialog.show();  // 显示滚动条

            // 联网模拟
            new Thread(){
                @Override
                public void run() {

                    UserLoginNet net = new UserLoginNet();  // 联网处理

                    if (net.sendUserLoginInfo(userInfo)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 1 关进度条
                                dialog.dismiss();

                                // 2 保存登陆的用户名
                                SharedPreferences sp = getSharedPreferences("user_info",MODE_PRIVATE);
                                sp.edit().putString("user_name",userName).apply();

                                // 3 页面跳转
                                Intent intent = new Intent(MainActivity.this,FortuneCenterActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();   // 关进度条
                                Toast.makeText(MainActivity.this,"账号或密码有误",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }.start();
        }else {
            Toast.makeText(MainActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
        }

    }

    // 校验用户登陆信息是否为空
    public boolean checkUserInfo(UserInfo userInfo) {
        if (TextUtils.isEmpty(userInfo.username) || TextUtils.isEmpty(userInfo.password)) {
            return true;   // 为空返回true
        }
        else {
            return false;
        }
    }

    // 实例化控件
    private void findViews() {
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_password = (EditText) findViewById(R.id.et_user_password);
        btn_login = (Button) findViewById(R.id.btn_login);
    }
}
