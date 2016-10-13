package com.power2sme.android.utilities.styels;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.power2sme.android.R;

public class StylesManager 
{
	private Typeface typefaceRobotoRegular;
	private Typeface typefaceRobotoMedium;
	
	private static StylesManager stylesManager;
	Context context;
	public static StylesManager getInstance(Context context)
	{
		if(stylesManager==null)
		{
			stylesManager=new StylesManager(context);
		}
		return stylesManager;
	}
	
	private StylesManager(Context context)
	{
		this.context = context;
	}
	
	public void initTypefaces()
	{
		typefaceRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Regular.ttf");
		typefaceRobotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Medium.ttf");
	}
	
	public Typeface getFontTypeface(AppliedFont appliedFont)
	{
		switch(appliedFont)
		{
			case ROBOTO_REGULAR:
				return typefaceRobotoRegular;
			case ROBOTO_MEDIUM:
				return typefaceRobotoMedium;
			default:
				return typefaceRobotoRegular;
		}
	}
	public void setButtonStyle(Button view, StyleTypes styleTypes)
	{
		switch(styleTypes)
		{
			case Button_SquareYellow:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_MEDIUM));
				view.setTextAppearance(context, R.style.Style_Button_SquareYellow);
				break;
			}
			case Button_SquareGray:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_MEDIUM));
				view.setTextAppearance(context, R.style.Style_Button_SquareGray);
				break;
			}
			case Button_topHeadTab_Selected:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_MEDIUM));
				view.setTextAppearance(context, R.style.Style_Button_topHeadTab_Selected);
				break;
			}
			case Button_topHeadTab_Unselected:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_MEDIUM));
				view.setTextAppearance(context, R.style.Style_Button_topHeadTab_Unselected);
				break;
			}
		}
	}
	public void setTextViewStyle(TextView view, StyleTypes styleTypes)
	{
		if(view==null)
			return;
		
		switch(styleTypes)
		{
			case TextView_menu:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_MEDIUM));
				view.setTextAppearance(context, R.style.Style_TextView_menu);
				break;
			}
			case TextView_caption:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_caption);
				break;
			}
			case TextView_body1:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_body1);
				break;
			}
			case TextView_body1_WhiteTextColor:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_body1);
				break;
			}
			case TextView_body1_accent1:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_body1_accent1);
				break;
			}
			case TextView_body1_accent2:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_body1_accent2);
				break;
			}
			case TextView_body1_accent3:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_body1_accent3);
				break;
			}
			case TextView_body1_light:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_body1_light);
				break;
			}
			case TextView_body2:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_MEDIUM));
				view.setTextAppearance(context, R.style.Style_TextView_body2);
				break;
			}
			case TextView_Subhead1:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_Subhead1);
				break;
			}
			case TextView_Subhead1_accent1:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_Subhead1_accent1);
				break;
			}
			case TextView_Subhead2:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_MEDIUM));
				view.setTextAppearance(context, R.style.Style_TextView_Subhead2);
				break;
			}
			case TextView_title:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_MEDIUM));
				view.setTextAppearance(context, R.style.Style_TextView_title);
				break;
			}
			case TextView_headline:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_headline);
				break;
			}
			
			
			case TextView_display1:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_display1);
				break;
			}
			case TextView_display2:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_display2);
				break;
			}
			case TextView_display3:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_display3);
				break;
			}
			case TextView_display4:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_MEDIUM));
				view.setTextAppearance(context, R.style.Style_TextView_display4);
				break;
			}
			case TextView_shipment_Status:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_TextView_shipment_Status);
				break;
			}
		};
	}
	
	public void setViewStyle(View view, StyleTypes styleTypes)
	{
		switch(styleTypes)
		{
			case View_divider:
			{
				break;
			}
			case View_sectionDivider:
			{
				break;
			}
		}
	}
	public void setEditTextStyle(EditText view, StyleTypes styleTypes)
	{
		switch(styleTypes)
		{
			case EditText:
			{
				view.setTypeface(getFontTypeface(AppliedFont.ROBOTO_REGULAR));
				view.setTextAppearance(context, R.style.Style_EditText);
				break;
			}
		}
	}
	public void setImageViewStyle(ImageView view, StyleTypes styleTypes)
	{
		switch(styleTypes)
		{
			case ImageView_listItemBullets:
			{
				break;
			}
		}
	}
	public void setSpinnerStyle(Spinner view, StyleTypes styleTypes)
	{
		switch(styleTypes)
		{
			case Spinner:
			{
				break;
			}
		}
	}
}
