package com.power2sme.android.sections;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.power2sme.android.sections.activities.BaseAppCompatActivity;

public class SuperListFragment  extends ListFragment
{
	Bundle fragmentDataBundle;
	public BaseAppCompatActivity baseActivity;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		baseActivity=(BaseAppCompatActivity)getActivity();
	}
	
	public Bundle getFragmentDataBundle() 
	{
		return fragmentDataBundle;
	}

	public void setFragmentDataBundle(Bundle fragmentDataBundle) 
	{
		this.fragmentDataBundle = fragmentDataBundle;
	}
}
