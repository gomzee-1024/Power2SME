package com.power2sme.android.sections.myorders.shipmentdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power2sme.android.R;
import com.power2sme.android.entities.tracking.Invoice;
import com.power2sme.android.entities.tracking.Material;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by satish on 30/10/15.
 */
public class ShipmentDetailsRecyclerAdapter extends RecyclerView.Adapter<ShipmentDetailsRecyclerAdapter.ViewHolder>  {

    Context context;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        context=parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipment_details_list_item, parent, false);
        return new ViewHolder(view);
    }

    private OnLocateMapClickListener onItemClickListener;
    public interface OnLocateMapClickListener
    {
        void onLocateMapButtonClick(String invoice_no);
    }
    public void setOnSalesOrderClickListener(OnLocateMapClickListener onItemClickListener)
    {
        this.onItemClickListener=onItemClickListener;
    }


    public ArrayList<Invoice> data;
    String orderDate;
    String orderNumber;
    public ShipmentDetailsRecyclerAdapter(ArrayList<Invoice> data, String orderDate, String orderNumber)
    {
        this.data = data;
        this.orderDate=orderDate;
        this.orderNumber=orderNumber;
        setHasStableIds(true);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position%2==0)
        {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        else
        {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.ulternate_row_color));
        }


        final Invoice invoiceItem=this.data.get(position);
        if(invoiceItem!=null){
            holder.mTextView_orderDate.setText(orderDate);
            holder.mTextView_orderNo.setText(orderNumber);
            holder.mTextView_currentLocation.setText(checkForEmpty(invoiceItem.getCurrent_address()));
            holder.mTextView_estimated_distance.setText(checkForEmpty(invoiceItem.getEstimated_schedule().getDistance()));
            holder.mTextView_shipment_startDate.setText(checkForEmpty(invoiceItem.getShipment_time().getDate()));
            holder.mTextView_Truck_label.setText("Truck " + (position+1) + " Information");
            List<Material> materials=invoiceItem.getMaterials();
            Iterator<Material> iterator=materials.iterator();
            while(iterator.hasNext()) {
                Material materialItem=iterator.next();

                TextView mTextView_material_detail=new TextView(context);
                mTextView_material_detail.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                mTextView_material_detail.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
                mTextView_material_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                mTextView_material_detail.setPadding(10,5,5,5);
                //mTextView_material_detail.setTextAppearance(R.style.p2s_30_gray_normal);
                mTextView_material_detail.setText(materialItem.getMaterial());
                holder.mLinearLayout_Materials.addView(mTextView_material_detail);

                TextView mTextView_material_quantity=new TextView(context);
                mTextView_material_quantity.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                mTextView_material_quantity.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
                mTextView_material_quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                mTextView_material_quantity.setPadding(10,5,5,5);
                mTextView_material_quantity.setText(materialItem.getQuantity()+materialItem.getUnits());
                holder.mLinearLayout_quantity.addView(mTextView_material_quantity);
            }


            holder.mButton_locateOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null)
                        onItemClickListener.onLocateMapButtonClick(invoiceItem.getInvoice_number());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    String checkForEmpty(String str)
    {
        if(!(str==null || "".equals(str)))
            return str;
        return "NA";
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView mTextView_Truck_label;
        public TextView mTextView_currentLocation;
        public TextView mTextView_shipment_startDate;
        public TextView mTextView_estimated_distance;
        public LinearLayout mLinearLayout_Materials;
        public LinearLayout mLinearLayout_quantity;
        public LinearLayout mButton_locateOnMap;
        TextView mTextView_orderNo;
        TextView mTextView_orderDate;
        public ViewHolder(View itemView)
        {
            super(itemView);
            mTextView_Truck_label=(TextView)itemView.findViewById(R.id.TextView_shipmentDetails_TruckLabel);
            mTextView_currentLocation=(TextView)itemView.findViewById(R.id.TextView_shipmentDetails_currentLocation);
            mTextView_shipment_startDate=(TextView)itemView.findViewById(R.id.TextView_shipmentDetails_shipment_startDate);
            mTextView_estimated_distance=(TextView)itemView.findViewById(R.id.TextView_shipmentDetails_shipment_estimated_distance);
            mLinearLayout_Materials=(LinearLayout)itemView.findViewById(R.id.LinearLayout_shipmentDetails_Materials_rootView);
            mLinearLayout_quantity=(LinearLayout)itemView.findViewById(R.id.LinearLayout_shipmentDetails_Quantity_rootView);
            mButton_locateOnMap=(LinearLayout)itemView.findViewById(R.id.Button_shipmentDetails_locate_on_map);
            mTextView_orderDate=(TextView)itemView.findViewById(R.id.TextView_shipmentDetails_orderDate);
		    mTextView_orderNo=(TextView)itemView.findViewById(R.id.TextView_shipmentDetails_orderId);
        }
    }
}
