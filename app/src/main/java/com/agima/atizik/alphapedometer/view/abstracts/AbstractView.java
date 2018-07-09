package com.agima.atizik.alphapedometer.view.abstracts;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.agima.atizik.alphapedometer.flow.AbstractScreen;

import javax.inject.Inject;

import flow.Flow;


/**
 * Created by DiNo on 08.03.2018.
 */

public abstract class AbstractView<P extends AbstractViewPresenter, B extends ViewDataBinding> extends FrameLayout {

    @Inject
    protected P mPresenter;
    protected B binding;
    protected AbstractScreen mScreen;

    public AbstractView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            mScreen = Flow.getKey(context);
            initDagger(context);
        }
    }


    protected abstract void initDagger(Context context);

    protected abstract void init();

    @SuppressWarnings("unchecked")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            binding = DataBindingUtil.bind(this);
            mPresenter.takeView(this);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            binding.unbind();
            mPresenter.dropView(this);
        }
    }

    public void saveStates() {

    }


}
