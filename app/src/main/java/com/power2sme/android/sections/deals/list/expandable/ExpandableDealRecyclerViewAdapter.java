package com.power2sme.android.sections.deals.list.expandable;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.power2sme.android.R;
import com.power2sme.android.entities.DealItemPrice;
import com.power2sme.android.entities.v3.Deal_v3;
import com.power2sme.android.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tausif on 1/8/15.
 */
public class ExpandableDealRecyclerViewAdapter extends AbstractExpandableItemAdapter<ExpandableDealRecyclerViewAdapter.GroupViewHolder, ExpandableDealRecyclerViewAdapter.ChildViewHolder>
{
    public ArrayList<Deal_v3> data;
    private OnItemClickListener onItemClickListener;
    private Context context;
    public interface OnItemClickListener
    {
        void onDealSelected(Deal_v3 rfq);
        void onCustomerInterested(Deal_v3 deal);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener=onItemClickListener;
    }
    public ExpandableDealRecyclerViewAdapter(Context context, ArrayList<Deal_v3> data)
    {
        this.data = data;
        this.context=context;
        setHasStableIds(true);
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType)
    {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.deals_list_expandable_group_item, parent, false);
        return new GroupViewHolder(v);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType)
    {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.deals_list_expandable_child_item, parent, false);
        return new ChildViewHolder(v);
    }

    @Override
    public void onBindGroupViewHolder(GroupViewHolder groupViewHolder, int groupPosition, int viewType)
    {
        final Deal_v3 deal = data.get(groupPosition);
        groupViewHolder.TextView_DealId.setText(deal.getId());
        groupViewHolder.TextView_productName.setText(deal.getLongDescription());

        if(deal.getPriceSpace()!=null && deal.getPriceSpace().size()>0)
        {
            String minPrice = deal.getPriceSpace().get(0).getRatevisibletocustomer();
            String maxPrice = deal.getPriceSpace().get(deal.getPriceSpace().size()-1).getRatevisibletocustomer();
            groupViewHolder.TextView_startingRate.setText("Price/"+deal.getUom()+": "+groupViewHolder.itemView.getContext().getString(R.string.inr)+minPrice +" - "+groupViewHolder.itemView.getContext().getString(R.string.inr)+maxPrice);
        }

        if(deal!=null && deal.getSku()!=null && deal.getSku().getCategory()!=null && deal.getSku().getCategory().length()>0)
        {
            groupViewHolder.TextView_productDetails.setText(deal.getSku().getCategory());
            int resId = Utils.getCategoryDrawableResId(context, deal.getSku().getCategory());
            if(resId!=-1)
            {
                groupViewHolder.ImageView_categoryDrawable.setBackgroundResource(resId);
            }
            else
            {
                TextDrawable drawable = Utils.getCategoryDrawable(context, deal.getSku().getCategory());
                groupViewHolder.ImageView_categoryDrawable.setBackgroundDrawable(drawable);
            }
        }

        // mark as clickable
        groupViewHolder.itemView.setClickable(true);

        // set background resource (target view ID: container)
        final int expandState = groupViewHolder.getExpandStateFlags();

        if ((expandState & RecyclerViewExpandableItemManager.STATE_FLAG_IS_UPDATED) != 0) {
            int bgResId;
            boolean isExpanded;

            if ((expandState & RecyclerViewExpandableItemManager.STATE_FLAG_IS_EXPANDED) != 0) {
                bgResId = R.drawable.expandable_list_up_arrow;
                groupViewHolder.TextView_startingRate.setVisibility(TextView.GONE);
                groupViewHolder.LinearLayout_dealIdParent.setVisibility(LinearLayout.VISIBLE);
                isExpanded = true;
            } else {
                bgResId = R.drawable.expandable_list_down_arrow;
                groupViewHolder.TextView_startingRate.setVisibility(TextView.VISIBLE);
                groupViewHolder.LinearLayout_dealIdParent.setVisibility(LinearLayout.GONE);
                isExpanded = false;
            }

            Resources r = context.getResources();
            int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, r.getDisplayMetrics());
            groupViewHolder.ImageView_indicator.getLayoutParams().height=px;
            groupViewHolder.ImageView_indicator.getLayoutParams().width=px;
            groupViewHolder.ImageView_indicator.setBackgroundResource(bgResId);
//            groupViewHolder.mIndicator.setExpandedState(isExpanded, true);
        }
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int groupPosition, int childPosition, int viewType)
    {
        final Deal_v3 deal = data.get(groupPosition);
        if(deal!=null)
        {
//            childViewHolder.TextView_Product.setText(deal.getLongDescription());

            if(childViewHolder.LinearLayout_ratesContainer!=null)
            {
                childViewHolder.LinearLayout_ratesContainer.removeAllViews();
            }

            if(deal.getPriceSpace()!=null && deal.getPriceSpace().size()>0)
            {
                childViewHolder.TextView_rateLabel.setText(childViewHolder.itemView.getContext().getString(R.string.deals_label_rate)+"/"+deal.getUom()+": ");
                List<DealItemPrice> dealItemPriceList = deal.getPriceSpace();
                for(int i=0; i<dealItemPriceList.size();i++)
                {
                    DealItemPrice dealItemPrice = dealItemPriceList.get(i);

                    View dealsRatesItem = LayoutInflater.from(childViewHolder.itemView.getContext()).inflate(R.layout.deals_rates_item, null);
                    TextView TextView_rate=(TextView)dealsRatesItem.findViewById(R.id.TextView_rate);
                    TextView TextView_minQtyMsg=(TextView)dealsRatesItem.findViewById(R.id.TextView_minQtyMsg);
                    TextView_rate.setText(childViewHolder.itemView.getContext().getString(R.string.inr)+ Utils.getCommaSeparatedNumber(dealItemPrice.getRatevisibletocustomer()));
                    TextView_minQtyMsg.setText(" for "+dealItemPrice.getMinqty()+" "+deal.getUom()+" to "+dealItemPrice.getMaxqty()+" "+deal.getUom());
                    childViewHolder.LinearLayout_ratesContainer.addView(dealsRatesItem);
                }
            }

            View locationView = LayoutInflater.from(childViewHolder.itemView.getContext()).inflate(R.layout.deals_rates_item, null);
            locationView.setPadding(0, 10, 0, 0);
            ((TextView)locationView.findViewById(R.id.TextView_minQtyMsg)).setText("Ex- "+deal.getLocationValue());
            childViewHolder.LinearLayout_ratesContainer.addView(locationView);

            View remarksView = LayoutInflater.from(childViewHolder.itemView.getContext()).inflate(R.layout.deals_rates_item, null);
            TextView remarksTextView = ((TextView)remarksView.findViewById(R.id.TextView_minQtyMsg));
            remarksTextView.setText(deal.getRemarks());
            childViewHolder.LinearLayout_ratesContainer.addView(remarksView);

            childViewHolder.Button_po.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(onItemClickListener!=null)
                        onItemClickListener.onDealSelected(deal);
                }
            });

            childViewHolder.Button_interested.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(onItemClickListener!=null)
                        onItemClickListener.onCustomerInterested(deal);
                }
            });
        }
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(GroupViewHolder groupViewHolder, int groupPosition, int x, int y, boolean expand) {

        if (!(groupViewHolder.itemView.isEnabled() && groupViewHolder.itemView.isClickable()))
        {
            return false;
        }
        return true;
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder implements ExpandableItemViewHolder
    {
        private int mExpandStateFlags;
        private TextView TextView_DealId;
        private LinearLayout LinearLayout_dealIdParent;
        private ImageView ImageView_categoryDrawable;
        private TextView TextView_productName;
        private TextView TextView_productDetails;
        private TextView TextView_startingRate;
        private ImageView ImageView_indicator;

        public GroupViewHolder(View itemView)
        {
            super(itemView);
            LinearLayout_dealIdParent = (LinearLayout)itemView.findViewById(R.id.LinearLayout_dealIdParent);
            TextView_DealId=(TextView)itemView.findViewById(R.id.TextView_DealId);
            ImageView_categoryDrawable=(ImageView)itemView.findViewById(R.id.ImageView_categoryDrawable);
            TextView_productName=(TextView)itemView.findViewById(R.id.TextView_productName);
            TextView_productDetails=(TextView)itemView.findViewById(R.id.TextView_productDetails);
            TextView_startingRate=(TextView)itemView.findViewById(R.id.TextView_startingRate);
            ImageView_indicator = (ImageView)itemView.findViewById(R.id.ImageView_indicator);
        }

        @Override
        public void setExpandStateFlags(int i) {
            mExpandStateFlags = i;
        }

        @Override
        public int getExpandStateFlags() {
            return mExpandStateFlags;
        }
    }

    public static class ChildViewHolder extends RecyclerView.ViewHolder implements ExpandableItemViewHolder
    {
        private int mExpandStateFlags;
//        private TextView TextView_Product;
        private LinearLayout LinearLayout_ratesContainer;
        private Button Button_po;
        private Button Button_interested;
        private TextView TextView_ProductLabel;
        private TextView TextView_rateLabel;

        public ChildViewHolder(View itemView)
        {
            super(itemView);
//            TextView_Product=(TextView)itemView.findViewById(R.id.TextView_Product);
            LinearLayout_ratesContainer=(LinearLayout)itemView.findViewById(R.id.LinearLayout_ratesContainer);
            TextView_ProductLabel=(TextView)itemView.findViewById(R.id.TextView_ProductLabel);
            TextView_rateLabel=(TextView)itemView.findViewById(R.id.TextView_rateLabel);
            Button_po=(Button)itemView.findViewById(R.id.Button_po);
            Button_interested=(Button)itemView.findViewById(R.id.Button_interested);
        }

        @Override
        public void setExpandStateFlags(int i) {
            mExpandStateFlags = i;
        }

        @Override
        public int getExpandStateFlags() {
            return mExpandStateFlags;
        }
    }

    /////////////////////////////////////// DATA METHODS ///////////////////////////////////

    public void add(Deal_v3 deal)
    {
        insert(deal, data.size());
    }

    public void insert(Deal_v3 deal, int position)
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

    public void addAll(List<Deal_v3> dealArrayList)
    {
        if(dealArrayList!=null && dealArrayList.size()>0)
        {
            int startIndex = data.size();
            data.addAll(startIndex, dealArrayList);
            notifyItemRangeInserted(startIndex, dealArrayList.size());
        }
    }
}
