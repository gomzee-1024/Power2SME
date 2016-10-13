package com.power2sme.android.sections.deliveryaddress.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.power2sme.android.R;
import com.power2sme.android.entities.DeliveryAddress;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAddressesRecyclerAdapter  extends RecyclerView.Adapter<DeliveryAddressesRecyclerAdapter.ViewHolder> 
{
    public ArrayList<DeliveryAddress> data;
    private DeliveryAddressesListener deliveryAddressesListener;
    private Context context;
    public interface DeliveryAddressesListener
    {
        void onDeleteDeliveryAddressClicked(DeliveryAddress deliveryAddress);
        void onEditDeliveryAddressClicked(DeliveryAddress deliveryAddress);
    }
    public void setDeliveryAddressesListener(DeliveryAddressesListener deliveryAddressesListener)
    {
    	this.deliveryAddressesListener=deliveryAddressesListener;
    }
    public DeliveryAddressesRecyclerAdapter(ArrayList<DeliveryAddress> data) 
    {
        this.data = data;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
    	context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.delivery_address_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
    	final DeliveryAddress deal = data.get(position);
		if(deal!=null)
		{
			if(position%2==0)
			{
				holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
			}
			else
			{
				holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.lightGrayBackground));
			}
			
			final DeliveryAddress deliveryAddress = getItem(position);
			
			holder.TextView_firstAddress.setText(deliveryAddress.getSmeAddress());
			holder.TextView_secondAddress.setText(deliveryAddress.getSmeAddress2());
            String str="";
            boolean adSeparatorFlag=false;
            if(deliveryAddress.getSmeCity()!=null && deliveryAddress.getSmeCity().length()>0)
            {
                str = deliveryAddress.getSmeCity();
                adSeparatorFlag=true;
            }

            if(deliveryAddress.getSmeState()!=null && deliveryAddress.getSmeState().length()>0)
            {
                if(adSeparatorFlag)
                    str=str+", ";
                str = str+ deliveryAddress.getSmeState();
            }

            if(deliveryAddress.getSmePostCode()!=null && deliveryAddress.getSmePostCode().length()>0)
            {
                if(adSeparatorFlag)
                    str=str+", ";
                str = str + deliveryAddress.getSmePostCode();
            }

			holder.TextView_cityPostCode.setText(str);

            holder.ImageButton_editShippingAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deliveryAddressesListener != null)
                        deliveryAddressesListener.onEditDeliveryAddressClicked(deliveryAddress);
                }
            });

            holder.ImageButton_deleteShippingAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deliveryAddressesListener != null)
                        deliveryAddressesListener.onDeleteDeliveryAddressClicked(deliveryAddress);
                }
            });

//			holder.itemView.setOnClickListener(new OnClickListener() 
//			{
//				@Override
//				public void onClick(View v) 
//				{
//				}
//			});
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

    public DeliveryAddress getItem(int position) 
    {
        return data.get(position);
    }
    
    public void add(DeliveryAddress deal) 
    {
        insert(deal, data.size());
    }

    public void insert(DeliveryAddress deal, int position) 
    {
        data.add(position, deal);
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

    public void addAll(List<DeliveryAddress> dealArrayList) 
    {
        int startIndex = data.size();
        data.addAll(startIndex, dealArrayList);
        notifyItemRangeInserted(startIndex, dealArrayList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
    	private TextView TextView_firstAddress;
    	private CheckBox CheckBox_selectedDeliveryAddress;
    	private TextView TextView_secondAddress;
    	private TextView TextView_cityPostCode;
        private ImageButton ImageButton_editShippingAddress;
        private ImageButton ImageButton_deleteShippingAddress;

        public ViewHolder(View itemView) 
        {
            super(itemView);
            TextView_firstAddress = (TextView)itemView.findViewById(R.id.TextView_firstAddress);
			CheckBox_selectedDeliveryAddress = (CheckBox)itemView.findViewById(R.id.CheckBox_selectedDeliveryAddress);
			CheckBox_selectedDeliveryAddress.setVisibility(CheckBox.GONE);
            TextView_secondAddress = (TextView)itemView.findViewById(R.id.TextView_secondAddress);
			TextView_cityPostCode = (TextView)itemView.findViewById(R.id.TextView_cityPostCode);
            ImageButton_editShippingAddress = (ImageButton)itemView.findViewById(R.id.ImageButton_editShippingAddress);
            ImageButton_deleteShippingAddress = (ImageButton)itemView.findViewById(R.id.ImageButton_deleteShippingAddress);
        }
    }
}
