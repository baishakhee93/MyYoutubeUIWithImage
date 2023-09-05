package com.baishakhee.youtube.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.baishakhee.youtube.R;


public class LoaderClass {
    private Context context;
    private ProgressDialog pDialog = null;

    public LoaderClass(Context context) {
        this.context = context;
    }

    public void showLoading() {
        try {
//            pDialog = new ProgressDialog(context);
//            pDialog.setMessage(loadingText);
//            pDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
//            pDialog.show();

            pDialog = new ProgressDialog(context, R.style.TransparentProgressDialog);
            // pDialog = new ProgressDialog(context);
            pDialog.getWindow().setBackgroundDrawable(new
                    ColorDrawable(Color.TRANSPARENT));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.setContentView(R.layout.progres_dialog);
            //pDialog.setMessage("Loading");
            // pDialog.setIndeterminate(true);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();


        } catch (Exception e) {
            Log.d("AlertDialog", "Progress dialog can not be shown");
        }
    }

    public void dismissDailog() {
        if(pDialog!=null&&pDialog.isShowing()){
            pDialog.dismiss();
        }

    }
}
