package net.blogsv.shop.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import net.blogsv.shop.R;
import net.blogsv.shop.model.User;
import net.blogsv.shop.ultil.CheckConnection;
import net.blogsv.shop.ultil.MD5Library;
import net.blogsv.shop.ultil.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    public static User userInfo= new User();
    public static String Avatar= new String();
    private GoogleApiClient mGoogleApiClient;
    int RC_SIGN_IN=001;
    EditText edUser, edpass;
    Button btnLogin, btnReg;
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        edUser.setText(getIntent().getStringExtra("user"));
        edpass.setText(getIntent().getStringExtra("pass"));
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){

            login();
            btnReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent  = new Intent(getApplicationContext(),RegisterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                }
            });
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối");

        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this  /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    private void login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Service.Log, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id;
                        String username;
                        String password;
                        String email;
                        String phone;
                        String diachi;
                        int chucvu;
                        if( response != null && response.length() !=2){
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                id = jsonObject.getInt("id");
                                username= jsonObject.getString("user");
                                password=jsonObject.getString("pass");
                                email=jsonObject.getString("email");
                                phone= jsonObject.getString("phone");
                                diachi=jsonObject.getString("diachi");
                                chucvu=jsonObject.getInt("chucvu");
                                if (password.equalsIgnoreCase(MD5Library.md5(edpass.getText().toString()))){

                                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đăng nhập thành công");
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    userInfo.setId(id);
                                    userInfo.setUsername(username.toString());
                                    userInfo.setPassword(password.toString());
                                    userInfo.setEmail(email.toString());
                                    userInfo.setPhone(phone.toString());
                                    userInfo.setDiachi(diachi.toString());
                                    userInfo.setChucvu(chucvu);
                                    Avatar = "https://exelord.github.io/ember-initials/images/default-d5f51047d8bd6327ec4a74361a7aae7f.jpg";
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);

                                }else {
                                    CheckConnection.ShowToast_Short(getApplicationContext(),"Mật khẩu không chính xác");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Tài khoản không tồn tại!");
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
                        hashMap.put("pass", edpass.getText().toString());
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }

        });

    }

    private void AnhXa() {
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        edUser= (EditText)findViewById(R.id.userlogin);
        edpass=(EditText)findViewById(R.id.passwordlogin);
        btnLogin= (Button)findViewById(R.id.btnlogin);
        btnReg=(Button) findViewById(R.id.btnreg);


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Failed",connectionResult+ "");


    }
    //Funcion đăng nhập
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);



    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
//            case R.id.button_sign_out:
//                signOut();
//                break;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            int id;
            String username;
            String password="";
            String email;
            String phone="";
            String diachi="";
            int chucvu=0;
            GoogleSignInAccount acct = result.getSignInAccount();
            username=acct.getDisplayName();
            email=acct.getEmail();

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            userInfo.setUsername(username.toString());
            userInfo.setPassword(password.toString());
            userInfo.setEmail(email.toString());
            userInfo.setPhone(phone.toString());
            userInfo.setDiachi(diachi.toString());
            userInfo.setChucvu(chucvu);
            Avatar = acct.getPhotoUrl()+"";
//            intent.putExtra("user",username);
//            intent.putExtra("pass",password);
//            intent.putExtra("email",email);
//            intent.putExtra("phone",phone);
//            intent.putExtra("diachi",diachi);
//            intent.putExtra("chucvu",chucvu);
//            intent.putExtra("avatar",(acct.getPhotoUrl()+""));
            CheckConnection.ShowToast_Short(getApplicationContext(),"Đăng nhập thành công");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);




        }
    }
}
