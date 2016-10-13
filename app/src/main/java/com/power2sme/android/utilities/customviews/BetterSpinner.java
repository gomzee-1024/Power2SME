package com.power2sme.android.utilities.customviews;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.power2sme.android.R;

import java.util.Calendar;

/**
 * Created by power2sme on 22/12/15.
 */
public class BetterSpinner extends AppCompatAutoCompleteTextView implements AdapterView.OnItemClickListener
{
    private static final int MAX_CLICK_DURATION = 800;
    private long startClickTime;
    private boolean isPopup;

    public static BetterSpinner activeSpinner;

    public BetterSpinner(Context context)
    {
        super(context);
        setOnItemClickListener(this);
    }

    public BetterSpinner(Context arg0, AttributeSet arg1)
    {
        super(arg0, arg1);
        setOnItemClickListener(this);
    }

    public BetterSpinner(Context arg0, AttributeSet arg1, int arg2)
    {
        super(arg0, arg1, arg2);
        setOnItemClickListener(this);
    }

    @Override
    public boolean enoughToFilter()
    {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,Rect previouslyFocusedRect)
    {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused)
        {
            if(getFilter()!=null)
            {
                performFiltering("", 0);
            }
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
            setKeyListener(null);
            dismissDropDown();
        }
        else
        {
            isPopup = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                startClickTime = Calendar.getInstance().getTimeInMillis();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (clickDuration < MAX_CLICK_DURATION)
                {
                    BetterSpinner.activeSpinner=this;
                    if (isPopup)
                    {
                        dismissDropDown();
                        isPopup = false;
                    }
                    else
                    {
                        if(getAdapter()!=null && getAdapter().getCount()>0)//check for empty
                        {
                            requestFocus();
                            if(isActiveSpinner())
                            {
                                showDropDown();
                            }
                            isPopup = true;
                            if(onEmptyBetterSpinnerListener!=null)
                                onEmptyBetterSpinnerListener.onEmpty(false);
                        }
                        else
                        {
                            if(onEmptyBetterSpinnerListener!=null)
                                onEmptyBetterSpinnerListener.onEmpty(true);
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private boolean isActiveSpinner()
    {
        if(BetterSpinner.activeSpinner==this)
        {
            return true;
        }
        return false;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        isPopup = false;
        if(onBetterSpinnerItemClickedListener!=null)
        {
            Object obj = adapterView.getItemAtPosition(position);
            onBetterSpinnerItemClickedListener.onItemClicked(obj, position);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom)
    {
        Drawable dropdownIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_expand_more_black_18dp);
        if (dropdownIcon != null)
        {
            right = dropdownIcon;
            right.mutate().setAlpha(128);
        }
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    OnEmptyBetterSpinnerListener onEmptyBetterSpinnerListener;
    public void setOnEmptyBetterSpinnerListener(OnEmptyBetterSpinnerListener onEmptyBetterSpinnerListener)
    {
        this.onEmptyBetterSpinnerListener=onEmptyBetterSpinnerListener;
    }
    public interface OnEmptyBetterSpinnerListener
    {
        void onEmpty(boolean isEmpty);
    }


    OnBetterSpinnerItemClickedListener onBetterSpinnerItemClickedListener;
    public void setOnBetterSpinnerItemClickedListener(OnBetterSpinnerItemClickedListener onBetterSpinnerItemClickedListener)
    {
        this.onBetterSpinnerItemClickedListener=onBetterSpinnerItemClickedListener;
    }
    public interface OnBetterSpinnerItemClickedListener
    {
        void onItemClicked(Object selectedObject, int position);
    }
}
