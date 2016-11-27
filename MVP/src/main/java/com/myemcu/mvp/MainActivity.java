package com.myemcu.mvp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myemcu.mvp.bean.UserInfo;
import com.myemcu.mvp.presenter.UserLoginPresenter;

public class MainActivity extends AppCompatActivity implements IUserLoginView {

    private EditText et_user_name,et_user_password;
    private Button btn_login;
    private ProgressDialog dialog; // 进度条对话框

    private UserLoginPresenter presenter;
    private String userName;
    private String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();    // 实例化控件(界面处理)

        presenter = new UserLoginPresenter(this);

        // 监听登陆
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    // 实例化控件
    private void findViews() {
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_password = (EditText) findViewById(R.id.et_user_password);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    public void login() {   // 界面相关
        // 获取输入到String
        userName = et_user_name.getText().toString();
        passWord = et_user_password.getText().toString();
        // 传递输入到bean
        final UserInfo userInfo = new UserInfo();
        userInfo.username = userName;
        userInfo.password = passWord;

        boolean isNull = presenter.checkUserInfo(userInfo);
        if (!isNull) {
            dialog.show();  // 显示滚动条
            presenter.login(userInfo);
        }else {
            Toast.makeText(MainActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
        }
    }

    // 登陆成功
    public void login_success() {

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
    }

    // 登录失败
    public void login_false() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();   // 关进度条
                Toast.makeText(MainActivity.this,"账号或密码有误",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
