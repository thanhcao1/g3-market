package net.blogsv.shop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.blogsv.shop.R;
import net.blogsv.shop.model.User;
import net.blogsv.shop.ultil.CheckConnection;
import net.blogsv.shop.ultil.MD5Library;
import net.blogsv.shop.ultil.Service;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText edUser, edpassword,edemail,edphone,eddiachi;
    private Button btnDangKy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Anhxa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();

        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối");
            finish();
        }


    }

    private void EventButton() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = edUser.getText().toString();
                final String pass = MD5Library.md5(edpassword.getText().toString());
                final String email= edemail.getText().toString();
                final String phone= edphone.getText().toString();
                final String diachi=eddiachi.getText().toString();
                final String chucvu= "0";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Service.Log, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if( response != null && response.length() !=2){
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Tài khoản đã tồn tại!");



                        }else {
                            //Thực hiện đăng ký
                            if (user.length()>0 && pass.length()>0 && email.length()>0 && phone.length()>0 && diachi.length()>0 && chucvu.length()>0){
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Service.Reg, new Response.Listener<String>() {
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
                                        hashMap.put("user",user);
                                        hashMap.put("pass",pass);
                                        hashMap.put("email",email);
                                        hashMap.put("phone",phone);
                                        hashMap.put("diachi",diachi);
                                        hashMap.put("chucvu",chucvu);
                                        return hashMap;
                                    }
                                };

                                requestQueue.add(stringRequest);
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                intent.putExtra("user",user);
                                intent.putExtra("pass",edpassword.getText().toString());
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                                edUser.setText("");
                                edpassword.setText("");
                                edemail.setText("");
                                eddiachi.setText("");
                                edphone.setText("");
                            }else {
                                CheckConnection.ShowToast_Short(getApplicationContext(),"Không được để trống");
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("user", edUser.getText().toString());
                        hashMap.put("pass", edpassword.getText().toString());
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);


            }
        });
    }

    private void Anhxa() {
        edUser=(EditText)findViewById(R.id.edUserreg);
        edpassword=(EditText) findViewById(R.id.edPassreg);
        edemail=(EditText) findViewById(R.id.edEmailreg);
        edphone=(EditText)findViewById(R.id.edPhonereg);
        eddiachi=(EditText)findViewById(R.id.edDiachireg);
        btnDangKy=(Button)findViewById(R.id.btnDangky);

    }


}
