package net.blogsv.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.blogsv.shop.R;
import net.blogsv.shop.activity.MainActivity;
import net.blogsv.shop.model.Giohang;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by quang on 11/6/2017.
 */

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrayGiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> arrayGiohang) {
        this.context = context;
        this.arrayGiohang = arrayGiohang;
    }

    @Override
    public int getCount() {
        return arrayGiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayGiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus,btnnvalues,btnplus;

    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

       ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txtgiagiohang= (TextView)view.findViewById(R.id.textviewgiagiohang);
            viewHolder.txttengiohang=(TextView)view.findViewById(R.id.textviewtengiohang);
            viewHolder.imggiohang=(ImageView) view.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus=(Button)view.findViewById(R.id.buttonminus);
            viewHolder.btnnvalues=(Button)view.findViewById(R.id.buttonvalues);
            viewHolder.btnplus=(Button)view.findViewById(R.id.buttonplus);
            view.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) view.getTag();

        }
        Giohang giohang= (Giohang) getItem(i);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp())+ " Đ");
        Picasso.with(context).load(giohang.getHinhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imggiohang);
        viewHolder.btnnvalues.setText(giohang.getSoluongsp()+ "");
        int sl= Integer.parseInt(viewHolder.btnnvalues.getText().toString());
        if (sl>=10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if (sl<=1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if (sl>=1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnnvalues.getText().toString())+1;
                int slhientai= MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht= MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat= (giaht*slmoinhat)/slhientai;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+ " Đ");
                net.blogsv.shop.activity.Giohang.EventUltil();
                if (slmoinhat> 9){
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnnvalues.setText(String.valueOf(slmoinhat));

                }else {
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnnvalues.setText(String.valueOf(slmoinhat));
                }


            }
        }) ;

        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnnvalues.getText().toString())-1;
                int slhientai= MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht= MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat= (giaht*slmoinhat)/slhientai;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+ " Đ");
                net.blogsv.shop.activity.Giohang.EventUltil();
                if (slmoinhat<2){
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnnvalues.setText(String.valueOf(slmoinhat));

                }else {
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnnvalues.setText(String.valueOf(slmoinhat));
                }


            }
        }) ;
        return view;
    }
}
