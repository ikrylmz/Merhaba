package com.example.merhaba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import java.util.List;

public class InsideActivity extends AppCompatActivity {

        SharedPreferences prefs = null;
        Database database;
        FrameLayout frameLayout;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_inside);
            prefs = getSharedPreferences("com.example.merhaba", MODE_PRIVATE);

            //btn_back_ia = (Button)findViewById( R.id.back_ia_btn);

            //database = new Database(getApplicationContext());
            frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

            UsersFragment usersFragment = new UsersFragment();
            MessageDownloader messageDownloader = new MessageDownloader();
            messageDownloader.usersFragment = usersFragment;
            messageDownloader.insideActivity = this;
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, usersFragment).commit();


        }


        public void onClick(View v) {
            switch (v.getId()) {

                /*
                case R.id.edit_profile_btn:

                    btn_edit_profile.setVisibility(View.INVISIBLE);
                    btn_back_ia.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    ProfileFragment profileFragment = new ProfileFragment();
                    profileFragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction2  = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.frameLayout,profileFragment);
                    fragmentTransaction2.commit();

                    break;
                case R.id.back_ia_btn:

                    btn_back_ia.setVisibility(View.INVISIBLE);
                    btn_edit_profile.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new UsersFragment()).commit();
                    break;
                 */
            }
        }
    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {

            database = new Database(this);
            database.addUser(MyInfo.my_uid,MyInfo.my_name,MyInfo.my_sur_name);

            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }

}
