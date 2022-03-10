package com.jiajia.mypractisedemos.module.mvpdemo.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.module.mvpdemo.base.BaseMVPActivity;
import com.jiajia.mypractisedemos.module.mvpdemo.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginMvpActivity extends BaseMVPActivity<LoginPresenter> implements ILoginView {

    @BindView(R.id.et_mvp_login_username)
    EditText et_username; // 用户名
    @BindView(R.id.et_mvp_login_password)
    EditText et_password;   // 密码
    @BindView(R.id.btn_mvp_login_login)
    Button btn_login; // 登录按钮



    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_login_mvp;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);

//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.login(String.valueOf(et_username.getText()), String.valueOf(et_password.getText()));
//            }
//        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
