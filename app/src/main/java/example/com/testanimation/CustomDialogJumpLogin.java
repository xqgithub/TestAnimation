package example.com.testanimation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

/**
 * Created by admin on 2017/6/2.
 */
public class CustomDialogJumpLogin {

    private Context mContext;
    private AlertDialog.Builder builder;


    public CustomDialogJumpLogin(Context context, String title, String message) {
        this.mContext = context;
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("11111", "----->我被点击了！");
            }
        }).show();
    }


}
