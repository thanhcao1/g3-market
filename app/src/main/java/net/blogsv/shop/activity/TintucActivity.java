package net.blogsv.shop.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.print.PrintJob;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.blogsv.shop.R;
import net.blogsv.shop.adapter.TintucAdapter;
import net.blogsv.shop.model.Sanpham;
import net.blogsv.shop.ultil.CheckConnection;
import net.blogsv.shop.ultil.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TintucActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView lvtt;
    TintucAdapter tintucAdapter;
    ArrayList<Sanpham> mangtt;
    int idtt = 0;
    int page = 1;
    View foodterview;
    boolean isLoading = false;
    mHandler mHandler;
    boolean limitdata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tintuc);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdloaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối ");
            finish();
        }

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

    private void LoadMoreData() {

        lvtt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(getApplicationContext(),TintucChitietActivity.class);
                intent.putExtra("thongtinsanpham",mangtt.get(i));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
        lvtt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView , int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int Firstitem, int VisibleItem, int TotalItem) {
                if (Firstitem + VisibleItem == TotalItem && TotalItem != 0 && isLoading == false && limitdata == false){
                    isLoading = true;
                    ThreadData threadData= new ThreadData();
                    threadData.start();
                }

            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Service.Duongdandienthoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tentt = "";
                int Giatt = 0;
                String Hinhanhtt = "";
                String Mota = "";
                int Idsptt = 0;
                if (response != null && response.length() !=2){
                    lvtt.removeFooterView(foodterview);
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id= jsonObject.getInt("id");
                            Tentt = jsonObject.getString("tensp");
                            Giatt= jsonObject.getInt("giasp");
                            Hinhanhtt=jsonObject.getString("hinhanhsp");
                            Mota=jsonObject.getString("motasp");
                            Idsptt=jsonObject.getInt("idsanpham");
                            mangtt.add(new Sanpham(id,Tentt,Giatt,Hinhanhtt,Mota,Idsptt));
                            tintucAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitdata= true;
                    lvtt.removeFooterView(foodterview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param= new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(idtt));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_out_to_right,R.anim.slide_in_from_left);
            }
        });
    }

    private void GetIdloaisp() {
        idtt= getIntent().getIntExtra("idloaisanpham",-1);

    }

    private void Anhxa() {
        toolbar= (Toolbar) findViewById(R.id.toolbartintuc);
        lvtt =(ListView) findViewById(R.id.listviewtintuc);
        mangtt= new ArrayList<>();
        tintucAdapter = new TintucAdapter(getApplicationContext(),mangtt);
        lvtt.setAdapter(tintucAdapter);
        LayoutInflater inflater =(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        foodterview = inflater.inflate(R.layout.progressbar,null);
        mHandler= new mHandler();
    }

    public class  mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvtt.addFooterView(foodterview);
                    break;
                case 1:

                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message= mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
