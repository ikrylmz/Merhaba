package com.example.merhaba;

import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MessagingFragment extends Fragment {

    String userUID,userName;
    EditText et_typing;
    TextView txt_name;
    Button btn_send,btn_back_messaging;
    LinearLayout lyt_messages;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_messaging, container, false);

        userUID = getArguments().getString("userUID");
        userName = getArguments().getString("userName");
        txt_name = (TextView)v.findViewById(R.id.name_txt);
        txt_name.setText(userName);

        lyt_messages = v.findViewById(R.id.messages_lyt);



        btn_back_messaging = (Button)v.findViewById(R.id.back_messaging_btn);
        btn_back_messaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UsersFragment  usersFragment = new UsersFragment();
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,usersFragment);
                fragmentTransaction.commit();

            }
        });

        et_typing = (EditText)v.findViewById(R.id.typing_et);
        btn_send = (Button)v.findViewById(R.id.send_btn);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadMessage(userUID);
            }
        });

        downloadMessage(MyInfo.my_uid);
        return v;
    }

    private Message setMessage()
    {
        Message message = new Message();
        message.setText(et_typing.getText().toString());
        message.setSenderID(MyInfo.my_uid);
        message.setUserName(MyInfo.my_name);
        message.setReceiverID(userUID);
        return message;
    }
    private void uploadMessage(String usersKey)
    {
        Message message = setMessage();
        productView(message.getUserName(),message.getText(),false);
        MessageUploader messageUploader = new MessageUploader();
        messageUploader.upload(message,usersKey);
    }

    private void downloadMessage(String myUid)
    {
        MessageDownloader messageDownloader = new MessageDownloader();
        messageDownloader.messagingFragment = this;
        messageDownloader.downloadMessage(myUid);
    }

    public  void productView(String userName,String text,Boolean isStranger)
    {
        LinearLayout rootView = new LinearLayout(this.getContext());
        rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rootView.setOrientation(LinearLayout.HORIZONTAL);

        TextView userNameTxt = new TextView(getContext());

        userNameTxt.setTextSize(28f);
        if(isStranger) {
            userNameTxt.setText(userName + " :");
            userNameTxt.setTextColor(Color.RED);
        }
        else {
            userNameTxt.setText(MyInfo.my_name+ " :");
            userNameTxt.setTextColor(Color.BLUE);
        }

        userNameTxt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        rootView.addView(userNameTxt);

        TextView textTxt = new TextView(getContext());
        textTxt.setText(text);
        textTxt.setTextSize(18f);
        textTxt.setTextColor(Color.BLACK);
        textTxt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.4f));
        rootView.addView(textTxt);

        lyt_messages.addView(rootView);

    }

}

