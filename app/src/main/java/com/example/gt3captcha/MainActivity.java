package com.example.gt3captcha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.sdk.GT3GeetestButton;
import com.example.sdk.GT3GeetestUrl;
import com.example.sdk.GT3GeetestUtils;
import com.example.sdk.GT3ViewColor;
import com.example.sdk.Gt3GeetestTestMsg;
import com.example.sdk.Gt3GeetestViewPath;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ll_btn_type)
    GT3GeetestButton linearLayout;
    // 设置获取id，challenge，success的URL，需替换成自己的服务器URL
    private static final String captchaURL = "http://www.geetest.com/demo/gt/register-click";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-click";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //验证码加载开始
        GT3GeetestUtils.getInstance(MainActivity.this).getGeetest();
       findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

    }

    private void init() {
        new GT3GeetestUrl().setCaptchaURL(captchaURL);
        new GT3GeetestUrl().setValidateURL(validateURL);
        //下面可以自定义显示的文字
        //new Gt3GeetestTestMsg().setWaitText();
        //可以自定义显示view的颜色
        //new GT3ViewColor().setAddColor();
        //可以自定义显示view的宽度
        //new Gt3GeetestViewPath().setInternalRadius();


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void text(String a) {
        if (a.equals("dosuccess")) {
            //验证码验证成功后的操作
            new Gt3GeetestTestMsg().setCandotouch(false);//执行这个方法表示验证成功后不能点击，不执行则表示可以点击
            Toast.makeText(getApplicationContext(), "这里是验证成功后执行的操作", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
