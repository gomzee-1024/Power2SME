package com.power2sme.android.sections.myorders.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power2sme.android.R;
import com.power2sme.android.entities.SalesOrder;
import com.power2sme.android.entities.SalesOrderItem;
import com.power2sme.android.entities.tracking.Order;

import java.util.ArrayList;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class OrdersRecyclerAdapter  extends RecyclerView.Adapter<OrdersRecyclerAdapter.ViewHolder> 
{
    public ArrayList<SalesOrder> data;
//    public DisplayImageOptions options;
    private OnSalesOrderClickListener onItemClickListener;
    public interface OnSalesOrderClickListener
    {
    	void onReOrderButtonClick(SalesOrder salesOrder);
    	void onViewShipmentLocationClicked(SalesOrder salesOrder, Order orderTracking);
    }
    public void setOnSalesOrderClickListener(OnSalesOrderClickListener onItemClickListener)
    {
    	this.onItemClickListener=onItemClickListener;
    }
    public OrdersRecyclerAdapter(ArrayList<SalesOrder> data) 
    {
        this.data = data;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list_item, parent, false);
        if(viewType==0)
            view.setBackgroundColor(parent.getContext().getResources().getColor(R.color.NewGray));
        else
            view.setBackgroundColor(parent.getContext().getResources().getColor(R.color.white));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
    	final SalesOrder salesOrder = data.get(position);
		if(salesOrder!=null)
		{
			holder.TextView_date.setText(salesOrder.getOrderDate());
			holder.TextView_orderId.setText(salesOrder.getOrderNo());
			holder.TextView_taxation.setText(salesOrder.getBillingType());
			holder.TextView_grandTotal.setText(salesOrder.getTotalAmt());
			
			//holder.TextView_status.setText(Utils.getOrderStatus(holder.itemView.getContext(), salesOrder.getOrderStatus()));
            holder.TextView_status.setText(salesOrder.getStatus());
			
			holder.LinearLayout_ordersItems.removeAllViews();
			if(salesOrder.getSalesLine().size()>0)
			{
				for(SalesOrderItem salesOrderItem:salesOrder.getSalesLine())
				{
					View rfqListItemView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.orders_item_list_item, null);

                    TextView TextView_item = (TextView)rfqListItemView.findViewById(R.id.TextView_item);
                    TextView TextView_brand = (TextView)rfqListItemView.findViewById(R.id.TextView_brand);
                    TextView TextView_quantity = (TextView)rfqListItemView.findViewById(R.id.TextView_quantity);
                    TextView TextView_unit = (TextView)rfqListItemView.findViewById(R.id.TextView_unit);
                    TextView TextView_unitPrice = (TextView)rfqListItemView.findViewById(R.id.TextView_unitPrice);
                    TextView TextView_itemPrice = (TextView)rfqListItemView.findViewById(R.id.TextView_itemPrice);

                    TextView_item.setText(salesOrderItem.getDescription());
                    TextView_brand.setText("");
                    TextView_quantity.setText(salesOrderItem.getQty());
                    TextView_unit.setText(salesOrderItem.getUom());
                    TextView_unitPrice.setText("(@"+salesOrderItem.getItemRate()+")");
                    TextView_itemPrice.setText("INR "+salesOrderItem.getItemAmt());
//
//
//					TextView TextView_item = (TextView)rfqListItemView.findViewById(R.id.TextView_item);
//					TextView TextView_total = (TextView)rfqListItemView.findViewById(R.id.TextView_total);
//					StylesManager.getInstance(holder.itemView.getContext()).setTextViewStyle(TextView_item, StyleTypes.TextView_body1);
//					StylesManager.getInstance(holder.itemView.getContext()).setTextViewStyle(TextView_total, StyleTypes.TextView_body1);
//
//					String str = salesOrderItem.getDescription();
//					str=str+"&nbsp;&nbsp;&nbsp;<B><I><font color='#727272'>"+salesOrderItem.getQty()+salesOrderItem.getUom()+"</font></I></B>&nbsp;(@"+holder.itemView.getContext().getString(R.string.inr)+salesOrderItem.getItemRate()+")";
//					TextView_item.setText(Html.fromHtml(str));
//					TextView_total.setText(holder.itemView.getContext().getString(R.string.inr)+salesOrderItem.getItemAmt());
					holder.LinearLayout_ordersItems.addView(rfqListItemView);
				}
			}
			
			holder.Button_reOrder.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					onItemClickListener.onReOrderButtonClick(salesOrder);
				}
			});

            if(BuyingOrdersFragment.trackingStatusMap!=null && BuyingOrdersFragment.trackingStatusMap.containsKey(salesOrder.getOrderNo()))
            {
                holder.TextView_status.setText("Shipped");
                holder.Button_viewShipmentLocation.setVisibility(Button.VISIBLE);
                holder.Button_viewShipmentLocation.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        onItemClickListener.onViewShipmentLocationClicked(salesOrder, BuyingOrdersFragment.trackingStatusMap.get(salesOrder.getOrderNo()));
                    }
                });
            }
            else
            {
                holder.Button_viewShipmentLocation.setVisibility(Button.GONE);
            }
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

    public SalesOrder getItem(int position) 
    {
        return data.get(position);
    }
    
    public void add(SalesOrder SalesOrder) 
    {
        insert(SalesOrder, data.size());
    }

    public void insert(SalesOrder SalesOrder, int position) 
    {
        data.add(position, SalesOrder);
        notifyItemInserted(position);
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        return position%2;
    }

    public void removeViaSwipe(int position)
    {
        data.remove(position);
        notifyDataSetChanged();
    }

    public void clear() 
    {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(ArrayList<SalesOrder> SalesOrderArrayList) 
    {
        int startIndex = data.size();
        data.addAll(startIndex, SalesOrderArrayList);
        notifyItemRangeInserted(startIndex, SalesOrderArrayList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
    	public TextView TextView_date;
    	public TextView TextView_orderId;
    	public TextView TextView_taxation;
    	public TextView TextView_grandTotal;
    	public TextView TextView_status;
    	public Button Button_reOrder;
    	public Button Button_viewShipmentLocation;
    	public LinearLayout LinearLayout_ordersItems;
    	public TextView TextView_dateLabel;
    	public TextView TextView_orderIdLabel;
    	public TextView TextView_taxationLabel;
    	public TextView TextView_grandTotalLabel;
    	public TextView TextView_statusLabel;
        public ViewHolder(View itemView) 
        {
            super(itemView);
            TextView_date = (TextView)itemView.findViewById(R.id.TextView_date);
        	TextView_orderId = (TextView)itemView.findViewById(R.id.TextView_orderId);
        	TextView_taxation = (TextView)itemView.findViewById(R.id.TextView_taxation);
        	TextView_grandTotal = (TextView)itemView.findViewById(R.id.TextView_grandTotal);
        	TextView_status = (TextView)itemView.findViewById(R.id.TextView_status);
        	Button_reOrder = (Button)itemView.findViewById(R.id.Button_reOrder);
        	Button_viewShipmentLocation = (Button)itemView.findViewById(R.id.Button_viewShipmentLocation);
        	LinearLayout_ordersItems = (LinearLayout)itemView.findViewById(R.id.LinearLayout_ordersItems);
        	TextView_dateLabel = (TextView)itemView.findViewById(R.id.TextView_dateLabel);
        	TextView_orderIdLabel = (TextView)itemView.findViewById(R.id.TextView_orderIdLabel);
        	TextView_taxationLabel = (TextView)itemView.findViewById(R.id.TextView_taxationLabel);
        	TextView_grandTotalLabel = (TextView)itemView.findViewById(R.id.TextView_grandTotalLabel);
        	TextView_statusLabel = (TextView)itemView.findViewById(R.id.TextView_statusLabel);
        }
    }
}
