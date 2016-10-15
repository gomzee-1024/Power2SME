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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.power2sme.android.R;

import java.util.ArrayList;

/**
 * Created by sysadmin on 18/7/16.
 */
public class ChildRViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SIMPLE_CARD = 0 ;
    private static final int BUTTON = 1 ;
    LayoutInflater inflater;
    Context context;
    private ArrayList<Object> list;
    public ChildRViewAdapter(Context context,ArrayList<Object> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType){
            case SIMPLE_CARD :  View v1 = inflater.inflate(R.layout.chat_recyclerview_item,parent,false);
                viewHolder = new CardViewHolder(v1);
                break;
            case BUTTON :    View v2 =inflater.inflate(R.layout.chat_childrview_button,parent,false);
                viewHolder = new ButtonHolder(v2);
                break;
            default :       View v = inflater.inflate(R.layout.chat_recyclerview_item,parent,false);
                viewHolder = new CardViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Log.d("response", "Child onBindViewHolder: ");
        if(holder.getItemViewType()==SIMPLE_CARD){
            CardViewHolder v1 = (CardViewHolder) holder;
            configureV1(v1,position);
        }
        else if(holder.getItemViewType()==BUTTON){
            ButtonHolder v2 = (ButtonHolder) holder;
            configureV2(v2,position);
        }
    }

    private void configureV1(CardViewHolder v1, int position) {
        ProductListItem p = (ProductListItem) list.get(position);
        v1.bind(p);
    }

    private void configureV2(ButtonHolder v2, int position) {
        v2.bind();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof ProductListItem){
            return SIMPLE_CARD;
        }
        else{
            return BUTTON;
        }
    }

    public void addProduct(ProductListItem p){
        list.add(p);
        notifyItemInserted(list.size());
    }
    public void addProduct(String p){
        list.add(p);
        notifyItemInserted(list.size());
    }

    public void removeLastObject(){
        list.remove(list.size()-1);
        notifyItemRemoved(list.size());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView prodName,price1,location,skucode;
        Button confirm;
        public CardViewHolder(View itemView) {
            super(itemView);
            prodName = (TextView) itemView.findViewById(R.id.product_name);
            price1 = (TextView) itemView.findViewById(R.id.list_price1);
            location = (TextView) itemView.findViewById(R.id.locations);
            confirm = (Button) itemView.findViewById(R.id.confirm_button);
            skucode = (TextView) itemView.findViewById(R.id.sku_code);
        }
        public void bind(ProductListItem p){
            prodName.setText(p.getProduct_name()) ;
            price1.setText(p.getPrices());
            location.setText(p.getLocations());
            skucode.setText(p.getSku_code());
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    //Toast.makeText(itemView.getContext(),price,Toast.LENGTH_SHORT).show();
                                    ((ChatActivity)itemView.getContext()).placeRfq(prodName.getText().toString(),skucode.getText().toString());
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setMessage("Are you sure you want to place a RFQ of this product?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });
        }
    }

    public static class ButtonHolder extends RecyclerView.ViewHolder{

        ImageButton imgb;

        public ButtonHolder(View itemView) {
            super(itemView);
            imgb = (ImageButton) itemView.findViewById(R.id.more_button);
        }
        public void bind(){
            imgb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  ((ChatActivity)itemView.getContext()).sendMoreRequest();
                }
            });
        }
    }

    public interface Rfq{
        public void placeRfq(String prodName, String skuCode);
    }
}
