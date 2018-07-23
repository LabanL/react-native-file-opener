package com.fileopener;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import org.json.JSONException;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;
import java.util.HashMap;

public class FileOpener extends ReactContextBaseJavaModule {

  public FileOpener(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "FileOpener";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    return constants;
  }

  @ReactMethod
  public void open(String fileArg, String contentType, Promise promise) throws JSONException {
  		File file = new File(fileArg);

  		contentType = getContentType(fileArg);
  		if (file.exists()) {
  			try {
                Uri path = FileProvider.getUriForFile(getReactApplicationContext(),
                  getReactApplicationContext().getPackageName() + ".provider", file);
  				Intent intent = new Intent(Intent.ACTION_VIEW);
  				intent.setDataAndType(path, contentType);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
  				getReactApplicationContext().startActivity(intent);

                promise.resolve("Open success!!");
  			} catch (android.content.ActivityNotFoundException e) {
                promise.reject("Open error!!");
  			}
  		} else {
            promise.reject("File not found");
  		}
  	}

    public String getContentType(String url) {
        String contentType = "*/*";

        if (url.contains(".doc") || url.contains(".docx")) {
            // Word document
            contentType = "application/msword";
        } else if(url.contains(".pdf")) {
            // PDF file
            contentType = "application/pdf";
        } else if(url.contains(".ppt") || url.contains(".pptx")) {
            // Powerpoint file
            contentType = "application/vnd.ms-powerpoint";
        } else if(url.contains(".xls") || url.contains(".xlsx")) {
            // Excel file
            contentType = "application/vnd.ms-excel";
        } else if(url.contains(".rtf")) {
            // RTF file
            contentType = "application/rtf";
        } else if(url.contains(".wav") || url.contains(".mp3")) {
            // WAV audio file
            contentType = "audio/*";
//            contentType = "audio/x-wav";
        } else if(url.contains(".gif")) {
            // GIF file
            contentType = "image/gif";
        } else if(url.contains(".jpg") || url.contains(".jpeg")) {
            // JPG file
            contentType = "image/jpeg";
        } else if(url.contains(".png")) {
            // PNG file
            contentType = "image/png";
        } else if(url.contains(".bmp")) {
            // BMP file
            contentType = "image/*";
        } else if(url.contains(".txt")) {
            // Text file
            contentType = "text/plain";
        } else if(url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")) {
            // Video files
            contentType = "video/*";
        }
        return contentType;
    }

}
