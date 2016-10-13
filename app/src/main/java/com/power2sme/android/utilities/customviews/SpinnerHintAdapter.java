package com.power2sme.android.utilities.customviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.power2sme.android.R;

import java.util.List;

public class SpinnerHintAdapter<T> extends ArrayAdapter<T>
{
	public SpinnerHintAdapter(Context theContext, List<T> objects)
    {
        super(theContext, R.layout.spinner_list_item, android.R.id.text1, objects);
    }
 
    @Override
    public int getCount() 
    {
        return super.getCount();
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
		View view = super.getView(position, convertView, parent);
		TextView checkedTextView = (TextView) view.findViewById(android.R.id.text1);
    	checkedTextView.setTextColor(getContext().getResources().getColor(R.color.black));
    	return view;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) 
    {
    	View view = super.getDropDownView(position, convertView, parent);
    	TextView checkedTextView = (TextView) view.findViewById(android.R.id.text1);
        checkedTextView.setPadding(0, 0, 0, 30);
    	checkedTextView.setTextColor(getContext().getResources().getColor(R.color.black));
    	return view;
    }
}
