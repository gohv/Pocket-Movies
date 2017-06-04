package xyz.georgihristov.pocketmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FullScreenImage extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView imageView = (ImageView) findViewById(R.id.photo_view);
        Intent intent = getIntent();
        String imagePath = intent.getExtras().getString("IMAGE_");

        Picasso.with(this).load(imagePath).into(imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
