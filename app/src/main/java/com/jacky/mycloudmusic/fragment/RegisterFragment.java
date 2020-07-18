package com.jacky.mycloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.activity.CommonToolbarActivity;
import com.jacky.mycloudmusic.domain.BaseModel;
import com.jacky.mycloudmusic.domain.User;
import com.jacky.mycloudmusic.domain.response.DetailResponse;
import com.jacky.mycloudmusic.listener.HttpObserver;
import com.jacky.mycloudmusic.networkapi.RetrofitAPI;
import com.jacky.mycloudmusic.util.LogUtil;
import com.jacky.mycloudmusic.util.StringUtil;
import com.jacky.mycloudmusic.util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseLoginFragment implements View.OnClickListener {

    private static final String TAG = "======RegisterFragment";
    private EditText etNickname;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private TextView tvAgreement;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    protected void initViews() {
        super.initViews();

        etNickname = findViewById(R.id.et_nickname);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvAgreement = findViewById(R.id.tv_agreement);
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btnRegister.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                onBtnRegisterClick();
                break;
            case R.id.tv_agreement:
                onAgreementClick();
                break;
            default:
                break;
        }
    }

    private void onBtnRegisterClick() {
        //检查昵称
        String nickname = etNickname.getText().toString().trim();
        if (nickname.length() == 0) {
            ToastUtil.errorShortToast(R.string.enter_nickname);
            return;
        }

        if (!StringUtil.isNickname(nickname)) {
            ToastUtil.errorShortToast(R.string.error_nickname_format);
            return;
        }

        //检查手机号
        String phone = etPhone.getText().toString().trim();
        if (phone.length() == 0) {
            ToastUtil.errorShortToast(R.string.enter_phone);
            return;
        }

        if (!StringUtil.isPhoneNum(phone)) {
            ToastUtil.errorShortToast(R.string.error_phone_format);
            return;
        }

        //检查邮箱
        String email = etEmail.getText().toString().trim();
        if (email.length() == 0) {
            ToastUtil.errorShortToast(R.string.enter_email);
            return;
        }

        if (!StringUtil.isEmailAddress(email)) {
            ToastUtil.errorShortToast(R.string.error_email_format);
            return;
        }

        //检查密码
        String password = etPassword.getText().toString().trim();
        if (password.length() == 0) {
            ToastUtil.errorShortToast(R.string.enter_password);
            return;
        }

        if (!StringUtil.isPassword(password)) {
            ToastUtil.errorShortToast(R.string.error_password_format);
            return;
        }

        //检查确认密码
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        if (confirmPassword.length() == 0) {
            ToastUtil.errorShortToast(R.string.enter_confirm_password);
            return;
        }

        if (!StringUtil.isPassword(confirmPassword)) {
            ToastUtil.errorShortToast(R.string.error_confirm_password_format);
            return;
        }

        if (!password.equals(confirmPassword)) {
            ToastUtil.errorShortToast(R.string.error_confirm_password);
            return;
        }

        //向服务器发送注册请求
        User user = new User();
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);

        RetrofitAPI.getInstance()
                .register(user)
                .subscribe(new HttpObserver<DetailResponse<BaseModel>>() {
                    @Override
                    public void onSucceeded(DetailResponse<BaseModel> baseModelDetailResponse) {
                        LogUtil.d(TAG, "register success:" + baseModelDetailResponse.getData().getId());

                        //自动登录（父类方法）
                        postLogin(phone, email, password);
                    }
                });
    }

    private void onAgreementClick() {
        //使用了一个小米用户协议做样子
        startActivityContainWebView(CommonToolbarActivity.class, getString(R.string.title_user_agreement), "http://www.miui.com/res/doc/eula/cn.html");
    }
}
