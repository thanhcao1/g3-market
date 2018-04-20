package net.blogsv.shop.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.blogsv.shop.R;
import net.blogsv.shop.adapter.GiohangAdapter;
import net.blogsv.shop.ultil.CheckConnection;
import net.blogsv.shop.ultil.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Giohang extends AppCompatActivity {

    ListView lvgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan,btntieptucmua;
    android.support.v7.widget.Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;
    EditText eddialogupdate;
    ImageButton btnClearDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionToolbar();
        CheckData();
        EventUltil();
        CactchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()>0){
                    if(LoginActivity.userInfo.getDiachi().equals("")){
                        CheckConnection.ShowToast_Short(getApplicationContext(),"Chưa có địa chỉ vui lòng cập nhật địa chỉ");
                        Intent intent = new Intent(getApplicationContext(),ChiTietTaiKhoanActivity.class);
                        startActivity(intent);

                    }else if (LoginActivity.userInfo.getPhone().equals("")){
                        CheckConnection.ShowToast_Short(getApplicationContext(),"Chưa có số điện thoại vui lòng cập nhật số địện thoại");
                    }else {
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest request= new StringRequest(Request.Method.POST, Service.Chitietdonhang, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("1")){
                                    MainActivity.manggiohang.clear();
                                    CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn đã thêm giữ liệu giỏ hàng thành công");
                                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                                    CheckConnection.ShowToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");

                                }else {
                                    CheckConnection.ShowToast_Short(getApplicationContext(),"Dữ liệu của bạn bị đã bị lỗi");
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                JSONArray jsonArray= new JSONArray();
                                for (int i = 0; i < MainActivity.manggiohang.size(); i++){
                                    JSONObject jsonObject= new JSONObject();
                                    try {
                                        jsonObject.put("madonhang",LoginActivity.userInfo.getId());
                                        jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                        jsonObject.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                        jsonObject.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                        jsonObject.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluongsp());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(jsonObject);
                                }

                                HashMap<String,String> hashMap = new HashMap<String, String>();
                                hashMap.put("json",jsonArray.toString());
                                return hashMap;
                            }
                        };
                        queue.add(request);
                    }


                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm để thanh toán");
                }
            }
        });
    }

    private void CactchOnItemListView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Giohang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (MainActivity.manggiohang.size()<=0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.manggiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if (MainActivity.manggiohang.size()<=0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else {
                                txtthongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        long tongtien = 0;
        for (int i= 0;i<MainActivity.manggiohang.size();i++){
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }

        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien)+ " Đ");
    }

    private void CheckData() {
        if (MainActivity.manggiohang.size() <= 0) {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }else {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        lvgiohang=(ListView) findViewById(R.id.listviewgiohang);
        txtthongbao=(TextView)findViewById(R.id.textviewthongbao);
        txttongtien=(TextView)findViewById(R.id.textviewtongtien);
        btnthanhtoan=(Button)findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua=(Button)findViewById(R.id.buttontieptucmuahang);
        toolbargiohang=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbargiohang);
        giohangAdapter= new GiohangAdapter(Giohang.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }
}
