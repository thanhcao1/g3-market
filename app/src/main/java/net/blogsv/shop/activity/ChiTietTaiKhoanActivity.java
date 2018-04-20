package net.blogsv.shop.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import net.blogsv.shop.R;
import net.blogsv.shop.ultil.Service;

import java.util.HashMap;
import java.util.Map;

public class ChiTietTaiKhoanActivity extends AppCompatActivity {
    private Dialog dialog;
    TextView tvAccnameTK,tvEmailTK,tvPhoneTK,tvDiachiTK,tvChucvuTK;
    Button btnCapnhatThongtintk,btnClosethongtintk;
    ImageButton btnClearDialog;
    ImageView imgAvatarTK;
    EditText eddialogupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tai_khoan);
        Anhxa();

        tvAccnameTK.setText(LoginActivity.userInfo.getUsername());
        tvEmailTK.setText(LoginActivity.userInfo.getEmail());
        tvPhoneTK.setText(LoginActivity.userInfo.getPhone());
        tvDiachiTK.setText(LoginActivity.userInfo.getDiachi());
        setChucVu();

        Picasso.with(getApplicationContext()).load(LoginActivity.Avatar).into(imgAvatarTK);

        btnClosethongtintk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCapnhatThongtintk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Service.UpdateUser, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("user",LoginActivity.userInfo.getUsername());
                        hashMap.put("pass",LoginActivity.userInfo.getPassword());
                        hashMap.put("email",LoginActivity.userInfo.getEmail());
                        hashMap.put("phone",LoginActivity.userInfo.getPhone());
                        hashMap.put("diachi",LoginActivity.userInfo.getDiachi());
                        hashMap.put("chucvu",LoginActivity.userInfo.getDiachi());
                        return hashMap;
                    }
                };

                requestQueue.add(stringRequest);
                Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();

            }
        });

    }



    private void Anhxa() {
        tvAccnameTK =(TextView)findViewById(R.id.tvAccnameTK);
        tvEmailTK=(TextView)findViewById(R.id.tvEmailTK);
        tvPhoneTK=(TextView)findViewById(R.id.tvPhoneTK);
        tvDiachiTK=(TextView)findViewById(R.id.tvDiachiTK);
        tvChucvuTK=(TextView)findViewById(R.id.tvChucvuTK);
        btnCapnhatThongtintk=(Button)findViewById(R.id.btnCapnhatThongtintk);
        btnClosethongtintk=(Button)findViewById(R.id.btnClosethongtintk);
        imgAvatarTK=(ImageView)findViewById(R.id.imgAvatarTK);

    }
    public void upEmail(View v){
        LayoutInflater inflater = getLayoutInflater();
        View noidung= inflater.inflate(R.layout.dialog_update,null);
        eddialogupdate= (EditText)noidung.findViewById(R.id.eddialogupdate);
        eddialogupdate.setText(LoginActivity.userInfo.getEmail());
        btnClearDialog= (ImageButton) noidung.findViewById(R.id.btnClearDialog);
        btnClearDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eddialogupdate.setText("");
            }
        });
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật Email:");
        builder.setView(noidung);
        builder.setCancelable(false);
        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginActivity.userInfo.setEmail(eddialogupdate.getText().toString());
                finish();
                startActivity(getIntent());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void upPhone(View v){
        LayoutInflater inflater = getLayoutInflater();
        View noidung= inflater.inflate(R.layout.dialog_update,null);
        eddialogupdate= (EditText)noidung.findViewById(R.id.eddialogupdate);
        eddialogupdate.setText(LoginActivity.userInfo.getPhone());
        btnClearDialog= (ImageButton) noidung.findViewById(R.id.btnClearDialog);
        btnClearDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eddialogupdate.setText("");
            }
        });
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật số điện thoại:");
        builder.setView(noidung);
        builder.setCancelable(false);
        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginActivity.userInfo.setPhone(eddialogupdate.getText().toString());
                finish();
                startActivity(getIntent());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void upDiachi(View v){
        LayoutInflater inflater = getLayoutInflater();
        View noidung= inflater.inflate(R.layout.dialog_update,null);
        eddialogupdate= (EditText)noidung.findViewById(R.id.eddialogupdate);
        eddialogupdate.setText(LoginActivity.userInfo.getDiachi());
        btnClearDialog= (ImageButton) noidung.findViewById(R.id.btnClearDialog);
        btnClearDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eddialogupdate.setText("");
            }
        });
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật địa chỉ:");
        builder.setView(noidung);
        builder.setCancelable(false);
        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginActivity.userInfo.setDiachi(eddialogupdate.getText().toString());
                finish();
                startActivity(getIntent());
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void setChucVu() {
        if (LoginActivity.userInfo.getChucvu()==10){
            tvChucvuTK.setText("Thằng Trùm");
        }else if (LoginActivity.userInfo.getChucvu()==9){
            tvChucvuTK.setText("Quản trị viên");
        }else if (LoginActivity.userInfo.getChucvu()==8) {
            tvChucvuTK.setText("Nhân viên");
        }else if (LoginActivity.userInfo.getChucvu()==7){
            tvChucvuTK.setText("Kho xưởng");
        }else {
            tvChucvuTK.setText("Thành viên");}
    }
}
