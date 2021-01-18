package com.example.merhaba;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessageDownloader {

    private Message message;
    MessagingFragment messagingFragment;
    InsideActivity insideActivity;
    UsersFragment usersFragment;

    public void downloadMessage(String uid)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Messages").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    Message message = ds.getValue(Message.class);
                    messagingFragment.productView(message.getUserName(),message.getText(),true);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Message getMessage()
    {
        return  message;
    }
}
