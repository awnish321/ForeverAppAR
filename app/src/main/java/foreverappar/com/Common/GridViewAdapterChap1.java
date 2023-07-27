package foreverappar.com.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import foreverappar.com.foreverappar.R;

public class GridViewAdapterChap1 extends BaseAdapter {

		//Context
	 Context context;

	ArrayList<String> Class_ID;
	ArrayList<String> C_Name;








	public GridViewAdapterChap1(Context context,ArrayList<String> C_id,
								ArrayList<String> Class_name) {
		this.context = context;
		this.Class_ID = C_id;
		this.C_Name = Class_name;

	}






	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("Valueeeecount"+" "+Class_ID.size());

		return Class_ID.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("NewApi") @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View grid;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);





		if (convertView == null) {



			grid = new View(context);
			grid = inflater.inflate(R.layout.gridview, null);
			TextView Class_Name = (TextView) grid.findViewById(R.id.textView1);

            System.out.println("Valueeee"+" "+C_Name.get(position).toString());
			Class_Name.setText(C_Name.get(position).toString());

		} else {
			grid = (View) convertView;
		}

		return grid;






	}

}
