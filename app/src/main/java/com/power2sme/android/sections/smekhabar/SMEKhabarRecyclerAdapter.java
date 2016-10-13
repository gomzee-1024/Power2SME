package com.power2sme.android.sections.smekhabar;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.power2sme.android.R;
import com.power2sme.android.utilities.logging.ACLogger;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssItem;

import static com.power2sme.android.R.drawable.sme_khabar_thumb_icon;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;

public class SMEKhabarRecyclerAdapter  extends RecyclerView.Adapter<SMEKhabarRecyclerAdapter.ViewHolder> 
{
    public ArrayList<RssItem> data;
//    public DisplayImageOptions options;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener
    {
    	void onItemClick(View v, RssItem rssItem);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
    	this.onItemClickListener=onItemClickListener;
    }
    public SMEKhabarRecyclerAdapter(ArrayList<RssItem> data) 
    {
        this.data = data;
//        options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.news_blank_thumb)
//		.showImageForEmptyUri(R.drawable.news_blank_thumb)
//		.showImageOnFail(R.drawable.news_blank_thumb)
//		.cacheInMemory(true)
//		.cacheOnDisk(true)
//		.considerExifParams(true)
//		.build();
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sme_khabar_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
    	RssItem rssItemObj = data.get(position);
		if(rssItemObj!=null)
		{
			holder.TextView_newsHeading.setText(Html.fromHtml(rssItemObj.getTitle()));
			holder.TextView_newsShortDescription.setText(Html.fromHtml(rssItemObj.getDescription()));

            if(!(rssItemObj.getImageURL()!=null && rssItemObj.getImageURL().length()>0))
            {
                String imageUrl = pullImageLink(rssItemObj.getDescription());
                rssItemObj.setImageURL(imageUrl);
            }
            if(rssItemObj.getImageURL()!=null && rssItemObj.getImageURL().length()>0)
            {
                Picasso.with(holder.itemView.getContext())
                        .load(rssItemObj.getImageURL())
                        .placeholder(sme_khabar_thumb_icon)
                        .error(sme_khabar_thumb_icon)
                        .into(holder.ImageView_thumbImage);
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

    public RssItem getItem(int position) 
    {
        return data.get(position);
    }
    
    public void add(RssItem rssItem) 
    {
        insert(rssItem, data.size());
    }

    public void insert(RssItem rssItem, int position) 
    {
        data.add(position, rssItem);
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

    public void addAll(ArrayList<RssItem> rssItemArrayList) 
    {
        int startIndex = data.size();
        data.addAll(startIndex, rssItemArrayList);
        notifyItemRangeInserted(startIndex, rssItemArrayList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener
    {
    	public ImageView ImageView_thumbImage;
    	public TextView TextView_newsHeading;
    	public TextView TextView_newsShortDescription;
        public ViewHolder(View itemView) 
        {
            super(itemView);
            ImageView_thumbImage = (ImageView)itemView.findViewById(R.id.ImageView_thumbImage);
			TextView_newsHeading = (TextView)itemView.findViewById(R.id.TextView_newsHeading);
			TextView_newsShortDescription = (TextView)itemView.findViewById(R.id.TextView_newsShortDescription);
			itemView.setOnClickListener(this);
        }
		@Override
		public void onClick(View v) 
		{
			int position = getPosition();
			RssItem rssItem = getItem(position);
			
			onItemClickListener.onItemClick(v, rssItem);
		}
    }
    
    ////////////////////////////////////////////////////////////////////
    private String pullImageLink(String encoded) 
	{
	    try 
	    {
	        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	        XmlPullParser xpp = factory.newPullParser();
	        xpp.setInput(new StringReader(encoded));
	        int eventType = xpp.getEventType();
	        while (eventType != XmlPullParser.END_DOCUMENT) 
	        {
	            if (eventType == XmlPullParser.START_TAG && "img".equals(xpp.getName())) 
	            {
	                int count = xpp.getAttributeCount();
	                for (int x = 0; x < count; x++) 
	                {
	                    if (xpp.getAttributeName(x).equalsIgnoreCase("src"))
	                        return xpp.getAttributeValue(x).replaceAll("-110x52", "");
	                }
	            }
	            eventType = xpp.next();
	        }
	    }
	    catch (Exception e) 
	    {
	        ACLogger.log("Error pulling image link from description!\n" + e.getMessage());
	    }
	    return "";
	}
}
