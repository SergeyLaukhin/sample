package com.agima.atizik.alphapedometer.view.login;

import android.content.Context;

import android.util.AttributeSet;



import com.agima.atizik.alphapedometer.databinding.ScreenLoginBinding;
import com.agima.atizik.alphapedometer.di.DaggerService;

import com.agima.atizik.alphapedometer.view.abstracts.AbstractView;

/**
 * Created by DiNo on 08.03.2018.
 */

public class LoginView extends AbstractView<LoginScreen.LoginPresenter, ScreenLoginBinding> {

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<LoginScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void init() {
        initListeners();
    }

    private void initListeners() {
        binding.btnLogin.setOnClickListener(v -> mPresenter.login());
    }

    public String getEmail() {
        return binding.editEmail.getText().toString();
    }

    public String getPassword() {
        return binding.editPassword.getText().toString();
    }

    public boolean isValidate() {
        return true;
    }

    public void showProgressDialog() {

    }

    public void closeProgressDialog() {

    }

}
