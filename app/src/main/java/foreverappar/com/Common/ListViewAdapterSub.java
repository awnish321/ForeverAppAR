package foreverappar.com.Common;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import foreverappar.com.foreverappar.R;
import foreverappar.com.ui.ActivityList.UnzipUtil;

public class ListViewAdapterSub extends BaseAdapter {

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;
    private ProgressDialog mProgressDialog;
    String unzipLocation ;
    String zipFile ;
    public static String Value;
    String zip_Name,Str_Chap_Name,Chap_Sub_Name,Str_Class_Name,Str_Sub_Name,Str_Book_Name;
    boolean deleted_zip,deleted_book,delete_activity ;
    LinearLayout Lnr_Dwlnd,Lnr_Play;

    //   private boolean isCanceled;

  /*  File ebook_directory;
    File file_details;
       File file_activities;*/
    //Context
	private Context context;
	List<SetterGetter_Sub_Chap> setterGetters;
	SetterGetter_Sub_Chap vimSetter;
	boolean array[];
	public ListViewAdapterSub(Context context, List<SetterGetter_Sub_Chap> getters) {
		this.context = context;
		this.setterGetters = getters;
		array = new boolean[getters.size()];
		Value= " ";
        // creating connection detector class instance
        cd = new ConnectionDetector(context);
	}


	@Override
	public int getViewTypeCount() {

		return getCount();
	}

	@Override
	public int getItemViewType(int position) {

		return position;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return setterGetters.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return setterGetters.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("NewApi") @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
        View List;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vimSetter = new SetterGetter_Sub_Chap();
		vimSetter = setterGetters.get(position);
		if (convertView == null) {
		    List = new View(context);
			List = inflater.inflate(R.layout.listview_chap, null);
			Lnr_Dwlnd=(LinearLayout) List.findViewById(R.id.Lnr_Download);
            Lnr_Play=(LinearLayout) List.findViewById(R.id.Lnr_Play);
            Button Chap_Name = (Button) List.findViewById(R.id.Btn_Chap_Name);
			Button Chap_Dwnld_Link = (Button) List.findViewById(R.id.Btn_Dwnld);
			Chap_Name.setText(vimSetter.getChapName());
			Chap_Dwnld_Link.setText(vimSetter.getChap_Id());
			Chap_Dwnld_Link.setTag(position);
			Chap_Dwnld_Link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
					String DwlndLink=setterGetters.get(position).getDownload_Link();/*(Integer)v.getTag();*/
                     zip_Name=setterGetters.get(position).getZip_Name();
                     Str_Chap_Name = setterGetters.get(position).getChapName();
                     Str_Class_Name  = setterGetters.get(position).getClassName();
                     Str_Sub_Name = setterGetters.get(position).getSubjectName();
                     Str_Book_Name = setterGetters.get(position).getBookName();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        unzipLocation = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) +"/.foreverappar"+"/"+Str_Class_Name+"/"+Str_Sub_Name+"/"+ Str_Book_Name;
                        Log.d("viii1",""+unzipLocation);
                    }else{
                        unzipLocation = Environment.getExternalStorageDirectory() +"/.foreverappar"+"/"+Str_Class_Name+"/"+Str_Sub_Name+"/"+ Str_Book_Name;
                    }
                    System.out.println("dinaadddaaa" + "  "+ DwlndLink+" "+zip_Name+" "+Str_Class_Name+"  "+unzipLocation);
                    Value=null;
                    // get Internet status
                    isInternetPresent = cd.isConnectingToInternet();
                    // check for Internet status
                    if (isInternetPresent) {
                        // Internet Connection is Present
                        System.out.println("LINKAAAEEE"+"   "+DwlndLink);
                        DownloadMapAsync mew = new DownloadMapAsync();
                        mew.execute(DwlndLink);
                        System.out.println("downloadlinkkaa"+"    "+DwlndLink);
                        Value=zip_Name+".zip";
                        zipFile = unzipLocation+"/"+zip_Name+".zip";
                    } else {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        showAlertDialog(context, "No Internet Connection",
                                "You don't have internet connection.", false);
                    }
                }
            });
		} else {
			List = (View) convertView;
		}
		return List;
	}

    class DownloadMapAsync extends AsyncTask<String, String, String> {
        String result ="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage("Downloading File..");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(false);
		 		/*mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener(){
		            // Set a click listener for progress dialog cancel button
		            @Override
		            public void onClick(DialogInterface dialog, int which){
		                // dismiss the progress dialog
		            	mProgressDialog.dismiss();
		                // Tell the system about cancellation
		                isCanceled = true;
		            }
		        });*/
            mProgressDialog.show();


        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {


                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                int lenghtOfFile = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());

                OutputStream output = new FileOutputStream(zipFile);

                System.out.println("tgtgtgttg"+" "+lenghtOfFile);

                if(lenghtOfFile == 0)
                {
                    showAlertDialog(context, "Error In Internet Connection",
                            "You don't have proper internet connection.", false);
                }




                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;



                    publishProgress(""+(int)((total*100)/lenghtOfFile));

                    System.out.println("fgfffff"+" "+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);


                }
                output.close();
                input.close();
                result = "true";

            } catch (Exception e) {

                result = "false";
            }
            return null;

        }
        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC",progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            mProgressDialog.dismiss();



            if(result.equalsIgnoreCase("true")){
                try {
                    unzip();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else{

            }
        }
    }

    public void unzip() throws IOException {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Please Wait... ");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
				/*mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener(){
		            // Set a click listener for progress dialog cancel button
		            @Override
		            public void onClick(DialogInterface dialog, int which){
		                // dismiss the progress dialog
		            	mProgressDialog.dismiss();
		                // Tell the system about cancellation
		                isCanceled = true;
		            }
		        });*/
        mProgressDialog.show();
        new UnZipTask().execute(zipFile, unzipLocation);
    }


    private class UnZipTask extends AsyncTask<String, Void, Boolean> {
        @SuppressWarnings("rawtypes")
        @Override
        protected Boolean doInBackground(String... params) {
            String filePath = params[0];
            String destinationPath = params[1];

            File archive = new File(filePath);
            try {
                ZipFile zipfile = new ZipFile(archive);
                for (Enumeration e = zipfile.entries(); e.hasMoreElements();) {
                    ZipEntry entry = (ZipEntry) e.nextElement();
                    unzipEntry(zipfile, entry, destinationPath);
                }
                System.out.println("saaaaaaa"+"     "+unzipLocation);
                UnzipUtil d = new UnzipUtil(zipFile, unzipLocation);
                d.unzip();
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mProgressDialog.dismiss();
            File filev;
            Toast.makeText(context, "Books downloading completed...", Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                filev = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), zip_Name+".zip");
                Log.d("viii2",""+filev);
            }else{
                 filev = new File(Environment.getExternalStorageDirectory(), zip_Name+".zip");
            }
            deleted_zip = filev.delete();
            System.out.println("dfdfdfdfdddddd"+"   "+filev);


           /* btn_Download_Book.setVisibility(View.GONE);
            btn_Delete_Book.setVisibility(View.VISIBLE);
            txt_Ebook_msg.setVisibility(View.VISIBLE);*/

            ///  finish

        }


        private void unzipEntry(ZipFile zipfile, ZipEntry entry,
                                String outputDir) throws IOException {

            if (entry.isDirectory()) {
                createDir(new File(outputDir, entry.getName()));
                return;
            }

            File outputFile = new File(outputDir, entry.getName());
            if (!outputFile.getParentFile().exists()) {
                createDir(outputFile.getParentFile());
            }

            // Log.v("", "Extracting: " + entry);
            BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry));
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

            try {

            } finally {
                outputStream.flush();
                outputStream.close();
                inputStream.close();


            }
        }

        private void createDir(File dir) {
            if (dir.exists()) {
                return;
            }
            if (!dir.mkdirs()) {
                throw new RuntimeException("Can not create dir " + dir);
            }
        }}


    @SuppressWarnings("deprecation")
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}
