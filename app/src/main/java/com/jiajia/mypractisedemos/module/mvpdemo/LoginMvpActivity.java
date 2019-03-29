package com.jiajia.mypractisedemos.module.mvpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiajia.mypractisedemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginMvpActivity extends AppCompatActivity implements iView{

    @BindView(R.id.et_mvp_login_username)
    EditText et_username; // 用户名
    @BindView(R.id.et_mvp_login_password)
    EditText et_password;   // 密码
    @BindView(R.id.btn_mvp_login_login)
    Button btn_login; // 登录按钮

    private DataPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mvp);

        ButterKnife.bind(this);

        mPresenter = new DataPresenter(this); // 绑定自己的Presenter

    }

    public void login(View view) {

        mPresenter.login(String.valueOf(et_username.getText()), String.valueOf(et_password.getText()));


    }

    @Override
    public void loginSuccess(String resultMsg) {
        Toast.makeText(this, resultMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailure(String failureMsg) {
        Toast.makeText(this, failureMsg, Toast.LENGTH_SHORT).show();

    }
}
