package com.example.merhaba;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageUploader {

    public void upload(Message message,String userKey)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        String key = databaseReference.push().getKey();
        DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("Messages").child(userKey).child(key);
        databaseReference2.setValue(message);
    }
}
