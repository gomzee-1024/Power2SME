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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sysadmin on 15/9/16.
 */
public class ChatRViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SIMPLECHATITEM = 0;
    private static final int LISTCHATITEM = 1;
    private static final int RECYCLERCHATITEM = 3;
    private static final int SIMPLECHATITEMCLIENT = 4;
    private ArrayList<Object> list;
    private Context context;
    private boolean loading = false;
    private int previousTotal;
    private ArrayList<Integer> previousPos = new ArrayList<>();
    public ChatRViewAdapter(Context context, ArrayList<Object> list) {
        this.context = context;
        this.list = list;
        previousPos.add(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case SIMPLECHATITEM:
                View v1 = inflater.inflate(R.layout.simple_chat_item_bot, parent, false);
                viewHolder = new SimpleItemHolder(v1);
                break;
            case SIMPLECHATITEMCLIENT:
                View v4 = inflater.inflate(R.layout.simple_chat_item_client, parent, false);
                viewHolder = new SimpleItemHolderClient(v4);
                break;
            case LISTCHATITEM:
                View v2 = inflater.inflate(R.layout.list_chat_item, parent, false);
                viewHolder = new ListItemHolder(v2);
                break;
            case RECYCLERCHATITEM:
                View v3 = inflater.inflate(R.layout.recycletview_chat_item, parent, false);
                viewHolder = new RecyclerItemHolder(v3);
                break;
            default:
                View v = inflater.inflate(R.layout.simple_chat_item_bot, parent, false);
                viewHolder = new SimpleItemHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == SIMPLECHATITEM) {
            SimpleItemHolder v1 = (SimpleItemHolder) holder;
            configureV1(v1, position);
        } else if (holder.getItemViewType() == SIMPLECHATITEMCLIENT) {
            SimpleItemHolderClient v1 = (SimpleItemHolderClient) holder;
            configureV4(v1, position);
        } else if (holder.getItemViewType() == LISTCHATITEM) {
            ListItemHolder v2 = (ListItemHolder) holder;
            configureV2(v2, position);
        } else {
            RecyclerItemHolder v3 = (RecyclerItemHolder) holder;
            configurev3(v3, position);
        }
    }

    private void configureV1(SimpleItemHolder v1, int position) {
        SimpleChatItem simpleChatItem = (SimpleChatItem) list.get(position);
        v1.cv.setImageResource(R.drawable.account_circle);
        Drawable drawable = context.getResources().getDrawable(R.drawable.my_chat_text_bg);
        v1.ptext.setBackground(drawable);
        v1.ptext.setText(simpleChatItem.getMessage().getMsg());
    }

    private void configureV4(SimpleItemHolderClient v1, int position) {
        SimpleChatItem simpleChatItem = (SimpleChatItem) list.get(position);
        v1.cv.setImageResource(R.drawable.photo);
        Drawable drawable = context.getResources().getDrawable(R.drawable.my_chat_text_bg1);
        v1.ptext.setBackground(drawable);
        v1.ptext.setText(simpleChatItem.getMessage().getMsg());
    }

    private void configureV2(final ListItemHolder v2, int position) {
        ListChatItem listChatItem = (ListChatItem) list.get(position);
        v2.ptext.setText(listChatItem.getMessage());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.list_view_row_item, R.id.label, listChatItem.getList());
        v2.listview.setAdapter(adapter);
        final int pos = position;
        v2.clicked=false;
        v2.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ((getItemCount() - 1) == pos && !v2.clicked) {
                    v2.clicked = true;
                    String msg = ((TextView) view.findViewById(R.id.label)).getText().toString();
                    Log.d("response", "onItemClick: " + msg);
                    ClickedItem item = (ClickedItem) context;
                    item.sendClickedItem(msg);
                }
            }
        });
        int contentHeight = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, context.getResources().getDisplayMetrics())) * (v2.listview.getCount());
        ViewGroup.LayoutParams lp = v2.listview.getLayoutParams();
        lp.height = contentHeight;
        v2.listview.setLayoutParams(lp);
    }

    private void configurev3(final RecyclerItemHolder v3, final int position) {
        Log.d("response", "configurev3: ");
        v3.list1.clear();
        ProductList productlist = (ProductList) list.get(position);
        for (int i = 0; i < productlist.list.size(); ++i) {
            v3.list1.add(productlist.list.get(i));
            Log.d("response", "configurev3: "+"adding product");
        }
        v3.adapter = new ChildRViewAdapter(context, v3.list1);
        v3.child.setLayoutManager(v3.horizontalLayout);
        v3.child.setAdapter(v3.adapter);
        v3.ptext.setText(productlist.getMessage());
        Log.d("response", "configurev3: "+"position " + position);
        Log.d("response", "configurev3: "+productlist.list.size());
        previousTotal = getItemCount();
        v3.child.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("response", "onScrollStateChanged: " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("response", "onScrolled: " + dx + ":" + dy);
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemIndex = v3.horizontalLayout.findFirstVisibleItemPosition();
                Log.d("response", "totalitemcount " + totalItemCount);
                Log.d("response", "visibleitemcount " + visibleItemCount);
                Log.d("response", "firstvisibleitemcount " + firstVisibleItemIndex);
                Log.d("response", "loading " + loading);
                //synchronizew loading state when item count changes
                Log.d("position", "onScrolled: "+position);
                if(!previousPos.contains(position)) {
                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        } else if (firstVisibleItemIndex == 0) {
                            // top of list reached
                            // if you start loading
                            loading = false;
                        }
                    }
                    if (!loading) {
                        if ((totalItemCount - visibleItemCount) <= firstVisibleItemIndex) {
                            // Loading NOT in progress and end of list has been reached
                            // also triggered if not enough items to fill the screen
                            // if you start loading
                            Log.d("response", "onScrolled: " + "loading");
                            ((ChatActivity) context).loadmore();
                            loading = true;
                            previousPos.add(position);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof SimpleChatItem) {
            SimpleChatItem item = (SimpleChatItem) list.get(position);
            if (item.getMessage().isItMe()) {
                return SIMPLECHATITEMCLIENT;
            } else {
                return SIMPLECHATITEM;
            }
        } else if (list.get(position) instanceof ListChatItem) {
            return LISTCHATITEM;
        } else {
            return RECYCLERCHATITEM;
        }
    }

    public void addMsg(SimpleChatItem simpleChatItem) {
        list.add(simpleChatItem);
        notifyItemInserted(list.size()-1);
    }

    public void refreshlastmsg(SimpleChatItem simpleChatItem) {
        list.remove(list.size() - 1);
        notifyItemRemoved(list.size());
        list.add(simpleChatItem);
        notifyItemInserted(list.size()-1);
    }

    public void removeLastObject() {
        list.remove(list.size() - 1);
        notifyItemRemoved(list.size());
    }

    public void refreshlastmsg(ListChatItem listChatItem) {
        list.remove(list.size() - 1);
        notifyItemRemoved(list.size());
        list.add(listChatItem);
        notifyItemInserted(list.size()-1);
    }

    public void refreshlastmsg(ProductList productList) {
        list.remove(list.size() - 1);
        notifyItemRemoved(list.size());
        list.add(productList);
        notifyItemInserted(list.size()-1);
    }

    public void addProductList(ProductList productList) {
        list.add(productList);
        notifyItemInserted(list.size()-1);
    }

    public void addToChildRecyclerView(ProductListItem item , int pos){
        ((ProductList)list.get(pos)).addToList(item);
        notifyItemChanged(pos);
    }

    class SimpleItemHolder extends RecyclerView.ViewHolder {
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

    class SimpleItemHolderClient extends RecyclerView.ViewHolder {
        CircleImageView cv;
        TextView ptext;
        LinearLayout root;

        public SimpleItemHolderClient(View itemView) {
            super(itemView);
            root = (LinearLayout) itemView.findViewById(R.id.simple_chat_item_root_client);
            cv = (CircleImageView) itemView.findViewById(R.id.simple_item_cirle_image_client);
            ptext = (TextView) itemView.findViewById(R.id.simple_item_textview_client);
        }
    }

    class ListItemHolder extends RecyclerView.ViewHolder {
        TextView ptext;
        ListView listview;
        boolean clicked = false;

        public ListItemHolder(View itemView) {
            super(itemView);
            ptext = (TextView) itemView.findViewById(R.id.list_chat_item_textview);
            listview = (ListView) itemView.findViewById(R.id.list_chat_item_listview);
        }
    }

    class RecyclerItemHolder extends RecyclerView.ViewHolder {
        RecyclerView child;
        LinearLayoutManager horizontalLayout;
        ArrayList<Object> list1 = new ArrayList<>();
        ChildRViewAdapter adapter;
        TextView ptext;

        public RecyclerItemHolder(View itemView) {
            super(itemView);
            ptext = (TextView) itemView.findViewById(R.id.recycler_chat_item_textview);
            child = (RecyclerView) itemView.findViewById(R.id.chat_item_recyclerview);
            horizontalLayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        }
    }

    public interface ClickedItem {
        public void sendClickedItem(String msg);

        public void loadmore();
    }
}
