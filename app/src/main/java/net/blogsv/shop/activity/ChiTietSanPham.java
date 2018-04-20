package net.blogsv.shop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.blogsv.shop.R;
import net.blogsv.shop.model.Giohang;
import net.blogsv.shop.model.Sanpham;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitie;
    TextView txtten, txtgia,txtmota;
    Spinner spinner;
    Button btndatmua;
    int id=0;
    String Tenchitiet="";
    int Giachitiet=0;
    String HinhanhChitiet="";
    String MotaChitiet="";
    int Idsanpham=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolbar();
        Getinformation();
        CatchEventSpinner();
        EventButton();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(), net.blogsv.shop.activity.Giohang.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);

        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.manggiohang.size()>0){
                    int sl= Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exits= false;
                    for(int i=0;i<MainActivity.manggiohang.size();i++){
                        if(MainActivity.manggiohang.get(i).getSoluongsp()== id){
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl);
                            if (MainActivity.manggiohang.get(i).getSoluongsp()>=10){
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(Giachitiet*MainActivity.manggiohang.get(i).getSoluongsp());
                            exits= true;
                        }
                    }
                    if (exits == false) {
                        int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong* Giachitiet;
                        MainActivity.manggiohang.add(new Giohang(id,Tenchitiet,Giamoi,HinhanhChitiet,soluong));
                    }

                }else {
                    int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong* Giachitiet;
                    MainActivity.manggiohang.add(new Giohang(id,Tenchitiet,Giamoi,HinhanhChitiet,soluong));
                }
                Intent intent  = new Intent(getApplicationContext(), net.blogsv.shop.activity.Giohang.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong= new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter= new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void Getinformation() {

        Sanpham sanpham=(Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id= sanpham.getID();
        Tenchitiet= sanpham.getTensanpham();
        Giachitiet= sanpham.getGiasanpham();
        HinhanhChitiet=sanpham.getHinhanhsanpham();
        MotaChitiet=sanpham.getMotasanpham();
        Idsanpham=sanpham.getIDSanpham();
        txtten.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá : "+decimalFormat.format(Giachitiet)+" Đ");
        txtmota.setText(MotaChitiet);
        Picasso.with(getApplicationContext()).load(HinhanhChitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgChitie);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChitiet= (Toolbar) findViewById(R.id.toolbarchitietsanpham);
        imgChitie=(ImageView) findViewById(R.id.imageviewchitietsanpham);
        txtten=(TextView)findViewById(R.id.textviewtenchitietsanpham);
        txtgia=(TextView)findViewById(R.id.textviewgiachitietsanpham);
        txtmota=(TextView)findViewById(R.id.textviewmotachitietsanpham);
        spinner=(Spinner)findViewById(R.id.spinner);
        btndatmua=(Button)findViewById(R.id.buttondatmua);
    }
}
