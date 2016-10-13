package com.power2sme.android.sections.myrfqs.add.typeahead;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ProgressBar;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power2sme.android.R;
import com.power2sme.android.conf.APIs;
import com.power2sme.android.dataprovider.network.MethodTypes;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.dtos.response.ResponseSKUsFetchDto;
import com.power2sme.android.entities.v3.SKU_v3;
import com.power2sme.android.sections.myrfqs.add.AddRFQFragment;
import com.power2sme.android.utilities.customviews.BetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tausif on 22/6/15.
 */
public class SuggestionAdapter extends ArrayAdapter<SKU_v3>
{
    protected static final String TAG = "SuggestionAdapter";
    Activity context;
    BetterSpinner cat;
    BetterSpinner subCat;
    ProgressBar ProgressBar_materialSpecificationLoader;
    private List<SKU_v3> suggestions;
    private ObjectMapper objectMapper = null;
    private JsonFactory jsonFactory = null;
    private boolean isQueryActive=false;
    public SuggestionAdapter(Activity context, ProgressBar ProgressBar_materialSpecificationLoader,String nameFilter, BetterSpinner cat, BetterSpinner subCat)
    {
        super(context, android.R.layout.simple_dropdown_item_1line);
        suggestions = new ArrayList<SKU_v3>();
        this.context=context;
        this.ProgressBar_materialSpecificationLoader=ProgressBar_materialSpecificationLoader;
        this.cat=cat;
        this.subCat=subCat;

        objectMapper = new ObjectMapper();
        objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        jsonFactory = new JsonFactory();
    }

    @Override
    public int getCount()
    {
        return suggestions.size();
    }

    @Override
    public SKU_v3 getItem(int index)
    {
        return suggestions.get(index);
    }

    @Override
    public Filter getFilter()
    {
        Filter myFilter = new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                FilterResults filterResults = new FilterResults();

                if(AddRFQFragment.isSKUSelectionPerformed)
                {
                    AddRFQFragment.isSKUSelectionPerformed=false;
                    return filterResults;
                }

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressBar_materialSpecificationLoader.setVisibility(ProgressBar.VISIBLE);
                    }
                });


                if (constraint != null && !isQueryActive)
                {
                    if(cat.getText()!=null && subCat.getText()!=null)
                    {
                        List<SKU_v3> new_suggestions = getSKUs(cat.getText().toString(), subCat.getText().toString(),constraint.toString());
                        if(new_suggestions!=null && new_suggestions.size()>0)
                        {
                            suggestions.clear();
                            suggestions.addAll(new_suggestions);
                            filterResults.values = suggestions;
                            filterResults.count = suggestions.size();
                        }
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint, FilterResults results)
            {
                if (results != null && results.count > 0)
                {
                    notifyDataSetChanged();
                }
                else
                {
                    notifyDataSetInvalidated();
                }

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressBar_materialSpecificationLoader.setVisibility(ProgressBar.GONE);
                    }
                });

            }
        };
        return myFilter;
    }

    private List<SKU_v3> getSKUs(String category, String subCategory, String longdesc)
    {
        isQueryActive=true;
        List<SKU_v3> skuList =null;
        try
        {
            Map params=new HashMap<String, String>();

            if(category!=null && category.trim().length()>0 && category.indexOf(context.getString(R.string.addrfq_hint_sku_category))==-1)
            {
                params.put("category", category);
            }

            if(subCategory!=null && subCategory.trim().length()>0 && subCategory.indexOf(context.getString(R.string.addrfq_hint_sku_sub_category))==-1)
            {
                params.put("subcategory", subCategory);
            }

            longdesc = longdesc.trim();
            longdesc = longdesc.replaceAll("  ", " ");
            longdesc = longdesc.replaceAll(" ", ",");

            params.put("longdesc", longdesc);
            params.put("pageSize", ""+100);
            params.put("pageIndex", ""+0);

            NetworkAsyncTaskResponse<String> response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_SKU_LIST), params);
            if(response.getResultObject()!=null)
            {
                JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
                ResponseSKUsFetchDto responseSKUsFetchDto = objectMapper.readValue(jp, ResponseSKUsFetchDto.class);
                skuList = responseSKUsFetchDto.getSKUs();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        isQueryActive=false;
        return skuList;
    }
}
