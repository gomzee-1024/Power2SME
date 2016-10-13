package com.power2sme.android.sections.chat;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.power2sme.android.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sysadmin on 15/9/16.
 */
public class ChatRViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SIMPLECHATITEM = 0;
    private static final int LISTCHATITEM = 1;
    private static final int RECYCLERCHATITEM = 3;
    private ArrayList<Object> list;
    private Context context;

    public ChatRViewAdapter(Context context, ArrayList<Object> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType){
            case SIMPLECHATITEM :  View v1 = inflater.inflate(R.layout.simple_chat_item,parent,false);
                viewHolder = new SimpleItemHolder(v1);
                break;
            case LISTCHATITEM :    View v2 =inflater.inflate(R.layout.list_chat_item,parent,false);
                viewHolder = new ListItemHolder(v2);
                break;
            case RECYCLERCHATITEM : View v3 =inflater.inflate(R.layout.recycletview_chat_item,parent,false);
                viewHolder = new RecyclerItemHolder(v3);
                break;
            default :       View v = inflater.inflate(R.layout.simple_chat_item,parent,false);
                viewHolder = new SimpleItemHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType()==SIMPLECHATITEM){
            SimpleItemHolder v1 = (SimpleItemHolder) holder;
            configureV1(v1,position);
        }
        else if(holder.getItemViewType()==LISTCHATITEM){
            ListItemHolder v2 = (ListItemHolder) holder;
            configureV2(v2,position);
        }else{
            RecyclerItemHolder v3 = (RecyclerItemHolder) holder;
            configurev3(v3,position);
        }
    }
    @TargetApi(17)
    private void configureV1(SimpleItemHolder v1, int position) {
        SimpleChatItem simpleChatItem = (SimpleChatItem) list.get(position);
        if(simpleChatItem.getMessage().isItMe()) {
            v1.cv.setImageResource(R.drawable.photo);
            Drawable drawable = context.getResources().getDrawable(R.drawable.my_chat_text_bg1);
            v1.ptext.setBackground(drawable);
             v1.root.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }else{
            v1.cv.setImageResource(R.drawable.account_circle);
            Drawable drawable = context.getResources().getDrawable(R.drawable.my_chat_text_bg);
            v1.ptext.setBackground(drawable);
            v1.root.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        v1.ptext.setText(simpleChatItem.getMessage().getMsg());
    }

    private void configureV2(final ListItemHolder v2, int position) {
        ListChatItem listChatItem = (ListChatItem) list.get(position);
        v2.ptext.setText(listChatItem.getMessage());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.list_view_row_item,R.id.label, listChatItem.getList());
        v2.listview.setAdapter(adapter);
        final int pos= position;
        v2.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if((getItemCount()-1)==pos && !v2.clicked){
                    v2.clicked = true;
                    String msg =((TextView)view.findViewById(R.id.label)).getText().toString();
                    Log.d("response", "onItemClick: " +msg);
                    ClickedItem item = (ClickedItem) context;
                    item.sendClickedItem(msg);
                }
            }
        });
        Drawable drawable = context.getResources().getDrawable(R.drawable.my_chat_text_bg);
        v2.ptext.setBackground(drawable);
        int contentHeight = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,33, context.getResources().getDisplayMetrics()))*(v2.listview.getCount());
        ViewGroup.LayoutParams lp = v2.listview.getLayoutParams();
        lp.height = contentHeight;
        v2.listview.setLayoutParams(lp);
    }

    private void configurev3(RecyclerItemHolder v3, int position) {
        Log.d("response", "configurev3: ");
        ProductList productlist = (ProductList) list.get(position);
        v3.list1 = productlist.list;
        v3.ptext.setText(productlist.getMessage());
        Drawable drawable = context.getResources().getDrawable(R.drawable.my_chat_text_bg);
        v3.ptext.setBackground(drawable);
        v3.adapter = new ChildRViewAdapter(context,productlist.list);
        v3.child.setLayoutManager(v3.horizontalLayout);
        v3.child.setAdapter(v3.adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof SimpleChatItem){
            return SIMPLECHATITEM;
        }
        else if(list.get(position) instanceof ListChatItem){
            return LISTCHATITEM;
        }else{
            return RECYCLERCHATITEM;
        }
    }

    public void addMsg(SimpleChatItem simpleChatItem){
        list.add(simpleChatItem);
        notifyItemInserted(list.size());
    }

    public void refreshlastmsg(SimpleChatItem simpleChatItem) {
        list.remove(list.size()-1);
        notifyItemRemoved(list.size());
        list.add(simpleChatItem);
        notifyItemInserted(list.size());
    }
    public void removeLastObject(){
        list.remove(list.size()-1);
        notifyItemRemoved(list.size());
    }

    public void refreshlastmsg(ListChatItem listChatItem) {
        list.remove(list.size()-1);
        notifyItemRemoved(list.size());
        list.add(listChatItem);
        notifyItemInserted(list.size());
    }

    public void refreshlastmsg(ProductList productList) {
        list.remove(list.size()-1);
        notifyItemRemoved(list.size());
        list.add(productList);
        notifyItemInserted(list.size());
    }

    class SimpleItemHolder extends RecyclerView.ViewHolder{
        CircleImageView cv;
        TextView ptext;
        LinearLayout root;
        public SimpleItemHolder(View itemView) {
            super(itemView);
            root = (LinearLayout) itemView.findViewById(R.id.simple_chat_item_root);
            cv = (CircleImageView) itemView.findViewById(R.id.simple_item_cirle_image);
            ptext = (TextView) itemView.findViewById(R.id.simple_item_textview);
        }
    }

    class ListItemHolder extends RecyclerView.ViewHolder{
        TextView ptext;
        ListView listview;
        boolean clicked=false;
        public ListItemHolder(View itemView) {
            super(itemView);
            ptext = (TextView) itemView.findViewById(R.id.list_chat_item_textview);
            listview = (ListView) itemView.findViewById(R.id.list_chat_item_listview);
        }
    }

    class RecyclerItemHolder extends RecyclerView.ViewHolder{
        RecyclerView child;
        LinearLayoutManager horizontalLayout;
        ArrayList<ProductListItem> list1 = new ArrayList<ProductListItem>();
        ChildRViewAdapter adapter;
        TextView ptext;
        public RecyclerItemHolder(View itemView) {
            super(itemView);
            ptext = (TextView) itemView.findViewById(R.id.recycler_chat_item_textview);
            child = (RecyclerView) itemView.findViewById(R.id.chat_item_recyclerview);
            horizontalLayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        }
    }

    public interface ClickedItem {
        public void sendClickedItem(String msg);
    }
}
