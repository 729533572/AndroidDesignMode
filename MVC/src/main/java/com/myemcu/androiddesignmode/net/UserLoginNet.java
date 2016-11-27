package com.myemcu.androiddesignmode.net;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.widget.Toast;

import com.myemcu.androiddesignmode.FortuneCenterActivity;
import com.myemcu.androiddesignmode.MainActivity;
import com.myemcu.androiddesignmode.bean.UserInfo;

/**
 * Created by Administrator on 2016/11/27 0027.
 */

public class UserLoginNet {
    // 发送用户的输入数据到服务端
    public boolean sendUserLoginInfo(UserInfo userInfo) {

        SystemClock.sleep(2000);    // 睡2s，模拟联网处理

        if (userInfo.username.equals("yuyang") && userInfo.password.equals("123456")) {// 验证成功
            return true;
        }else {
            return false;
        }
    }
}
