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

public class TintucChitietActivity extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitie;
    TextView txtten,txtmota;
    int id=0;
    String Tenchitiet="";
    String HinhanhChitiet="";
    String MotaChitiet="";
    int Idsanpham=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tintuc_chitiet);
        Anhxa();
        ActionToolbar();
        Getinformation();


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



    private void Getinformation() {

        Sanpham sanpham=(Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id= sanpham.getID();
        Tenchitiet= sanpham.getTensanpham();
        HinhanhChitiet=sanpham.getHinhanhsanpham();
        MotaChitiet=sanpham.getMotasanpham();
        Idsanpham=sanpham.getIDSanpham();
        txtten.setText(Tenchitiet);
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
        txtmota=(TextView)findViewById(R.id.textviewmotachitietsanpham);
    }
}
