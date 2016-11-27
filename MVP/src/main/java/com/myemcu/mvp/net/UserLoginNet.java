package com.myemcu.mvp.net;

import android.os.SystemClock;
import com.myemcu.mvp.bean.UserInfo;
/**
 * Created by Administrator on 2016/11/27 0027.
 */

public class UserLoginNet {
    // 发送用户的输入数据到服务端
    public boolean sendUserLoginInfo(UserInfo userInfo) {

        if (userInfo.username.equals("yuyang") && userInfo.password.equals("123456")) {// 验证成功
            return true;
        }else {
            return false;
        }
    }
}
