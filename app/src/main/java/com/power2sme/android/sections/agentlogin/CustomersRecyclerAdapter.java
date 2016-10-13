package com.power2sme.android.sections.agentlogin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.power2sme.android.R;
import com.power2sme.android.entities.v3.Customer;

import java.util.ArrayList;

public class CustomersRecyclerAdapter extends RecyclerView.Adapter<CustomersRecyclerAdapter.ViewHolder>
{
    public ArrayList<Customer> data;
    private OnItemClickListener onItemClickListener;
    private Context context;
    public interface OnItemClickListener
    {
    	void onActOnBehalf(Customer customer);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
    	this.onItemClickListener=onItemClickListener;
    }
    public CustomersRecyclerAdapter(ArrayList<Customer> data)
    {
        this.data = data;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
    	context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.customers_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
    	final Customer customer = data.get(position);
		if(customer!=null)
		{
			holder.TextView_companyName.setText(customer.getCompany_name());
			holder.TextView_smeId.setText(customer.getSmeId());
//			holder.TextView_date.setText(customer.getDate());
			holder.TextView_email.setText(customer.getEmail());
			holder.TextView_phone.setText(customer.getPhone());
			holder.Button_actOnBehalf.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if(onItemClickListener!=null)
					{
						onItemClickListener.onActOnBehalf(customer);
					}
				}
			});
		}
    }

    @Override
    public int getItemCount() 
    {
        return data.size();
    }

    @Override
    public long getItemId(int position) 
    {
        return position;
    }

    public Customer getItem(int position)
    {
        return data.get(position);
    }
    
    public void add(Customer customer)
    {
        insert(customer, data.size());
    }

    public void insert(Customer customer, int position)
    {
        data.add(position, customer);
        notifyItemInserted(position);
    }

    public void removeViaSwipe(int position) 
    {
        data.remove(position);

        //Because item is already out of view via swipe do not animate this with default animator
        notifyDataSetChanged();
    }

    public void clear() 
    {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(ArrayList<Customer> customers)
    {
        int startIndex = data.size();
        data.addAll(startIndex, customers);
        notifyItemRangeInserted(startIndex, customers.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
    	public TextView TextView_companyName;
    	public TextView TextView_smeId;
//    	public TextView TextView_date;
    	public TextView TextView_email;
		public TextView TextView_phone;
    	public Button Button_actOnBehalf;

    	
        public ViewHolder(View itemView) 
        {
            super(itemView);
			TextView_companyName = (TextView)itemView.findViewById(R.id.TextView_companyName);
			TextView_smeId = (TextView)itemView.findViewById(R.id.TextView_smeId);
//			TextView_date = (TextView)itemView.findViewById(R.id.TextView_date);
			TextView_email = (TextView)itemView.findViewById(R.id.TextView_email);
			TextView_phone = (TextView)itemView.findViewById(R.id.TextView_phone);
			Button_actOnBehalf = (Button)itemView.findViewById(R.id.Button_actOnBehalf);
        }
    }
}
