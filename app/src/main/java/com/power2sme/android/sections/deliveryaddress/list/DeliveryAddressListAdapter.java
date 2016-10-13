package com.power2sme.android.sections.deliveryaddress.list;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.power2sme.android.R;
import com.power2sme.android.entities.DeliveryAddress;

import java.util.List;

public class DeliveryAddressListAdapter extends BaseAdapter
{
	private static final String TAG="DealsFragment";
	private SparseBooleanArray checkedBoxes; 
	List<DeliveryAddress> deliveryAddressList;
	Context context;
	boolean isCheckboxVisible;
	DeliveryAddressListener deliveryAddressListener;
	public interface DeliveryAddressListener
	{
		void onDeleteDeliveryAddressClicked(DeliveryAddress deliveryAddress);
		void onEditDeliveryAddressClicked(DeliveryAddress deliveryAddress);
	}
	public void setDeliveryAddressListener(DeliveryAddressListener deliveryAddressListener)
	{
		this.deliveryAddressListener=deliveryAddressListener;
	}
	public DeliveryAddressListAdapter(Context context, List<DeliveryAddress> deliveryAddressList, boolean isCheckboxVisible)
	{
		this.deliveryAddressList=deliveryAddressList;
		this.context=context;
		this.isCheckboxVisible=isCheckboxVisible;
		checkedBoxes = new SparseBooleanArray();
	}
	@Override
	public int getCount() 
	{
		return deliveryAddressList.size();
	}
	@Override
	public DeliveryAddress getItem(int position) 
	{
		return deliveryAddressList.get(position);
	}
	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	private class ViewHolder
	{
		TextView TextView_firstAddress;
		CheckBox CheckBox_selectedDeliveryAddress;
		TextView TextView_secondAddress;
		TextView TextView_cityPostCode;
		ImageButton ImageButton_editShippingAddress;
		ImageButton ImageButton_deleteShippingAddress;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder viewHolder;
		if(convertView==null)
		{
			convertView=LayoutInflater.from(context).inflate(R.layout.delivery_address_list_item, null);
			viewHolder=new ViewHolder();
			
			viewHolder.TextView_firstAddress = (TextView)convertView.findViewById(R.id.TextView_firstAddress);
			viewHolder.CheckBox_selectedDeliveryAddress = (CheckBox)convertView.findViewById(R.id.CheckBox_selectedDeliveryAddress);
			viewHolder.TextView_secondAddress = (TextView)convertView.findViewById(R.id.TextView_secondAddress);
			viewHolder.TextView_cityPostCode = (TextView)convertView.findViewById(R.id.TextView_cityPostCode);
			viewHolder.ImageButton_editShippingAddress = (ImageButton)convertView.findViewById(R.id.ImageButton_editShippingAddress);
			viewHolder.ImageButton_deleteShippingAddress = (ImageButton)convertView.findViewById(R.id.ImageButton_deleteShippingAddress);
			
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder=(ViewHolder)convertView.getTag();
		}
		
		if(isCheckboxVisible) {
			viewHolder.CheckBox_selectedDeliveryAddress.setVisibility(CheckBox.VISIBLE);
			viewHolder.ImageButton_editShippingAddress.setVisibility(ImageButton.GONE);
			viewHolder.ImageButton_deleteShippingAddress.setVisibility(ImageButton.GONE);
		}
		else {
			viewHolder.CheckBox_selectedDeliveryAddress.setVisibility(CheckBox.GONE);
			viewHolder.ImageButton_editShippingAddress.setVisibility(ImageButton.VISIBLE);
			viewHolder.ImageButton_deleteShippingAddress.setVisibility(ImageButton.VISIBLE);
		}
			
		
		viewHolder.CheckBox_selectedDeliveryAddress.setChecked(checkedBoxes.get(position, false));
		
		if(position%2==0)
		{
			convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		else
		{
			convertView.setBackgroundColor(context.getResources().getColor(R.color.ulternate_row_color));
		}
		
		final DeliveryAddress deliveryAddress = getItem(position);
		
		viewHolder.CheckBox_selectedDeliveryAddress.setVisibility(CheckBox.VISIBLE);

		viewHolder.TextView_firstAddress.setText(deliveryAddress.getSmeAddress());
		viewHolder.TextView_secondAddress.setText(deliveryAddress.getSmeAddress2());
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

		viewHolder.TextView_cityPostCode.setText(str);

		viewHolder.ImageButton_editShippingAddress.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(deliveryAddressListener!=null)
					deliveryAddressListener.onEditDeliveryAddressClicked(deliveryAddress);
			}
		});

		viewHolder.ImageButton_deleteShippingAddress.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(deliveryAddressListener!=null)
					deliveryAddressListener.onDeleteDeliveryAddressClicked(deliveryAddress);
			}
		});

		return convertView;
	}
	public void setSelection(int position, boolean flag)
	{
		checkedBoxes.clear();
		checkedBoxes.put(position, flag);
	}
}
