package com.example.merhaba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GirisActivity extends AppCompatActivity {

    private EditText et_email, et_parola;
    private FirebaseAuth mAuth;
    private String email, parola;


    static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);



        mAuth = FirebaseAuth.getInstance();

        et_email = (EditText) findViewById(R.id.email_et);
        et_parola = (EditText) findViewById(R.id.parola_et);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.giris_btn:
                girdileriAl();
                if (girisIzni())
                    girisYap();
                break;
            case R.id.register_tv:
                Intent intent2 = new Intent(this, KayitActivity.class);
                this.startActivity(intent2);
                break;

        }
    }

    private void girdileriAl() {
        email = et_email.getText().toString();
        parola = et_parola.getText().toString();
    }

    private boolean girisIzni() {
        if (gerekliYerlerDoluMu())
            return true;
        else {
            Toast.makeText(this.getApplicationContext(), "Lütfen gerekli alanları doldurun.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean gerekliYerlerDoluMu() {
        if (email.isEmpty() || parola.isEmpty())
            return false;
        else
            return true;
    }

    private void girisYap() {
        mAuth.signInWithEmailAndPassword(email, parola).addOnCompleteListener(GirisActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            MyInfo.my_uid  = task.getResult().getUser().getUid();

                            downloadMe();


                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void downloadMe()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.getValue(User.class).getUid().equals(MyInfo.my_uid)){
                        MyInfo.my_name = ds.getValue(User.class).getAd();
                        MyInfo.my_sur_name = ds.getValue(User.class).getSoyad();
                        MyInfo.my_email = ds.getValue(User.class).getEmail();

                        Intent i = new Intent(GirisActivity.this, InsideActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
