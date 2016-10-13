package com.power2sme.android.sections.chat;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.power2sme.android.R;

import java.util.ArrayList;

/**
 * Created by sysadmin on 18/7/16.
 */
public class ChildRViewAdapter extends RecyclerView.Adapter<ChildRViewAdapter.MyViewHolder> {

    LayoutInflater inflater;
    Context context;
    private ArrayList<ProductListItem> list;
    public ChildRViewAdapter(Context context,ArrayList<ProductListItem> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.chat_recyclerview_item,parent,false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Log.d("response", "Child onBindViewHolder: ");
        ProductListItem p = list.get(position);
        holder.prodName.setText(p.getProduct_name()) ;
        holder.price1.setText(p.getPrices());
        holder.location.setText(p.getLocations());
        holder.skucode.setText(p.getSku_code());
        holder.dp= p.getProd();
        holder.dp.skuCode = p.getSku_code();
        holder.dp.longDescription = p.getProduct_name();
        holder.dp.price = p.getPrices();
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String price = holder.price1.getText().toString();
                final DetectedProduct dp = holder.dp;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                Toast.makeText(context,price,Toast.LENGTH_LONG).show();
                                ((ChatActivity)context).placeRfq(dp);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to place a RFQ of this product?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    public void addProduct(ProductListItem p){
        list.add(p);
        notifyItemInserted(list.size());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView prodName,price1,location,skucode;
        Button confirm;
        DetectedProduct dp;
        public MyViewHolder(View itemView) {
            super(itemView);
            prodName = (TextView) itemView.findViewById(R.id.product_name);
            price1 = (TextView) itemView.findViewById(R.id.list_price1);
            location = (TextView) itemView.findViewById(R.id.locations);
            confirm = (Button) itemView.findViewById(R.id.confirm_button);
            skucode = (TextView) itemView.findViewById(R.id.sku_code);
        }
    }

    public interface Rfq{
        public void placeRfq(DetectedProduct prod);
    }
}
