package com.power2sme.android.utilities.customviews;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by power2sme on 11/08/15.
 */
public class MyAppBarLayoutBehavior extends AppBarLayout.ScrollingViewBehavior
{

    private View layout;

    public MyAppBarLayoutBehavior() {
    }

    public MyAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        boolean result = super.onDependentViewChanged(parent, child, dependency);
        if (layout != null) {
            layout.setPadding(layout.getPaddingLeft(), layout.getPaddingTop(), layout.getPaddingRight(), layout.getTop());
        }
        return result;
    }

    public void setLayout(View layout) {
        this.layout = layout;
    }
}
