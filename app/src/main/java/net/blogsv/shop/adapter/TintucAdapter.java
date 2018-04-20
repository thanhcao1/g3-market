package net.blogsv.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.blogsv.shop.R;
import net.blogsv.shop.model.Sanpham;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by quang on 11/4/2017.
 */

public class TintucAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraytintuc;

    public TintucAdapter(Context context, ArrayList<Sanpham> arraytintuc) {
        this.context = context;
        this.arraytintuc = arraytintuc;
    }

    @Override
    public int getCount() {
        return arraytintuc.size();
    }

    @Override
    public Object getItem(int i) {
        return arraytintuc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{

        public TextView txttentintuc,txtmotatintuc;
        public ImageView imgtintuc;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder =null;
        if (view ==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_tintuc,null);
            viewHolder.txttentintuc= (TextView) view.findViewById(R.id.tvtitletintuc);
            viewHolder.txtmotatintuc= (TextView) view.findViewById(R.id.tvcontenttintuc);
            viewHolder.imgtintuc= (ImageView) view.findViewById(R.id.imgTintuc);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) view.getTag();
        }
        Sanpham sanpham= (Sanpham) getItem(i);
        viewHolder.txttentintuc.setText(sanpham.getTensanpham());
        viewHolder.txtmotatintuc.setMaxLines(3);
        viewHolder.txtmotatintuc.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotatintuc.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgtintuc);
        return view;
    }
}
