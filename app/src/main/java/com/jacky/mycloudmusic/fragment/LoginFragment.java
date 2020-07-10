package com.jacky.mycloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.util.StringUtil;
import com.jacky.mycloudmusic.util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseLoginFragment implements View.OnClickListener {

    private static final String TAG = "======LoginFragment";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnForgetPassword;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    protected void initViews() {
        super.initViews();

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnForgetPassword = findViewById(R.id.btn_forget_password);
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btnLogin.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                onBtnLoginClick();
                break;
            case R.id.btn_forget_password:
                onBtnForgetPasswordClick();
                break;
            default:
                break;
        }
    }

    private void onBtnLoginClick() {
        String username = etUsername.getText().toString().trim();
        if (username.length() == 0) {
            ToastUtil.errorShortToast(R.string.enter_username);
            return;
        }

        if (!StringUtil.isPhoneNum(username) && !StringUtil.isEmailAddress(username)) {
            ToastUtil.errorShortToast(R.string.error_username_format);
            return;
        }

        String password = etPassword.getText().toString().trim();
        if (password.length() == 0) {
            ToastUtil.errorShortToast(R.string.enter_password);
            return;
        }

        if (!StringUtil.isPassword(password)) {
            ToastUtil.errorShortToast(R.string.error_password_format);
            return;
        }

        //判断是手机号还是邮箱
        String phone = null;
        String email = null;
        if (StringUtil.isPhoneNum(username)) {
            phone = username;
        } else {
            email = username;
        }

        //请求登录（父类方法）
        postLogin(phone, email, password);
    }

    private void onBtnForgetPasswordClick() {
        ToastUtil.infoShortToast("onBtnForgetPasswordClick");
    }
}
