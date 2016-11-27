package com.myemcu.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/11/26 0026.
 */
public class FortuneCenterActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortune_center);

        Toast.makeText(this,"登陆成功，欢迎您", Toast.LENGTH_SHORT).show();
    }
}
