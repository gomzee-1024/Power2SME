package com.power2sme.android.utilities.media;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACErrorCodes;
import com.power2sme.android.utilities.logging.ACLogger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MediaHelper 
{
	public static final int REQUEST_CODE_CHOOSE_FILE = 0;
	public static final int REQUEST_CODE_IMAGE_CAPTURE = 1;

	private static Uri mImageUri;

//	public static void takePicByCamera(Activity activity)
//	{
//		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//	    if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null)
//	    {
//	    	activity.startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
//	    }
//	}




//	public static void takePicByCamera(Fragment fragment)
//	{
//		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//	    if (takePictureIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null)
//	    {
//	    	fragment.startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
//	    }
//	}

	public static void takePicByCamera(Fragment fragment)
	{
		Activity activity = fragment.getActivity();

		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		File photo;
		try
		{
			// place where to store camera taken picture
			photo = File.createTempFile("photo", ".jpg", activity.getExternalCacheDir());
			photo.setWritable(true, false);

//			photo = createTemporaryFile("picture", ".jpg");
//			photo.delete();

//			photo = createImageFile();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ACLogger.log("Can't create file to take picture!,Please check SD card! Image shot is impossible!");
			return;
		}
		mImageUri = Uri.fromFile(photo);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
		//start camera intent

		if (intent.resolveActivity(fragment.getActivity().getPackageManager()) != null)
		{
			fragment.startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
		}
	}

	private static File createImageFile() throws IOException
	{
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		//mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}

//	public static void takeFileByPicker(Activity activity, String mimeType)
//	{
//		Intent chooserIntent = getTakeFileByPickerIntent(activity, mimeType);
//		if (activity.getPackageManager().resolveActivity(chooserIntent, 0) != null)
//		{
//			try
//	        {
//				activity.startActivityForResult(chooserIntent, REQUEST_CODE_CHOOSE_FILE);
//	        }
//	        catch (Exception ex)
//	        {
//	        	openThirdPartyFileChooser(activity);
////	        	showFileChooser(activity);
//	        }
//		}
//		else
//		{
//			openThirdPartyFileChooser(activity);
////			showFileChooser(activity);
//		}
//	}
	public static void takeFileByPicker(Fragment fragment, String mimeType)
	{
		Intent chooserIntent = getTakeFileByPickerIntent(fragment.getActivity(), mimeType);
		if (fragment.getActivity().getPackageManager().resolveActivity(chooserIntent, 0) != null)
		{
			try 
	        {
				fragment.startActivityForResult(chooserIntent, REQUEST_CODE_CHOOSE_FILE);
	        } 
	        catch (Exception ex) 
	        {
	        	openThirdPartyFileChooser(fragment.getActivity());
//	        	showFileChooser(fragment.getActivity());
	        }
		}
		else
		{
			openThirdPartyFileChooser(fragment.getActivity());
//			showFileChooser(fragment.getActivity());
		}
	}
	
	private static void openThirdPartyFileChooser(Activity activit)
	{
		Intent getContentIntent = com.ipaulpro.afilechooser.utils.FileUtils.createGetContentIntent();
//	    Intent intent = Intent.createChooser(getContentIntent, activit.getResources().getString(R.string.myorder_label_uploadpodialog));
		Intent intent = Intent.createChooser(getContentIntent, "Select a file");
	    activit.startActivityForResult(intent, REQUEST_CODE_CHOOSE_FILE);
	}

	public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, IOnActivityResultCallback callback) 
	{
		if(resultCode == Activity.RESULT_OK)
		{
		    switch (requestCode) 
		    {
		        case REQUEST_CODE_CHOOSE_FILE:   
		        {
		        	try
		        	{
		        		final Uri uri = data.getData();
		        		
				        String path = com.ipaulpro.afilechooser.utils.FileUtils.getPath(activity, uri);
				        if (path != null && new File(path).exists()) 
				        {
				        	File file = new File(path);
				        	callback.onSuccess(file, REQUEST_CODE_CHOOSE_FILE);
				        	return;
				        }
		        	}
		        	catch(Exception ex)
		        	{
		        		ex.printStackTrace();
		        	}
			        break;
		        }
		        case REQUEST_CODE_IMAGE_CAPTURE:
		        {
					if(mImageUri!=null)
					{
//						activity.getContentResolver().notifyChange(mImageUri, null);
//						ContentResolver cr = activity.getContentResolver();
//						Bitmap bitmap;
						try
						{
							File rcvFile = new File(mImageUri.getPath());
							if(rcvFile.exists())
							{
								callback.onSuccess(rcvFile, REQUEST_CODE_CHOOSE_FILE);
							}
//							bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
//							callback.onSuccess(bitmap, REQUEST_CODE_CHOOSE_FILE);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					return;
		        }
		    }			
		}
		ACError error=new ACError(ACErrorCodes.APP_FUNCTIONING_ERROR , "Media helper onActivityResult error.");
		callback.onError(error);
	}

	private static Intent getTakeFileByPickerIntent(Context context, String mimeType) 
	{
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mimeType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        sIntent.putExtra("CONTENT_TYPE", mimeType); 
        sIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Intent chooserIntent;
        if (context.getPackageManager().resolveActivity(sIntent, 0) != null)
        {
            chooserIntent = Intent.createChooser(sIntent, "Open file");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
        }
        else 
        {
            chooserIntent = Intent.createChooser(intent, "Open file");
        }
        return chooserIntent;
	}

//	private static File createTemporaryFile(String part, String ext) throws Exception
//	{
//		File tempDir= Environment.getExternalStorageDirectory();
//		tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
//		if(!tempDir.exists())
//		{
//			tempDir.mkdir();
//		}
//		return File.createTempFile(part, ext, tempDir);
//	}
}
