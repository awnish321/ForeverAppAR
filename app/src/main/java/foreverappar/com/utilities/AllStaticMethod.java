package foreverappar.com.utilities;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import foreverappar.com.foreverappar.R;

public class AllStaticMethod {
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo wifiInfo, mobileInfo;
        boolean connected = false;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        return connected;
    }
    public static void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
    public static void logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("RSAR_APP",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.clear();
        myEdit.apply();
    }
     public static String capitalizeWord(String str){
            String[] words =str.split("\\s");
            StringBuilder capitalizeWord= new StringBuilder();
            for(String w:words){
                String first=w.substring(0,1);
                String afterfirst=w.substring(1);
                capitalizeWord.append(first.toUpperCase()).append(afterfirst).append(" ");
            }
            return capitalizeWord.toString().trim();
        }

}
