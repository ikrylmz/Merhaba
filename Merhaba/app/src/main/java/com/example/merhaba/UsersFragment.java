package com.example.merhaba;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {


        RecyclerView rv;
        List<String> userUidList  = new ArrayList<>();
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_users, container, false);

            rv = (RecyclerView)v.findViewById(R.id.rv_User);



        UserDownloader userDownloader = new UserDownloader();
        userDownloader.usersFragment = this;
        userDownloader.downloadAllUser("Users"); // Burda Kaldık,Sorun şu idi : Firebase tablosunda userslerden sonra
        //id ler geliyor.Biz o idleri bilmiyoruz.Dİrek olarak Users'tan dan çekemiyoruz.

        return v;
    }

    public void getAllUser(final List<User> userList)
    {
        RvAdapterUsers.ClickListener listener = new RvAdapterUsers.ClickListener() {

            @Override
            public void onItemClicked(User userData) {

                String uid = userData.getUid();
                Bundle bundle = new Bundle();
                bundle.putString("userUID",uid);
                bundle.putString("userName",userData.getAd());
                Log.i("User uid",uid);

                MessagingFragment  messagingFragment = new MessagingFragment();
                messagingFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,messagingFragment);
                fragmentTransaction.commit();

            }
        };
        showList(userList);
        //addUserToDatabase(userList);
        RvAdapterUsers rvAdapterUsers = new RvAdapterUsers(userList,listener,getActivity().getApplicationContext());
        rv.setAdapter(rvAdapterUsers);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);
        rv.setLayoutManager(llm);
    }

    private void showList(List<User> list)
    {
        for(int i = 0;i<list.size();i++)
        {
            Log.i("list members","userName : "+list.get(i).getAd() +" surName : "+ list.get(i).getSoyad()+
                    " uid : "+ list.get(i).getUid()+ " email : " + list.get(i).getEmail());
        }
    }

}
