package net.blogsv.shop.adapter;

import android.content.Context;
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
 * Created by quang on 11/17/2017.
 */

public class SPAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SPAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @Override
    public int getCount() {
        return arraysanpham.size();
    }

    @Override
    public Object getItem(int i) {
        return arraysanpham.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{

        public TextView txttensanpham,txtgiasanpham,txtmotasanpham;
        public ImageView imgsanpham;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        SPAdapter.ViewHolder viewHolder =null;
        if (view ==null){
            viewHolder = new SPAdapter.ViewHolder();
            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_sanpham,null);
            viewHolder.txttensanpham= (TextView) view.findViewById(R.id.textviewsanpham);
            viewHolder.txtgiasanpham= (TextView) view.findViewById(R.id.textviewgiasanpham);
            viewHolder.txtmotasanpham= (TextView) view.findViewById(R.id.textviewmotasanpham);
            viewHolder.imgsanpham= (ImageView) view.findViewById(R.id.imageviewsanpham);
            view.setTag(viewHolder);
        }else {
            viewHolder=(SPAdapter.ViewHolder) view.getTag();
        }
        Sanpham sanpham= (Sanpham) getItem(i);
        viewHolder.txttensanpham.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiasanpham.setText("Giá : "+decimalFormat.format(sanpham.getGiasanpham())+" Đ");
        viewHolder.txtmotasanpham.setMaxLines(2);
        viewHolder.txtmotasanpham.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotasanpham.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgsanpham);
        return view;
    }
}