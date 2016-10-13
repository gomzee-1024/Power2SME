package com.power2sme.android.sections.accountupdates;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.power2sme.android.R;
import com.power2sme.android.entities.Update;

import java.util.ArrayList;
import java.util.List;

public class UpdatesRecyclerAdapter  extends RecyclerView.Adapter<UpdatesRecyclerAdapter.ViewHolder> 
{
    public ArrayList<Update> data;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener
    {
    	void onItemClick(Update update);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
    	this.onItemClickListener=onItemClickListener;
    }
    public UpdatesRecyclerAdapter(ArrayList<Update> data) 
    {
        this.data = data;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.updates_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
    	holder.TextView_update.setText(getItem(position).getMessage());
		if(getItem(position).getOrderType().equals("RFQ") || getItem(position).getOrderType().equals("Order"))
		{
			holder.ImageView_rowActionIndicatorArrow.setVisibility(ImageView.VISIBLE);
		}
		else
		{
			holder.ImageView_rowActionIndicatorArrow.setVisibility(ImageView.GONE);
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

    public Update getItem(int position) 
    {
        return data.get(position);
    }
    
    public void add(Update rfqItem) 
    {
        insert(rfqItem, data.size());
    }

    public void insert(Update rfqItem, int position) 
    {
        data.add(position, rfqItem);
        notifyItemInserted(position);
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

    public void addAll(List<Update> rssItemArrayList) 
    {
        int startIndex = data.size();
        data.addAll(startIndex, rssItemArrayList);
        notifyItemRangeInserted(startIndex, rssItemArrayList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
    	public TextView TextView_update;
    	public ImageView ImageView_rowActionIndicatorArrow;
    	
        public ViewHolder(View itemView) 
        {
            super(itemView);
            
            TextView_update = (TextView)itemView.findViewById(R.id.TextView_update);
        	ImageView_rowActionIndicatorArrow = (ImageView)itemView.findViewById(R.id.ImageView_rowActionIndicatorArrow);
        	itemView.setOnClickListener(this);
        }

		@Override
		public void onClick(View v) 
		{
			int position = getPosition();
			Update update = getItem(position);
			onItemClickListener.onItemClick(update);			
		}
    }
}
