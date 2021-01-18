package com.example.merhaba;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserUploader {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public UserUploader(String uid)
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users").child(uid);
    }
    public void upload(User user)
    {
        databaseReference.setValue(user);
    }
}
















