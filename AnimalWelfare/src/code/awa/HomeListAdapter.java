package code.awa;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeListAdapter extends BaseAdapter {
	private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    
    public HomeListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.activity_single_homelist, null);

        TextView name = (TextView)vi.findViewById(R.id.name); // roll
        TextView status = (TextView)vi.findViewById(R.id.status); // status        
        
        HashMap<String, String> classes = new HashMap<String, String>();
        classes = data.get(position);
        
        // Setting all values in listview
        name.setText(classes.get(HomeFragment.KEY_NAME));
        status.setText(classes.get(HomeFragment.KEY_STATUS)); 
        return vi;
    }

}
