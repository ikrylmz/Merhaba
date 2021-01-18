package com.example.merhaba;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MenuActivity extends AppCompatActivity {

    TextView txt;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        txt = (TextView)findViewById(R.id.open);
        image = (ImageView)findViewById(R.id.imageView3);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button2:
                // galeriyiAc();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl(
                        "gs://merhaba-3afca.appspot.com/ProfilePhotos/MAPrB7PJPMWpYTqnKpsIbKZbUhr2");

                Toast.makeText(this,"path :    " + storageRef.getMetadata().getResult(),Toast.LENGTH_LONG).show();
                break;

            case R.id.button3:

                Toast.makeText(this,"Tıklandır",Toast.LENGTH_LONG).show();

                /*
                fileDownloader = new FileDownloader("gs://merhaba-3afca.appspot.com/ProfilePhotos/MAPrB7PJPMWpYTqnKpsIbKZbUhr2");
                fileDownloader.textView = txt;
                fileDownloader.fileDownload();
                */
            break;

            case R.id.button4:
              //   Bitmap bitmap = BitmapFactory.decodeFile(fileDownloader.getFile().getPath());
              //   image.setImageBitmap(bitmap);
                break;
        }
    }

    private void galeriyiAc() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

}
