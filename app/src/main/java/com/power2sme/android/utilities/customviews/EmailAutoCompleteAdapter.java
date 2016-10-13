package com.power2sme.android.utilities.customviews;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EmailAutoCompleteAdapter  extends ArrayAdapter<String> implements Filterable
{
    private ArrayList<String> resultList;
    Activity context;
    public EmailAutoCompleteAdapter(Activity context, int textViewResourceId)
    {
        super(context, textViewResourceId);
        this.context=context;
        resultList=new ArrayList<String>();
    }

    @Override
    public int getCount() 
    {
        return resultList.size();
    }

    @Override
    public String getItem(int index) 
    {
        return resultList.get(index);
    }

    @Override
    public Filter getFilter() 
    {
        Filter filter = new Filter() 
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) 
            {
                FilterResults filterResults = new FilterResults();
//                if (constraint != null)
//                {
                    // Retrieve the autocomplete results.
                    resultList = autocomplete(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
//                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) 
            {
                if (results != null && results.count > 0) 
                {
                    notifyDataSetChanged();
                }
                else 
                {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
    
    private ArrayList<String> autocomplete(String input)
    {
    	ArrayList<String> resultList = new ArrayList<String>();
        Set<String> resultSet = new HashSet<String>();
    	AccountManager am = AccountManager.get(context);
    	Account [] acc = am.getAccounts();

    	for(Account account:acc)
    	{
            if(input!=null && input.length()>0)
            {
                if(account.name.indexOf(input)!=-1 && android.util.Patterns.EMAIL_ADDRESS.matcher(account.name).matches())
                    resultSet.add(account.name);
            }
            else if(android.util.Patterns.EMAIL_ADDRESS.matcher(account.name).matches())
            {
                resultSet.add(account.name);
            }
    	}

        if(resultSet.size()>0)
        {
            Iterator<String> itr = resultSet.iterator();
            while(itr.hasNext())
            {
                resultList.add(itr.next());
            }
        }
        return resultList;
    }
}
