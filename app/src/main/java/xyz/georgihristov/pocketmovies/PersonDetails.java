package xyz.georgihristov.pocketmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class PersonDetails extends AppCompatActivity{
    private TextView personNameTextView;
    private TextView personBio;
    private TextView birthDateTextView;
    private TextView placeOfBirthTextView;
    private ImageView personImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_details);
        Intent intent = getIntent();
        int id = intent.getIntExtra("PERSONID",0);

        personNameTextView = (TextView) findViewById(R.id.personName);
        personBio = (TextView) findViewById(R.id.personBio);
        birthDateTextView = (TextView) findViewById(R.id.personBirthDate);
        placeOfBirthTextView = (TextView) findViewById(R.id.personPlaceOfBirth);
        personImageView = (ImageView) findViewById(R.id.personImageView);

        new PersonExecutor().execute(String.format(Api.GET_PERSON,id));


    }

    private class PersonExecutor extends AsyncTask<String, Person, Void> {
        final Downloader downloader = new Downloader();

        @Override
        protected Void doInBackground(String... params) {
            String apiToGet = params[0];
            try {
                Person personResult = downloader.personResult(apiToGet);
                Log.d("PERSON`",personResult.getName());
                publishProgress(personResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Person... values) {
            super.onProgressUpdate(values);
            Person person = values[0];
            String url = "https://image.tmdb.org/t/p/w640";
            Picasso.with(PersonDetails.this).load(url+person.getProfilePath()).into(personImageView);
            personNameTextView.setText(person.getName());

            String bio = person.getBiography();
            String birthDate = person.getBirthday();
            String placeOfBirth = person.getPlaceOfBirth();
            if (bio == null || birthDate == null || placeOfBirth == null){
                personBio.setText("No information Available, you can contribute on: \n" +
                        "https://www.themoviedb.org/");
                birthDateTextView.setVisibility(View.INVISIBLE);
                placeOfBirthTextView.setVisibility(View.INVISIBLE);
            }else {
                personBio.setText(person.getBiography());
                birthDateTextView.setText("Birth Date: " + person.getBirthday());
                placeOfBirthTextView.setText("Place of Birth: " + person.getPlaceOfBirth());
            }
        }
    }
}