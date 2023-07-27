package foreverappar.com.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import foreverappar.com.foreverappar.R;

public class GridViewAdapterSub extends BaseAdapter {

		//Context
	private Context context;

	List<SetterGetter_Sub_Chap> setterGetters;
	SetterGetter_Sub_Chap vimSetter;
	boolean array[];







	public GridViewAdapterSub(Context context, List<SetterGetter_Sub_Chap> getters) {
		this.context = context;
		this.setterGetters = getters;
		array = new boolean[getters.size()];
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

		View grid;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		vimSetter = new SetterGetter_Sub_Chap();
		vimSetter = setterGetters.get(position);



		if (convertView == null) {



			grid = new View(context);
			grid = inflater.inflate(R.layout.gridview, null);
			TextView Sub_Name = (TextView) grid.findViewById(R.id.textView1);


			Sub_Name.setText(vimSetter.getSubjectName());

		} else {
			grid = (View) convertView;
		}

		return grid;






	}

}
