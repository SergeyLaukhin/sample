package com.agima.atizik.alphapedometer.flow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.agima.atizik.alphapedometer.R;
import com.agima.atizik.alphapedometer.mortar.ScreenScoper;
import com.agima.atizik.alphapedometer.utils.Lg;

import java.lang.annotation.Retention;
import java.util.Collections;
import java.util.Map;

import flow.Direction;
import flow.Dispatcher;
import flow.Flow;
import flow.State;
import flow.Traversal;
import flow.TraversalCallback;
import flow.TreeKey;
import mortar.MortarScope;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class TreeKeyDispatcher implements Dispatcher {
    private static final String TAG = "TreeKeyDispatcher";

    private Activity mActivity;
    private View mCurrentView;

    public TreeKeyDispatcher(Activity activity) {
        mActivity = activity;
    }

    private static void waitForMeasure(final View view, final OnMeasuredCallback callback) {
        int width = view.getWidth();
        int height = view.getHeight();

        if (width > 0 && height > 0) {
            callback.onMeasured(view);
            return;
        }

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final ViewTreeObserver observer = view.getViewTreeObserver();
                if (observer.isAlive()) {
                    observer.removeOnPreDrawListener(this);
                }

                callback.onMeasured(view);
                return true;
            }
        });
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        State inState = traversal.getState(traversal.destination.top());
        State outState = traversal.origin == null ? null : traversal.getState(traversal.origin.top());
        Object inKey = inState.getKey();
        Object outKey = outState == null ? null : (Object) outState.getKey();
        FrameLayout rootFrame = (FrameLayout) mActivity.findViewById(R.id.root_frame);

        mCurrentView = null;
        Layout screen = inKey.getClass().getAnnotation(Layout.class);
        int layout = screen.value();
        final int animationType = getAnimationType(outKey,layout);
        if (rootFrame.getChildCount() > 0) {
            mCurrentView = rootFrame.getChildAt(0);

            final Object currentKey = Flow.getKey(mCurrentView);
            if (inKey.equals(currentKey)) {
                callback.onTraversalCompleted();
                return;
            }

            if (outState != null) {
                outState.save(mCurrentView);
            }

            if (animationType == AnimationType.NONE) {
                clearScope(traversal.direction, inKey, outKey, mCurrentView);
                rootFrame.removeAllViews();
            } else {
                clearScope(traversal.direction, inKey, outKey, mCurrentView);
            }
        }
        if (layout == 0) {
            throw new IllegalStateException("Can't get layoutResId for screen ");
        }
        Context flowContext = traversal.createContext(inKey, mActivity);
        Context screenContext = ScreenScoper.getScreenScope((AbstractScreen) inKey).createContext(flowContext);
        Map<Object, Context> contexts;
        contexts = Collections.singletonMap(inKey, screenContext);
        Context context = contexts.get(inKey);
        LayoutInflater inflater = LayoutInflater.from(context);
        View newView = inflater.inflate(layout, rootFrame, false);
        inState.restore(newView);
        rootFrame.addView(newView);
        if (animationType != AnimationType.NONE) {
            waitForMeasure(newView, view -> runAnimation(rootFrame, mCurrentView, newView, traversal.direction, callback));
        } else {
            callback.onTraversalCompleted();
        }
    }

    private void clearScope(@NonNull Direction direction, Object inKey, Object outKey, View currentView) {
        if (direction == Direction.BACKWARD) {
            MortarScope ms = MortarScope.getScope(currentView.getContext());
            Log.e(TAG, "dispatch: " + ms.getName() + " was destroyed");
            ms.destroy();
        } else if (!(inKey instanceof TreeKey) && outKey != null) {
            ((AbstractScreen) outKey).unregisterScope();
        }
    }

    @AnimationType
    private int getAnimationType(Object screen,int layout) {
        if (screen == null) return AnimationType.NONE;
        switch (layout) {
            case R.layout.screen_login:
                return AnimationType.SLIDE_SIDE;
            /*case R.layout.screen_splash:
                return AnimationType.NONE;
            case R.layout.screen_main:
                return AnimationType.SLIDE_SIDE;*/
            default:
                return AnimationType.NONE;
        }
    }

    private void runAnimation(final ViewGroup container, final View from, final View to,
                              Direction direction, final TraversalCallback callback) {

        Lg.e("FLOW_ANIM", "runAnimation: ");
        Animator animator = createSegue(from, to, direction);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (from != null) {
                    container.removeView(from);
                }
                callback.onTraversalCompleted();
            }
        });
        animator.setInterpolator(new FastOutLinearInInterpolator());
        animator.start();
    }

    private Animator createSegue(@Nullable View from, View to, Direction direction) {
        boolean backward = direction == Direction.BACKWARD;

        AnimatorSet set = new AnimatorSet();

        int fromTranslation = 0;
        if (from != null) {
            fromTranslation = backward ? from.getWidth() : -from.getWidth();
            set.play(ObjectAnimator.ofFloat(from, View.TRANSLATION_X, fromTranslation));
        }
        int toTranslation = backward ? -to.getMeasuredWidth() : to.getMeasuredWidth();
        Lg.e("TRANS", "fromTranslation: " + fromTranslation);
        Lg.e("TRANS", "toTranslation: " + toTranslation);

        ObjectAnimator fade = ObjectAnimator.ofFloat(to, "alpha", 0f, 1f);
        ObjectAnimator trans = ObjectAnimator.ofFloat(to, View.TRANSLATION_X, toTranslation, 0);
        set.playTogether(fade, trans);

        return set;
    }

    @Retention(SOURCE)
    @IntDef({
            AnimationType.NONE,
            AnimationType.SLIDE_SIDE
    })
    @interface AnimationType {
        int NONE = 0;
        int SLIDE_SIDE = 1;
    }

    interface OnMeasuredCallback {
        void onMeasured(View view);
    }
}

