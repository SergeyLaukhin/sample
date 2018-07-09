package com.agima.atizik.alphapedometer.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.agima.atizik.alphapedometer.R;

import com.agima.atizik.alphapedometer.databinding.ActivityRootBinding;
import com.agima.atizik.alphapedometer.di.DaggerService;
import com.agima.atizik.alphapedometer.di.app.AppComponent;
import com.agima.atizik.alphapedometer.di.root.RootModule;
import com.agima.atizik.alphapedometer.di.root.RootScope;
import com.agima.atizik.alphapedometer.flow.AbstractScreen;
import com.agima.atizik.alphapedometer.flow.BasicKeyParceler;
import com.agima.atizik.alphapedometer.flow.TreeKeyDispatcher;

import com.agima.atizik.alphapedometer.view.abstracts.IRootView;
import com.agima.atizik.alphapedometer.view.login.LoginScreen;


import javax.inject.Inject;

import flow.ClassKey;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

/**
 * Created by DiNo on 08.03.2018.
 */


public class RootActivity extends AppCompatActivity implements IRootView {

    @Inject
    RootPresenter rootPresenter;
    private ActivityRootBinding binding;
    private TreeKeyDispatcher mTreeKeyDispatcher;

    @Override
    protected void attachBaseContext(Context newBase) {
        mTreeKeyDispatcher = new TreeKeyDispatcher(this);
        newBase = Flow.configure(newBase, this)
                .dispatcher(mTreeKeyDispatcher)
                .defaultKey(getDefaultScreen())
                .install();
        super.attachBaseContext(newBase);
    }

    private ClassKey getDefaultScreen() {
        return new LoginScreen();
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        MortarScope RootActivityScope = MortarScope.findChild(getApplicationContext(), RootActivity.class.getName());
        return RootActivityScope.hasService(name) ? RootActivityScope.getService(name) : super.getSystemService(name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_root);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_root);
        super.onCreate(savedInstanceState);
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
        DaggerService.<RootActivity.RootComponent>getDaggerComponent(this).inject(this);
        rootPresenter.takeView(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy() {
        rootPresenter.dropView(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (Flow.get(this).getHistory().size() > 1) {
            if (getCurrentScreen() != null && !Flow.get(this).goBack() && !viewOnBackPressed()) {
                super.onBackPressed();
            }
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
        } else {
            createDialogExit();
        }
    }


    private void createDialogExit() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.dialog_exit_app_message)
                .setPositiveButton(R.string.dialog_logout_positive_button, (dialog, which) -> finish())
                .setNegativeButton(R.string.dialog_logout_negative_button, null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void showScreen(AbstractScreen screen) {
        Flow.get(this).set(screen);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    public View getCurrentScreen() {
        return binding.rootFrame.getChildAt(0);
    }


    @dagger.Component(dependencies = AppComponent.class, modules = {RootModule.class})
    @RootScope
    public interface RootComponent {

        void inject(RootActivity activity);

        RootPresenter getRootPresenter();

    }

}
