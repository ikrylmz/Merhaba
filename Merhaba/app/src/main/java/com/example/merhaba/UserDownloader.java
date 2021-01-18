package com.example.merhaba;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class UserDownloader {

    private FirebaseDatabase firebaseDatabase;

    UsersFragment usersFragment;

    List<User> userList = new ArrayList<>();


    public void downloadAllUser(String path)
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(!(ds.getValue(User.class).getUid().equals(MyInfo.my_uid)))
                        userList.add(ds.getValue(User.class));


                }
                usersFragment.getAllUser(userList);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
