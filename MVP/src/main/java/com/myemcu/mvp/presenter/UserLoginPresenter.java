package com.myemcu.mvp.presenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Toast;

import com.myemcu.mvp.IUserLoginView;
import com.myemcu.mvp.MainActivity;
import com.myemcu.mvp.bean.UserInfo;
import com.myemcu.mvp.net.UserLoginNet;

/**
 * Created by Administrator on 2016/11/27 0027.
 */

// P 层放的是业务代码
public class UserLoginPresenter {

    private IUserLoginView view;

    // 构造器
    public UserLoginPresenter (IUserLoginView view) {
       this.view=view;
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

    // 登陆业务
    public void login(final UserInfo userInfo) {
            // 联网模拟
            new Thread(){

                @Override
                public void run() {

                        UserLoginNet net = new UserLoginNet();  // 联网处理

                        if (net.sendUserLoginInfo(userInfo)) {  // 登录验证
                            view.login_success();
                        }else {                                 // 登录失败
                            view.login_false();
                        }

                }
            }.start();
        }
}
