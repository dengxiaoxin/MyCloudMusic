package com.jacky.mycloudmusic.fragment;

import com.jacky.mycloudmusic.AppContext;
import com.jacky.mycloudmusic.MainActivity;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.domain.Session;
import com.jacky.mycloudmusic.domain.User;
import com.jacky.mycloudmusic.domain.response.DetailResponse;
import com.jacky.mycloudmusic.listener.HttpObserver;
import com.jacky.mycloudmusic.networkapi.RetrofitAPI;
import com.jacky.mycloudmusic.util.LogUtil;
import com.jacky.mycloudmusic.util.ToastUtil;

/**
 * 登录父类
 */
public class BaseLoginFragment extends BaseCommonFragment {

    private static final String TAG = "======BaseLoginFragment";

    /**
     * 登录
     *
     * @param phone    手机号
     * @param email    邮箱
     * @param password 密码
     */
    protected void postLogin(String phone, String email, String password) {
        //配置要发送的用户信息
        User user = new User();
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);

        RetrofitAPI.getInstance()
                //发出请求
                .login(user)
                //回调函数
                .subscribe(new HttpObserver<DetailResponse<Session>>(getCurrentActivity(), true) {
                               @Override
                               public void onSucceeded(DetailResponse<Session> sessionDetailResponse) {
                                   Session data = sessionDetailResponse.getData();

                                   LogUtil.d(TAG, "login success:" + data);

                                   //保存登录成功信息
                                   AppContext.getInstance().loginSuccess(sp, data);

                                   ToastUtil.successShortToast(R.string.login_success);

                                   //跳转到首页
                                   startActivityAndFinishThis(MainActivity.class);
                               }
                           }
                );
    }
}
