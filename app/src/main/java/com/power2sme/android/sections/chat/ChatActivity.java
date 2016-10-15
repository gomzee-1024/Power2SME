package com.power2sme.android.sections.chat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.power2sme.android.ContainerActivity;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.entities.LeadSource;
import com.power2sme.android.entities.UnitOfMeasure;
import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.entities.v3.OpportunityLine_v3;
import com.power2sme.android.entities.v3.Opportunity_v3;
import com.power2sme.android.entities.v3.Organization_v3;
import com.power2sme.android.entities.v3.SKU_v3;
import com.power2sme.android.entities.v3.TaxationPreference_v3;
import com.power2sme.android.entities.v3.Urgency_v3;
import com.power2sme.android.sections.activities.BaseAppCompatActivity;
import com.power2sme.android.sections.activities.ChildActivity;
import com.power2sme.android.sections.myrfqs.add.AddRFQPresentorImpl;
import com.power2sme.android.sections.myrfqs.add.IAddRFQPresentor;
import com.power2sme.android.sections.myrfqs.add.IAddRFQView;
import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.UIMessage;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shubhamkansal on 9/19/16.
 */

public class ChatActivity extends BaseAppCompatActivity implements IAddRFQView, View.OnClickListener, ChatRViewAdapter.ClickedItem, ChildRViewAdapter.Rfq {
    IAddRFQPresentor iAddRFQPresentor;

    private RecyclerView chat_rview;
    private LinearLayoutManager layoutManager;
    private ImageButton send_button;
    private EditText edit_chat_msg;
    private ArrayList<Object> list = new ArrayList<Object>();
    private boolean advance_stage = false;
    private ApiRequest apiRequestObj = new ApiRequest();
    private DetectedProduct dp = new DetectedProduct();
    private boolean numans = false;
    private boolean pin_ans = false;
    private boolean qty_ans = false;
    private ChatRViewAdapter chatRViewAdapter;
    private LinkedHashSet<String> skucodes = new LinkedHashSet<>();
    private int chatid;
    private ObjectMapper mapper = new ObjectMapper();
    private String smeid, phone, email;
    private MyAccountApplication app;
    //private String apiPrefix = "http://192.168.0.175:8080";
    private String apiPrefix = "http://192.168.0.175:8080";
    private String skuApiPrefix = "http://192.168.1.142:9090";
    private StringBuilder recordedWords = new StringBuilder("");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyAccountApplication) getApplicationContext();
        SharedPreferences sharedPref = app.getPrefs();
        smeid = sharedPref.getString(Constants.PREFERENCE_CUSTOMER_SMEID, "");
        phone = sharedPref.getString(Constants.PREFERENCE_CUSTOMER_MOBILENUMBER, "");
        email = sharedPref.getString(Constants.PREFERENCE_CUSTOMER_EMAIL, "");
        //id = smeidpref.getString("SmeId", "");
        //Log.d("SMEID", "id=" + id);
        send_button = (ImageButton) findViewById(R.id.send_button);
        edit_chat_msg = (EditText) findViewById(R.id.edit_msg);
        chat_rview = (RecyclerView) findViewById(R.id.chat_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        send_button.setOnClickListener(this);
        chat_rview.setLayoutManager(layoutManager);
        chatRViewAdapter = new ChatRViewAdapter(this, list);
        chat_rview.setAdapter(chatRViewAdapter);
        generateTypingResponce(0);
        RequestDto request = new RequestDto();
        request.setSmeid(smeid);
        request.setPhone(phone);
        request.setEmail(email);
        request.setInput("Hello");
        String url = apiPrefix+"/chatapi/welcome";
        //String url = "http://192.168.1.142:9090/chatapi/welcome";
        //String url = "http://192.168.0.175:8080/chatapi/welcome";
        String requestString = null;
        try {
            requestString = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        apiRequestObj.sendJsonRequest(url, requestString, new ApiRequest.SyntaxnetCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    chatid = result.getInt("chatid");
                    generateChatMessage(result.getString("response"), false, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ChatActivity.this);
                builder.setMessage("Due to network issues we are not able to communicate, you will get a call from us shortly").show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (edit_chat_msg.getText().toString().equalsIgnoreCase("")) {
            edit_chat_msg.setError("Enter some text first");
        }else if(edit_chat_msg.getText().toString().equalsIgnoreCase("select app with key=power2sme")){
            final EditText input = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            input.setText("http://");
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ChatActivity.this);
            builder.setTitle("Enter ip of the server with port").setView(input);
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            apiPrefix = input.getText().toString();
                        }
                    });
            // Setting Negative "NO" Button
            builder.setNegativeButton("CANCEL",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                        }
                    });
            builder.show();
            edit_chat_msg.setText("");
            hideKeyboard(view);
        }
        else {
            String message = edit_chat_msg.getText().toString();
            edit_chat_msg.setText("");
            hideKeyboard(view);
            generateChatMessage(message, true, false);
            generateTypingResponce();
            if (checkforHiHello(message)) {
                return;
            }
            processForApiRequests(message);
        }
    }

    private void processForApiRequests(String message) {
        //StringBuilder url = new StringBuilder("http://192.168.1.142:9090/chatapi/chat");
        StringBuilder url = new StringBuilder(apiPrefix+"/chatapi/chat");
        //StringBuilder url = new StringBuilder("http://192.168.0.175:8080/chatapi/chat");
        RequestDto request = new RequestDto();
        if (dp.category != null) {
            request.setCategory(dp.category);
        }
        if (dp.subCategory != null) {
            request.setSubcategory(dp.subCategory);
        }
        if (dp.brand != null) {
            request.setBrand(dp.brand);
        }
        request.setPin_ans(pin_ans);
        request.setNumans(numans);
        request.setCredit(dp.creditPayment);
        request.setAdvancestage(advance_stage);
        request.setQtyans(qty_ans);
        request.setChatid(chatid);
        request.setInput(message);
        Log.d("response", "processForApiRequests: " + url.toString());
        String requestString = null;
        try {
            requestString = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        apiRequestObj.sendJsonRequest(url.toString(), requestString, new ApiRequest.SyntaxnetCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    showResponse(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                //generateChatMessageWithDelay("Due to network issue, We will contact you later,Thank you", false, true, 0);
                chatRViewAdapter.removeLastObject();
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ChatActivity.this);
                builder.setMessage("Due to network issues we are not able to communicate, you will get a call from us shortly").show();
            }
        });
    }

    private void showResponse(JSONObject result) throws JSONException {
        final int responseNo;
        recordedWords.append(result.getString("recordedWords"));
        JSONObject dp1 = result.getJSONObject("detectedProduct");
        if (!dp1.getString("category").equalsIgnoreCase("null")) {
            dp.category = dp1.getString("category");
        } else {
            dp.category = null;
        }
        if (!dp1.getString("subCategory").equalsIgnoreCase("null")) {
            dp.subCategory = dp1.getString("subCategory");
        } else {
            dp.subCategory = null;
        }
        if (!dp1.getString("brand").equalsIgnoreCase("null")) {
            dp.brand = dp1.getString("brand");
        } else {
            dp.brand = null;
        }
        if (dp1.getInt("creditDays") != -1) {
            dp.creditDays = dp1.getInt("creditDays");
        }
        if (dp1.getInt("qty") != -1) {
            dp.qty = dp1.getDouble("qty");
        }
        if (!dp1.getString("taxPref").equalsIgnoreCase("null")) {
            dp.taxPref = dp1.getString("taxPref");
        }
        int pincode = dp1.getInt("pincode");
        if (dp1.getInt("pincode") != -1) {
            dp.pincode = pincode;
        }
        qty_ans = result.getBoolean("qty_ans");
        pin_ans = result.getBoolean("pin_ans");
        advance_stage = result.getBoolean("advance_stage");
        numans = result.getBoolean("numans");
        dp.creditPayment = dp1.getBoolean("creditPayment");
        responseNo = result.getInt("response_num");
        final String response = result.getString("response");
        if (result.getBoolean("showprice")) {
            final ApiRequest apiRequestObj = new ApiRequest();
            String url;
            if (dp.category.equalsIgnoreCase("aluminium")) {
                if (dp.brand != null) {
                    url = skuApiPrefix+"/p2sapi/ws/v3/skuList?" + "category=" + dp.category
                            + "&subcategory=" + dp.category + "+" + dp.subCategory.replace("-", "+") +
                            "&brand=" + dp.brand+"&longdesc="+recordedWords.toString();
                } else {
                    url = skuApiPrefix+"/p2sapi/ws/v3/skuList?" + "category=" + dp.category
                            + "&subcategory=" + dp.category + "+" + dp.subCategory.replace("-", "+") +
                            "&longdesc="+recordedWords.toString();
                }
            } else {
                if (dp.brand != null) {
                    url = skuApiPrefix+"/p2sapi/ws/v3/skuList?" + "category=" + dp.category
                            + "&subcategory=" + dp.subCategory.replace("-", "+") +
                            "&brand=" + dp.brand+"&longdesc="+recordedWords.toString();
                } else {
                    url = skuApiPrefix+"/p2sapi/ws/v3/skuList?" + "category=" + dp.category
                            + "&subcategory=" + dp.subCategory.replace("-", "+")+"&longdesc="+recordedWords.toString();
                }
            }
            Log.d("NOW", "showResponse: " + url);
            skucodes.clear();
            final ArrayList<ProductListItem> productlist = new ArrayList<ProductListItem>();
            final ProductList prolist = new ProductList(productlist);
            prolist.setMessage("Please wait while we find product list for you");
            chatRViewAdapter.refreshlastmsg(prolist);
            chat_rview.scrollToPosition(chatRViewAdapter.getItemCount() - 1);
            final Count count = new Count();
            apiRequestObj.sendskuapirequest(url, new ApiRequest.SkuApiCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    int totalrecords = 0;
                    try {
                        totalrecords = result.getInt("TotalRecord");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (totalrecords != 0) {
                        JSONArray data = null;
                        count.setSkusfound(count.getSkusfound() + totalrecords);
                        try {
                            data = result.getJSONArray("Data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < totalrecords; ++i) {
                            try {
                                skucodes.add(data.getJSONObject(i).getString("skucode"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //String url = "http://192.168.1.142:9090/chatapi/cards";
                        //String url = skuApiPrefix+"/chatapi/cards";
                        sendCardRequest(false);
                        //final ArrayList<ProductListItem> productlist = new ArrayList<ProductListItem>();
                        //ProductList prolist = new ProductList(productlist);
                        //prolist.setMessage("Please wait while we find product list for you");
                        //chatRViewAdapter.refreshlastmsg(prolist);
                        //chat_rview.scrollToPosition(chatRViewAdapter.getItemCount() - 1);
                        //generateChatMessageWithDelay("Please wait while we find product list for you",false,true,0);
                        //sendForDealsDataByLocation(count, skucodes, 0);
                        /*for (int i = 0; i < skucodes.size(); ++i) {
                            String url1 = skuApiPrefix+"/p2sapi/ws/v3/dealsDataByLocation?sku=" + skucodes.get(i) + "&pincode=" + dp.pincode + "&smeid="+smeid;
                            Log.d("Price", "url " + url1);
                            apiRequestObj.sendskuapirequest(url1, new ApiRequest.SkuApiCallback() {
                                @Override
                                public void onSuccess(JSONObject result) {
                                    int totalrecords1 = 0;
                                    try {
                                        totalrecords1 = result.getInt("TotalRecords");
                                        if (totalrecords1 != 0) {
                                            Log.d("response", "Product :: :");
                                            //ProductList prolist = new ProductList(productlist);
                                            //prolist.setMessage(response);
                                            //chatRViewAdapter.refreshlastmsg(prolist);
                                            JSONArray product = null;
                                            product = result.getJSONArray("Data");
                                            for (int j = 0; j < 1; ++j) {
                                                JSONObject obj = product.getJSONObject(j);
                                                final ProductListItem p = new ProductListItem();
                                                JSONArray priceArray = obj.getJSONArray("priceSpace");
                                                Double minqty = 0.0, maxqty = 0.0;
                                                Double bestAvailPrice = 0.0, rateVisibleToCustomer = 0.0;
                                                String dealid = obj.getString("id");
                                                String sku = obj.getString("product_sku");
                                                String uom = obj.getString("uom");


                                                Log.d("responce", "flag ::");
                                                p.setProduct_name(obj.getString("product"));
                                                p.setLocations(obj.getString("locationValue"));
                                                p.setSku_code(sku);
                                                p.setProd(dp);
                                                String url2 = skuApiPrefix+"/p2sapi/ws/v3/getSkuPriceForSme?dealid=" + dealid + "&qty=" + dp.qty + "&creditdays=" + (dp.creditPayment ? dp.creditDays : 0) + "&skucode=" + sku + "&uomcode=" + uom + "&smeid=C00008";

                                                apiRequestObj.sendskuapirequest(url2, new ApiRequest.SkuApiCallback() {
                                                    @Override
                                                    public void onSuccess(JSONObject result) {
                                                        int totalrec = 0;
                                                        try {
                                                            totalrec = result.getInt("TotalRecords");
                                                            Double price = result.getJSONObject("Data").getDouble("salesPrice");
                                                            String price1 = String.format("%.2f",price);
                                                            p.makePriceStatement(price1);
                                                            int position = chatRViewAdapter.getItemCount() - 1;
                                                            ChatRViewAdapter.RecyclerItemHolder holder = (ChatRViewAdapter.RecyclerItemHolder) chat_rview.findViewHolderForAdapterPosition(position);
                                                            holder.ptext.setText("These are the products which we have at your location currently");
                                                            holder.adapter.addProduct(p);

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });

                                                // productlist.add(p);
                                                // Log.d("response", "Productlist size: "+ productlist.size());
                                                // ProductList prolist = new ProductList(productlist);
                                                // chatRViewAdapter.refreshlastmsg(prolist);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        //generateChatMessageWithDelay("Enter correct pincode",false,true,0);
                                    }
                                }
                            });
                        }*/
                        /*int position = chatRViewAdapter.getItemCount() - 1;
                        ChatRViewAdapter.RecyclerItemHolder holder = (ChatRViewAdapter.RecyclerItemHolder) chat_rview.findViewHolderForAdapterPosition(position);
                        if(holder.adapter.getItemCount()==0){
                            generateChatMessageWithDelay("Sorry Sir, No products found in your delivery location which matches your specification",false,true,0);
                        }*/
                    } else {
                        generateChatMessageWithDelay("No products found of this brand", false, true, 0);
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    //generateChatMessageWithDelay("Due to network issue, We will contact you later,Thank you",false,true,0);
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ChatActivity.this);
                    builder.setMessage("Due to network issues we are not able to communicate, you will get a call from us shortly").show();
                }
            });
        } else if (responseNo == -1) {
            generateChatMessageWithDelay(result.getString("response"), false, true, 0);
        } else if (responseNo == 1) {
            // String response = result.getString("response");
            String[] list = response.split(",");
            Log.d("response", "list size " + list.length);
            generateChatMessageWithDelay("Sir we have these Product Categories\nPlease specify different brand", list, 0);
        } else if (responseNo == 2) {
            //String response = result.getString("response");
            String[] list = response.split(",");
            Log.d("response", "list size " + list.length);
            generateChatMessageWithDelay("Sir we have these subcategories in " + dp.category, list, 0);
        } else if (responseNo == 3) {
            //String response = result.getString("response");
            String[] list = {"Credit", "Advance"};
            generateChatMessageWithDelay(response, list, 0);
        } else if (responseNo == 4) {
            //String response = result.getString("response");
            String[] list = {"7 days", "15 days", "30 days"};
            generateChatMessageWithDelay(response, list, 0);
        } else if (responseNo == 5) {
            String[] list = response.split(",");
            generateChatMessageWithDelay("Ok,Sir please specify your tax preference", list, 0);
        }
    }

    private void sendCardRequest(final boolean scroll) {
        final CardRequest cardRequest = new CardRequest();
        String url = apiPrefix+"/chatapi/cards";
        cardRequest.setSkucodeList(skucodes);
        cardRequest.setCreditDays(dp.creditDays);
        cardRequest.setNoOfCardsToDisplay(10);
        cardRequest.setPincode(dp.pincode);
        cardRequest.setQuantity(dp.qty);
        cardRequest.setSmeid(smeid);
        RequestDto requestDto = new RequestDto();
        requestDto.setChatid(chatid);
        requestDto.setCardRequest(cardRequest);
        String requestBody=null;
        try {
            requestBody = mapper.writeValueAsString(requestDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Log.d("response", "sendCardRequest: "+requestBody);
        apiRequestObj.sendJsonRequest(url, requestBody, new ApiRequest.SyntaxnetCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                CardsResponse cardsResponse = null;
                String cardResponseString = null;
                try {
                    cardResponseString = result.getJSONObject("cardResponse").toString();
                    cardsResponse = mapper.readValue(cardResponseString,CardsResponse.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(cardsResponse.getTotalRecords()==0){
                    if(!scroll) {
                        generateChatMessageWithDelay("Sorry your product is not currently available at your location \nPlease try different location or change your product requirement", false, true, 0);
                        dp = new DetectedProduct();
                    }else{
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ChatActivity.this);
                        builder.setMessage("No more products prices are available right now, Please specify different requirement or raise an rfq by pressing yes and soon you will get a call from us to meet your requirement").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                placeRfq("","");
                            }
                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                    }
                }else {
                    int x;
                    int position = chatRViewAdapter.getItemCount() - 1;
                    Collection<String> removeSkus = cardsResponse.getProcessedSkus();
                    skucodes.removeAll(removeSkus);
                    ChatRViewAdapter.RecyclerItemHolder holder = (ChatRViewAdapter.RecyclerItemHolder) chat_rview.findViewHolderForAdapterPosition(position);
                    holder.ptext.setText("These are the products which we have at your location currently");
                    int pos = holder.adapter.getItemCount();
                    List<CardsInfo> cardsInfoList = cardsResponse.getCardsInfo();
                    for (x = 0; x < cardsInfoList.size(); ++x) {
                        ProductListItem p = new ProductListItem();
                        p.setProduct_name(cardsInfoList.get(x).getLongdescription());
                        p.setSku_code(cardsInfoList.get(x).getSkucode());
                        p.setPrices(cardsInfoList.get(x).getPrice());
                        p.setLocations(cardsInfoList.get(x).getLocation());
                        p.setProd(dp);
                        holder.adapter.addProduct(p);
                    }
                    if(scroll){
                        holder.child.scrollToPosition(pos);
                    }
                    holder.adapter.addProduct("button");
                }
            }

            @Override
            public void onError(VolleyError error) {
                //generateChatMessageWithDelay("Due to network issue we were not able to show product, you will get a call from us shortly",false,true,0);
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ChatActivity.this);
                builder.setMessage("Due to network issues we are not able to communicate, you will get a call from us shortly").show();
            }
        });
    }

    private void sendForDealsDataByLocation(final Count count, final List<String> skucodes, final int i) {
        Log.d("response", "sendForDealsDataByLocation: "+ i);
        for(int j=i;j<skucodes.size() && j<(i+3) && count.getDealsfound()<5;++j) {
            String url1 = skuApiPrefix+"/p2sapi/ws/v3/dealsDataByLocation?sku=" + skucodes.get(j) + "&pincode=" + dp.pincode + "&smeid=" + smeid;
            Log.d("Price", "url " + url1);
            apiRequestObj.sendskuapirequest(url1, new ApiRequest.SkuApiCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    int totalrecords1 = 0;
                    count.setSkucount(count.getSkucount()+1);
                    try {
                        totalrecords1 = result.getInt("TotalRecords");
                        if (totalrecords1 != 0) {
                            Log.d("response", "Product :: :");
                            count.setDealsfound(count.getDealsfound() + 1);
                            //ProductList prolist = new ProductList(productlist);
                            //prolist.setMessage(response);
                            //chatRViewAdapter.refreshlastmsg(prolist);
                            JSONArray product = null;
                            product = result.getJSONArray("Data");

                            JSONObject obj = product.getJSONObject(0);
                            final ProductListItem p = new ProductListItem();
                            //JSONArray priceArray = obj.getJSONArray("priceSpace");
                            //Double minqty = 0.0, maxqty = 0.0;
                            //Double bestAvailPrice = 0.0, rateVisibleToCustomer = 0.0;
                            String dealid = obj.getString("id");
                            String sku = obj.getString("product_sku");
                            String uom = obj.getString("uom");


                            Log.d("responce", "flag ::");
                            p.setProduct_name(obj.getString("product"));
                            p.setLocations(obj.getString("locationValue"));
                            p.setSku_code(sku);
                            p.setProd(dp);
                            String url2 = skuApiPrefix+"/p2sapi/ws/v3/getSkuPriceForSme?dealid=" + dealid + "&qty=" + dp.qty + "&creditdays=" + (dp.creditPayment ? dp.creditDays : 0) + "&skucode=" + sku + "&uomcode=" + uom + "&smeid=C00008";

                            apiRequestObj.sendskuapirequest(url2, new ApiRequest.SkuApiCallback() {
                                @Override
                                public void onSuccess(JSONObject result) {
                                    int totalrec = 0;
                                    try {
                                        totalrec = result.getInt("TotalRecords");
                                        Double price = result.getJSONObject("Data").getDouble("salesPrice");
                                        String price1 = String.format("%.2f", price);
                                        p.makePriceStatement(price1);
                                        int position = chatRViewAdapter.getItemCount() - 1;
                                        ChatRViewAdapter.RecyclerItemHolder holder = (ChatRViewAdapter.RecyclerItemHolder) chat_rview.findViewHolderForAdapterPosition(position);
                                        holder.ptext.setText("These are the products which we have at your location currently");
                                        holder.adapter.addProduct(p);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(VolleyError error) {

                                }
                            });
                        }
                        if(count.getSkucount()==i+2){
                            sendForDealsDataByLocation(count,skucodes,i+3);
                        }
                        if(count.getSkucount()==(skucodes.size()-1)){
                            generateChatMessageWithDelay("Sorry sir price is not available for your product right now!",false,true,0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //generateChatMessageWithDelay("Enter correct pincode",false,true,0);
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    count.setSkucount(count.getSkucount()+1);
                    if(count.getSkucount()==i+2){
                        sendForDealsDataByLocation(count,skucodes,i+3);
                    }
                    if(count.getSkucount()==(skucodes.size()-1)){
                        generateChatMessageWithDelay("Sorry sir price is not available for your product right now!",false,true,0);
                    }
                }
            });
        }
    }


    private void hideKeyboard(View view) {
        View view1 = getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean checkforHiHello(String message) {
        switch (message) {
            case "hi":
            case " hi":
                generateChatMessageWithDelay("Hello sir ,How may I help you?", false, true, 2000);
                return true;
            default:
                return false;
        }
    }

    private void generateTypingResponce() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ChatMessage chat_msg = new ChatMessage();
                chat_msg.setMsg("Typing...");
                chat_msg.setIsMe(false);
                SimpleChatItem simpleChatItem = new SimpleChatItem();
                simpleChatItem.setMessage(chat_msg);
                chatRViewAdapter.addMsg(simpleChatItem);
                chat_rview.scrollToPosition(chatRViewAdapter.getItemCount() - 1);
            }
        }, 1000);
    }

    private void generateTypingResponce(int delay) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ChatMessage chat_msg = new ChatMessage();
                chat_msg.setMsg("Typing...");
                chat_msg.setIsMe(false);
                SimpleChatItem simpleChatItem = new SimpleChatItem();
                simpleChatItem.setMessage(chat_msg);
                chatRViewAdapter.addMsg(simpleChatItem);
                chat_rview.scrollToPosition(chatRViewAdapter.getItemCount() - 1);
            }
        }, delay);
    }

    private void generateChatMessage(String s, boolean isMe, boolean replaceLast) {
        ChatMessage msg = new ChatMessage();
        msg.setMsg(s);
        msg.setIsMe(isMe);
        SimpleChatItem chatItem = new SimpleChatItem();
        chatItem.setMessage(msg);
        if (replaceLast) {
            chatRViewAdapter.refreshlastmsg(chatItem);
        } else {
            chatRViewAdapter.addMsg(chatItem);
        }
        chat_rview.scrollToPosition(chatRViewAdapter.getItemCount() - 1);
    }

    private void generateChatMessageWithDelay(final String s, final boolean isMe, final boolean replaceLast, int delay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ChatMessage msg = new ChatMessage();
                msg.setMsg(s);
                msg.setIsMe(isMe);
                SimpleChatItem chatItem = new SimpleChatItem();
                chatItem.setMessage(msg);
                if (replaceLast) {
                    chatRViewAdapter.refreshlastmsg(chatItem);
                } else {
                    chatRViewAdapter.addMsg(chatItem);
                }
                chat_rview.scrollToPosition(chatRViewAdapter.getItemCount() - 1);
            }
        }, delay);
    }

    private void generateChatMessageWithDelay(final String s, final String[] list, int delay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ListChatItem chatItem = new ListChatItem();
                chatItem.setMessage(s);
                chatItem.setList(list);
                chatRViewAdapter.refreshlastmsg(chatItem);
                chat_rview.scrollToPosition(chatRViewAdapter.getItemCount() - 1);
            }
        }, delay);
    }

    @Override
    public void sendClickedItem(String msg) {
        generateChatMessageWithDelay(msg, true, false, 0);
        generateTypingResponce();
        processForApiRequests(msg);
    }

    @Override
    public void loadmore() {
        sendCardRequest(true);
    }

    @Override
    public void placeRfq(String prodName,String skuCode) {
        //dp.skuCode = product.skuCode;

        iAddRFQPresentor = new AddRFQPresentorImpl(this, this);
        NewRFQ_v3 newRFQ = new NewRFQ_v3();
        newRFQ.setObject_type_id("1");

        Organization_v3 organization = getOrganizationPayloadEntity(prodName,skuCode);
        organization.setLeadSource(LeadSource.Android_RFQ.toString());
        newRFQ.setOrganisation(organization);

        Opportunity_v3 opportunity = getOpportunityPayloadEntity(prodName,skuCode);
        opportunity.setLeadSource(LeadSource.Android_RFQ.toString());
        newRFQ.setOpportunity(opportunity);

        iAddRFQPresentor.addNewRFQ(newRFQ);
        generateTypingResponce(1000);
        generateChatMessageWithDelay("Thanks for placing order with us ,we were happy to help you",false,true,2000);
        dp = new DetectedProduct();
    }

    @Override
    public void onBackPressed() {

        placeRfq("","");
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        placeRfq("","");
        super.onDestroy();
    }

    private Organization_v3 getOrganizationPayloadEntity(String prodName, String skuCode) {
        Organization_v3 organization = new Organization_v3();
        organization.setCompany_name("JOHN DOE TEST ORG");
        organization.setContactPerson("Dabangg");
        organization.setEmail("john171084@gmail.com");
        organization.setPhone("8555555554");
        organization.setShip_street(
                "qatest93, New Delhi, Delhi, 110020, South West delhi, Delhi, 110022, , South Delhi, Delhi, 110049"
        );
        organization.setShip_pincode("" + dp.pincode);
        organization.setShip_state("Delhi");
        organization.setShip_city("South Delhi");
        organization.setShippingAddressCode("SOU281");
        return organization;
    }

    private Opportunity_v3 getOpportunityPayloadEntity(String prodName,String skuCode) {
        Opportunity_v3 opportunity = new Opportunity_v3();
        if (dp.creditPayment) {
            opportunity.setPaymentTermDays("" + dp.creditDays);
        } else {
            opportunity.setPaymentTermDays("0");
        }
        TaxationPreference_v3 taxationPreference_v3 = new TaxationPreference_v3();
        taxationPreference_v3.setKey("1");
        if(dp.taxPref!=null)
        taxationPreference_v3.setValue(dp.taxPref.toUpperCase() + " Only");
        else
        taxationPreference_v3.setValue("VAT Only");
        opportunity.setTaxationPref(taxationPreference_v3);
        Urgency_v3 urgency_v3 = new Urgency_v3();
        urgency_v3.setKey("1");
        urgency_v3.setValue("Within 48 Hours");
        opportunity.setUrgency(urgency_v3);
        opportunity.setFreightArrangement("false");
        opportunity.setFormC("false");
        ArrayList<OpportunityLine_v3> rfqLineItemList = new ArrayList<OpportunityLine_v3>();

        OpportunityLine_v3 newRFQItem = new OpportunityLine_v3();


        SKU_v3 sku = new SKU_v3();
        sku.setCategory(dp.category);
        sku.setSubcategory(dp.subCategory);
        sku.setSkucode(skuCode);

        newRFQItem.setSku(sku);

        newRFQItem.setRemarks(prodName);
        //Toast.makeText(this,prod.longDescription,Toast.LENGTH_LONG).show();
        newRFQItem.setQuantity("" + dp.qty);
        if (prodName.equalsIgnoreCase("aluminium")) {
            newRFQItem.setUom("Kilograms");
        } else {
            newRFQItem.setUom("Metric Tonnes");
        }

        rfqLineItemList.add(newRFQItem);

        opportunity.setOpportunityLine(rfqLineItemList);
        return opportunity;
    }

    /////////////////////// Base Activity Method Implementation /////////////////////
    @Override
    public int getActivityLayoutResourceId() {
        return R.layout.activity_chat;
    }

    @Override
    public ProgressBar getProgressBar() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    public void showProgress(ProgressTypes progressTypes, int flag) {

    }

    @Override
    public void hideProgress(ProgressTypes progressTypes, int flag) {

    }

    @Override
    public void showUIMessage(UIMessage uiMessage, int flag) {

    }


    /////////////////////// Add RFQ Method Implementation /////////////////////
    @Override
    public void showProgress(BetterSpinner spinner, ProgressBar progressBar, ProgressTypes progressTypes, int flag) {

    }

    @Override
    public void hideProgress(BetterSpinner spinner, ProgressBar progressBar, ProgressTypes progressTypes, int flag) {

    }

    @Override
    public void showUIMessage(BetterSpinner spinner, ProgressBar progressBar, UIMessage uiMessage, int flag) {

    }

    @Override
    public void showUnitsList(String category, BetterSpinner spinner, ProgressBar progressBar, List<UnitOfMeasure> unitsList) {

    }

    @Override
    public void showUrgencyList(BetterSpinner spinner, ProgressBar progressBar, List<Urgency_v3> urgencyList) {

    }

    @Override
    public void showTaxationPrefsList(BetterSpinner spinner, ProgressBar progressBar, List<TaxationPreference_v3> taxationPrefList) {

    }

    @Override
    public void navigateToMyRFQs(final String rfqNo, final String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent intent = new Intent(ChatActivity.this, ChildActivity.class);
                        intent.putExtra(Constants.BUNDLE_KEY_RFQ_NO, rfqNo);
                        intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_myrfq);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivityForResult(intent, ContainerActivity.CHILD_ACTIVITY_REQUEST_CODE);
                    }
                })
                .show();
    }

    @Override
    public void showItemCategoryList(BetterSpinner spinner, ProgressBar progressBar, List<String> categories) {

    }

    @Override
    public void showItemSubCategoryList(BetterSpinner spinner, ProgressBar progressBar, String category, List<String> subCategories) {

    }
}
