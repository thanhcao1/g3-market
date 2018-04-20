package net.blogsv.shop.activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import net.blogsv.shop.R;
import net.blogsv.shop.adapter.LoaispAdapter;
import net.blogsv.shop.adapter.SanphamAdapter;
import net.blogsv.shop.model.Giohang;
import net.blogsv.shop.model.Loaisp;
import net.blogsv.shop.model.Sanpham;
import net.blogsv.shop.model.User;
import net.blogsv.shop.ultil.CheckConnection;
import net.blogsv.shop.ultil.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public  static ArrayList<Giohang> manggiohang;
    ImageView imgAvatar;
    TextView tvTenTaiKhoan;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangLoaisps;
    LoaispAdapter loaispAdapter;
    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;
    ArrayList<Sanpham> mangquangcao= new ArrayList<>();
    int id=0;
    String tenloaisp="";
    String hinhanhloaisp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        tvTenTaiKhoan.setText(LoginActivity.userInfo.getUsername());
        Picasso.with(getApplicationContext()).load(LoginActivity.Avatar).into(imgAvatar);
//        getDataUserInfo();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            GetDuLieuLoaisp();
            GetDuLieuSPMoiNhat();
            GetDuLieuTinTuc();
            ActionViewFlipper();
            CatchOnItemListView();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối");
            finish();
        }



    }

//    private void getDataUserInfo() {
//        if ( getIntent().getStringExtra("user") != null){
//            String us= getIntent().getStringExtra("user");
//            String pas= getIntent().getStringExtra("pass");
//            String em= getIntent().getStringExtra("email");
//            String ph= getIntent().getStringExtra("phone");
//            String dia= getIntent().getStringExtra("diachi");
//            int chucv= getIntent().getIntExtra("chucvu",0);
////            userInfo=new User(us,pas,em,ph,dia,chucv);
////            tvTenTaiKhoan.setText(LoginActivity.userInfo.getUsername());
//            String Avatar= getIntent().getStringExtra("avatar");
//            Picasso.with(this).load(Avatar).into(imgAvatar);
//        }
//    }

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
                break;
            case R.id.menucanha:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Thông báo.");
                builder.setIcon(R.drawable.attenion);
                builder.setMessage("Bạn có muốn đăng xuất không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        Intent intent1= new Intent(getApplicationContext(),LoginActivity.class);
                        LoginActivity.userInfo.setUsername("");
                        LoginActivity.userInfo.setPassword("");
                        LoginActivity. userInfo.setEmail("");
                        LoginActivity.userInfo.setPhone("");
                        LoginActivity.userInfo.setDiachi("");
                        LoginActivity.userInfo.setChucvu(0);
                        LoginActivity.Avatar = "";

                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_out_to_right,R.anim.slide_in_from_left);

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;





        }
        return super.onOptionsItemSelected(item);
    }



    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bán hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LapTopActivity.class);
                            intent.putExtra("idloaisanpham",mangLoaisps.get(i).getId());
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bán hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangLoaisps.get(i).getId());
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bán hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,SanPhamActivity.class);
                            intent.putExtra("idloaisanpham",mangLoaisps.get(i).getId());
                            intent.putExtra("title",mangLoaisps.get(i).Tenloaisp);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bán hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,SanPhamActivity.class);
                            intent.putExtra("idloaisanpham",mangLoaisps.get(i).getId());
                            intent.putExtra("title",mangLoaisps.get(i).Tenloaisp);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bán hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,TintucActivity.class);
                            intent.putExtra("idloaisanpham",mangLoaisps.get(i).getId());
                            intent.putExtra("title",mangLoaisps.get(i).Tenloaisp);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bán hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bán hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 7:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bán hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                }
            }
        });
    }

    private void GetDuLieuSPMoiNhat() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Service.Duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    int ID=0;
                    String Tensanpham="";
                    Integer Giasangpham=0;
                    String Hinhanhsanpham="";
                    String Motasanpham="";
                    int IDsanpham=0;
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject= response.getJSONObject(i);
                            ID =jsonObject.getInt("id");
                            Tensanpham= jsonObject.getString("tensp");
                            Giasangpham= jsonObject.getInt("giasp");
                            Hinhanhsanpham= jsonObject.getString("hinhanhsp");
                            Motasanpham=jsonObject.getString("motasp");
                            IDsanpham=jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID,Tensanpham,Giasangpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void GetDuLieuTinTuc() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Service.Duongdantintuc, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){

                    int ID=0;
                    String Tentintuc="";
                    Integer Giatintuc=0;
                    String Hinhanhtintuc="";
                    String Motatintuc="";
                    int IDtintuc=0;

                    for (int i=0;i<response.length();i++){
                        try {

                            JSONObject jsonObject= response.getJSONObject(i);
                            ID =jsonObject.getInt("id");
                            Tentintuc= jsonObject.getString("tensp");
                            Giatintuc= jsonObject.getInt("giasp");
                            Hinhanhtintuc= jsonObject.getString("hinhanhsp");
                            Motatintuc=jsonObject.getString("motasp");
                            IDtintuc=jsonObject.getInt("idsanpham");
                            mangquangcao.add(new Sanpham(ID,Tentintuc,Giatintuc,Hinhanhtintuc,Motatintuc,IDtintuc));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i=0; i<mangquangcao.size();i++){
                        ImageView imageView = new ImageView(getApplicationContext());
                        Picasso.with(getApplicationContext()).load(mangquangcao.get(i).getHinhanhsanpham()).into(imageView);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        viewFlipper.addView(imageView);
                    }



                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);



    }

    private void GetDuLieuLoaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Service.DuongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i =0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id= jsonObject.getInt("id");
                            tenloaisp= jsonObject.getString("tenloaisp");
                            hinhanhloaisp=jsonObject.getString("hinhanhloaisp");
                            mangLoaisps.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangLoaisps.add(6,new Loaisp(0,"Liên hệ","https://hongngochospital.vn/wp-content/themes/flexible/assets/images/icon_phone_01.png"));
                    mangLoaisps.add(7,new Loaisp(0,"Hỗ trợ","http://www.pvhc.net/img8/sbuoyqvnnvddfrtnfiow.png"));

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void ActionViewFlipper() {

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplication(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),TintucChitietActivity.class);
//                CheckConnection.ShowToast_Short(getApplicationContext(),viewFlipper.getDisplayedChild()+"");
                intent.putExtra("thongtinsanpham",mangquangcao.get(viewFlipper.getDisplayedChild()));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);

            }
        });
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }

    private void Anhxa() {
        imgAvatar=(ImageView)findViewById(R.id.imgAvatar);
        tvTenTaiKhoan=(TextView)findViewById(R.id.tvTenTaiKhoan);
        toolbar= (Toolbar)findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewlipper);
        recyclerViewmanhinhchinh=(RecyclerView)findViewById(R.id.recyclerview);
        navigationView=(NavigationView)findViewById(R.id.navigationview);
        listViewmanhinhchinh=(ListView)findViewById(R.id.listviewmanhinhchinh);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mangLoaisps= new ArrayList<>();
        mangLoaisps.add(0, new Loaisp(0,"Trang chủ","http://ducphatdph.com/catalog/view/theme/default/image/home_services_icon.png "));
        loaispAdapter= new LoaispAdapter(mangLoaisps,getApplicationContext());


        listViewmanhinhchinh.setAdapter(loaispAdapter);
        mangsanpham= new ArrayList<>();
        sanphamAdapter= new SanphamAdapter(getApplicationContext(),mangsanpham);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);
        if(manggiohang !=null){

        }else {
            manggiohang= new ArrayList<>();
        }
    }
    public void Chitiettk(View v){
        Intent intent= new Intent(this,ChiTietTaiKhoanActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);

    }

    public void gg(View view){
        Intent intent= new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
