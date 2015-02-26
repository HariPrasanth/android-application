package code.awa;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryListAdapter extends BaseAdapter {
	private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    
    public GalleryListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.activity_single_gallery, null);

        TextView name = (TextView)vi.findViewById(R.id.iname); 
        TextView status = (TextView)vi.findViewById(R.id.icaption);         
        ImageView image = (ImageView)vi.findViewById(R.id.imagemain);
        HashMap<String, String> classes = new HashMap<String, String>();
        classes = data.get(position);
        
        // Setting all values in listview
        name.setText(classes.get(GalleryFragment.KEY_NAME));
        status.setText(classes.get(GalleryFragment.KEY_CAPTION));
        String imagestr = classes.get(GalleryFragment.KEY_IMAGE);
        byte[] decodedString = Base64.decode(imagestr, Base64.NO_WRAP);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
				0, decodedString.length);
		System.out.println(decodedByte);
		image.getLayoutParams().height = 400;
		image.setImageBitmap(decodedByte);
        return vi;
    }

}
