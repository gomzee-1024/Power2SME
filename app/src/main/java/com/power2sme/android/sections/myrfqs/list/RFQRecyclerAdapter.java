package com.power2sme.android.sections.myrfqs.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power2sme.android.R;
import com.power2sme.android.entities.v3.RFQLineItem_v3;
import com.power2sme.android.entities.v3.Wishlist_v3;

import java.util.ArrayList;
import java.util.List;

public class RFQRecyclerAdapter  extends RecyclerView.Adapter<RFQRecyclerAdapter.ViewHolder> 
{
    public ArrayList<Wishlist_v3> data;
    private OnItemClickListener onItemClickListener;
    private Context context;
    public interface OnItemClickListener
    {
    	void onAcceptQuoteClick(Wishlist_v3 rfq);
    	void onUploadPOClick(Wishlist_v3 rfq);
		void onStartCallWithKAM();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
    	this.onItemClickListener=onItemClickListener;
    }
    public RFQRecyclerAdapter(ArrayList<Wishlist_v3> data)
    {
        this.data = data;
        setHasStableIds(true);
    }

	@Override
	public int getItemViewType(int position) {
		super.getItemViewType(position);
		return position%2;
	}

	@Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
		View view;
    	context = parent.getContext();
		if(viewType==0)
			view = LayoutInflater.from(context).inflate(R.layout.buying_rfq_list_item_even, parent, false);
		else
		 view = LayoutInflater.from(context).inflate(R.layout.buying_rfq_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
    	final Wishlist_v3 rfq = data.get(position);
		if(rfq!=null)
		{
			holder.TextView_date.setText(rfq.getRfqDate());
			holder.TextView_orderId.setText(rfq.getRfqno());
			holder.TextView_status.setText(rfq.getRfqStatus());
			holder.TextView_billing.setText(holder.itemView.getContext().getString(R.string.inr)+rfq.getGrandTotal());

			if(rfq.getTaxationPreference()!=null && rfq.getTaxationPreference().getValue()!=null && rfq.getTaxationPreference().getValue().length()>0 )
			{
				holder.TextView_taxation.setText(rfq.getTaxationPreference().getValue());
			}
			else
			{
				holder.TextView_taxation.setText("Not Specified");
			}

			
//			if(rfq.getType()==1 || rfq.getType()==2 || rfq.getType()==3)//rfq
//			{
				holder.TextView_orderIdLabel.setText(context.getString(R.string.myrfqs_label_rfqid));
				holder.TextView_dateLabel.setText(context.getString(R.string.myrfqs_label_rfqdate));
//			}
//			else//quote
//			{
//				holder.TextView_orderIdLabel.setText(context.getString(R.string.myrfqs_label_quoteid));
//				holder.TextView_dateLabel.setText(context.getString(R.string.myrfqs_label_quotedate));
//			}
			
			if(rfq.getRfqCode() == 4)
			{
				holder.Button_accept.setVisibility(Button.VISIBLE);
				holder.Button_accept.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						onItemClickListener.onAcceptQuoteClick(rfq);
					}
				});
			}
			else
			{
				holder.Button_accept.setVisibility(Button.GONE);
			}
			
			if(rfq.getRfqCode()==5)
			{
				holder.Button_uploadPO.setVisibility(Button.VISIBLE);
				holder.Button_uploadPO.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						onItemClickListener.onUploadPOClick(rfq);
					}
				});
			}
			else
			{
				holder.Button_uploadPO.setVisibility(Button.GONE);
			}
			
			holder.LinearLayout_rfqItems.removeAllViews();
			if(rfq.getRfqLineList()!=null && rfq.getRfqLineList().size()>0)
			{
				for(RFQLineItem_v3 rfqItem:rfq.getRfqLineList())
				{
					if(rfqItem.getSku()==null)
					{
						continue;
					}
					View rfqListItemView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.buying_rfq_items_list_item, null);
					TextView TextView_item_line1 = (TextView)rfqListItemView.findViewById(R.id.TextView_item_line1);
					TextView TextView_item_line2 = (TextView)rfqListItemView.findViewById(R.id.TextView_item_line2);
					TextView TextView_total = (TextView)rfqListItemView.findViewById(R.id.TextView_total);

					String itemDescription="";
					if(rfqItem.getSku().getLongDescription()!=null)
					{
						itemDescription = rfqItem.getSku().getLongDescription();
					}
					else
					{
						itemDescription = rfqItem.getRemarks();
					}
					if(rfq.getRfqno()!=null && rfq.getRfqno().trim().length()>0)
					{
						//String str = itemDescription+"&nbsp;&nbsp;&nbsp;<B><I><font color='#727272'>"+rfqItem.getQuantity() +" "+rfqItem.getUom().trim()+"</font></I></B>&nbsp;(@ "+holder.itemView.getContext().getString(R.string.inr)+ rfqItem.getUnitPrice()+")";

						TextView_item_line1.setText(Html.fromHtml(itemDescription));
						TextView_item_line2.setText(Html.fromHtml("<I><font color='#727272'>"+rfqItem.getQuantity() +" "+rfqItem.getUom().trim()+"</font></I></B>&nbsp;@ "+holder.itemView.getContext().getString(R.string.inr)+ rfqItem.getUnitPrice()));
						TextView_item_line2.setVisibility(TextView.VISIBLE);
						TextView_total.setText(holder.itemView.getContext().getString(R.string.inr) + rfqItem.getItemPrice());
						holder.LinearLayout_rfqItems.addView(rfqListItemView);
					}
					else
					{
						TextView_item_line1.setText(Html.fromHtml(itemDescription));
						TextView_item_line2.setVisibility(TextView.GONE);
						TextView_total.setText("");
						holder.LinearLayout_rfqItems.addView(rfqListItemView);
					}
				}
			}
			holder.Button_callus.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					if(onItemClickListener!=null)
					{
						onItemClickListener.onStartCallWithKAM();
					}
				}
			});

			if(rfq.getRfqno()!=null && rfq.getRfqno().trim().length()>0)
			{
				holder.LinearLayout_rfqIdParent.setVisibility(LinearLayout.VISIBLE);
				holder.LinearLayout_taxPrefsParent.setVisibility(LinearLayout.VISIBLE);
				holder.LinearLayout_statusParent.setVisibility(LinearLayout.VISIBLE);
				holder.TextView_interestedMsg.setVisibility(TextView.GONE);
			}
			else
			{
				holder.LinearLayout_rfqIdParent.setVisibility(LinearLayout.GONE);
				holder.LinearLayout_taxPrefsParent.setVisibility(LinearLayout.GONE);
				holder.LinearLayout_statusParent.setVisibility(LinearLayout.GONE);
				holder.TextView_interestedMsg.setVisibility(TextView.VISIBLE);
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

    public Wishlist_v3 getItem(int position)
    {
        return data.get(position);
    }
    
    public void add(Wishlist_v3 rfqItem)
    {
        insert(rfqItem, data.size());
    }

    public void insert(Wishlist_v3 rfqItem, int position)
    {
        data.add(position, rfqItem);
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

    public void addAll(List<Wishlist_v3> rssItemArrayList)
    {
        int startIndex = data.size();
        data.addAll(startIndex, rssItemArrayList);
        notifyItemRangeInserted(startIndex, rssItemArrayList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
    	public TextView TextView_orderId;
    	public TextView TextView_date;
    	public TextView TextView_taxation;
    	public TextView TextView_billing;
//    	public Button Button_requestRequote;
    	public Button Button_uploadPO;
    	public Button Button_accept;
    	public Button Button_callus;
    	public TextView TextView_status;
    	public LinearLayout LinearLayout_rfqItems;
    	public TextView TextView_orderIdLabel;
    	public TextView TextView_dateLabel;
    	public TextView TextView_taxationLabel;
    	public TextView TextView_billingLabel;
    	public TextView TextView_statusLabel;

		public LinearLayout LinearLayout_rfqIdParent;
		public LinearLayout LinearLayout_taxPrefsParent;
		public LinearLayout LinearLayout_statusParent;
		public TextView TextView_interestedMsg;
    	
    	
        public ViewHolder(View itemView) 
        {
            super(itemView);
            TextView_orderId = (TextView)itemView.findViewById(R.id.TextView_orderId);
        	TextView_date = (TextView)itemView.findViewById(R.id.TextView_date);
        	TextView_taxation = (TextView)itemView.findViewById(R.id.TextView_taxation);
        	TextView_billing = (TextView)itemView.findViewById(R.id.TextView_billing);
//        	Button_requestRequote = (Button)itemView.findViewById(R.id.Button_requestRequote);
        	Button_uploadPO = (Button)itemView.findViewById(R.id.Button_uploadPO);
        	Button_accept = (Button)itemView.findViewById(R.id.Button_accept);
        	Button_callus = (Button)itemView.findViewById(R.id.Button_callus);
        	TextView_status = (TextView)itemView.findViewById(R.id.TextView_status);
        	LinearLayout_rfqItems = (LinearLayout)itemView.findViewById(R.id.LinearLayout_rfqItems);
        	TextView_orderIdLabel = (TextView)itemView.findViewById(R.id.TextView_orderIdLabel);
        	TextView_dateLabel = (TextView)itemView.findViewById(R.id.TextView_dateLabel);
        	TextView_taxationLabel = (TextView)itemView.findViewById(R.id.TextView_taxationLabel);
        	TextView_billingLabel = (TextView)itemView.findViewById(R.id.TextView_billingLabel);
        	TextView_statusLabel = (TextView)itemView.findViewById(R.id.TextView_statusLabel);

			LinearLayout_rfqIdParent = (LinearLayout)itemView.findViewById(R.id.LinearLayout_rfqIdParent);
			LinearLayout_taxPrefsParent = (LinearLayout)itemView.findViewById(R.id.LinearLayout_taxPrefsParent);
			LinearLayout_statusParent = (LinearLayout)itemView.findViewById(R.id.LinearLayout_statusParent);
			TextView_interestedMsg = (TextView)itemView.findViewById(R.id.TextView_interestedMsg);
        }
    }
}
