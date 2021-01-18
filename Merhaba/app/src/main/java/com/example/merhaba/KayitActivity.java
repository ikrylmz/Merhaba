package com.example.merhaba;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class KayitActivity extends AppCompatActivity {

    private EditText et_email,et_parola,et_parola_tk,et_username,et_surname;
    private FirebaseAuth mAuth;
    private String email,parola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        et_email = (EditText) findViewById(R.id.email2_et);
        et_parola = (EditText) findViewById(R.id.parola2_et);
        et_parola_tk = (EditText) findViewById(R.id.parola_tk_et);
        et_username = (EditText)findViewById(R.id.username_et);
        et_surname = (EditText)findViewById(R.id.surname_et);

        mAuth = FirebaseAuth.getInstance();

    }
    private void girdileriAl()
    {
         email = et_email.getText().toString();
         parola = et_parola.getText().toString();
    }
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.kayitOl_btn:
              girdileriAl();
              if(kayitIzini()) {
                  kayitYap();
              }
                break;

            case R.id.back_btn:
                Intent intent2 = new Intent(this, GirisActivity.class);
                this.startActivity(intent2);
                break;
        }
    }
    private boolean kayitIzini()
    {
        if(parolaAyniMi())
           return true;
        else {
            Toast.makeText(this.getApplicationContext(), "Parolanız eşleşmiyor.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    private boolean parolaAyniMi()
    {
        String s1 = et_parola.getText().toString();
        String s2 = et_parola_tk.getText().toString();
        if(s1.equals(s2))
            return true;
        else
            return false;
    }
    private void kayitYap()
    {
        mAuth.createUserWithEmailAndPassword(email,parola)
                .addOnCompleteListener(KayitActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Log.i("Register","Registered ");

                            MyInfo.my_uid = task.getResult().getUser().getUid();
                            MyInfo.my_email = et_email.getText().toString();
                            MyInfo.my_name = et_username.getText().toString();
                            MyInfo.my_sur_name = et_surname.getText().toString();




                            updateData();
                            Intent i = new Intent(getApplicationContext(),InsideActivity.class);
                            //sendDataActivity(i,uid,et_email.getText().toString());
                            startActivity(i);

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Kayıt yapılamadı",Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void updateData()
    {
        User user = new User();
        user.setEmail(MyInfo.my_email);
        user.setUid(MyInfo.my_uid);
        user.setAd(MyInfo.my_name);
        user.setSoyad(MyInfo.my_sur_name);

        UserUploader userUploader = new UserUploader(MyInfo.my_uid);
        userUploader.upload(user);
        Log.i("User Data","User Data is registered and uploaded");
    }

}
