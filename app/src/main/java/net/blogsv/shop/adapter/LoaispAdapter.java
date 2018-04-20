package net.blogsv.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.blogsv.shop.R;
import net.blogsv.shop.model.Loaisp;

import java.util.ArrayList;

/**
 * Created by quang on 11/3/2017.
 */

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp>  arrayListloaisps;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> arrayListloaisps, Context context) {
        this.arrayListloaisps = arrayListloaisps;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListloaisps.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListloaisps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txttenloaisp;
        ImageView imgloaisp;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view==null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater = (LayoutInflater ) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.txttenloaisp= view.findViewById(R.id.textviewloaisp);
            viewHolder.imgloaisp= view.findViewById(R.id.imageviewloaisp);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) view.getTag();

        }
        Loaisp loaisp= (Loaisp) getItem(i);
        viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhanhloaisp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgloaisp);
        return view;
    }
}
