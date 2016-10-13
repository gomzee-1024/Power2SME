package com.power2sme.android;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class ProgressDialogFragment extends DialogFragment 
{
    public ProgressDialogFragment() 
    {
        // Empty constructor required for DialogFragment
    }
    public static ProgressDialogFragment newInstance(int title) 
    {
    	ProgressDialogFragment frag = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.progress_dialog_fragment, container);
        return view;
    }
}
