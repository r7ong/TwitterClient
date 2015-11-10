package com.codepath.apps.twitter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ComposeActivity extends AppCompatActivity {

    ImageView ivCmpProfileImage;
    EditText etCompose;
    TextView tvCmpName;
    TextView tvCmpUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#50ABF1")));
        ivCmpProfileImage = (ImageView) findViewById(R.id.ivCmpProfileImage);
        etCompose = (EditText) findViewById(R.id.etCompose);
        tvCmpName = (TextView) findViewById(R.id.tvCmpName);
        tvCmpUserName = (TextView) findViewById(R.id.tvCmpUserName);

        tvCmpName.setText("桔梗店老板");
        tvCmpUserName.setText("@kikyo0o");
        String imgUrl = "https://pbs.twimg.com/profile_images/558541794829299712/WrO1V1wl.jpeg";
        Picasso.with(this).load(imgUrl).into(ivCmpProfileImage);

//        int age = getIntent().getIntExtra("age", 0);
//        User user = getIntent().getSerializableExtra("age"); //for complex object data
//        etAge.setText(String.valueOf(age));//user.age
//        btnSubmit = (Button) findViewById(R.id.btnSubmit);
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                onSubmit(v);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    public void onSubmit() {
        /*  either setup setOnClickListener or add "android:onClick="onSubmit" in layout
            http://stackoverflow.com/questions/4153517/how-exactly-does-the-androidonclick-xml-attribute-differ-from-setonclicklistene
         */
        // 1. get new tweet
        String newTweet = etCompose.getText().toString();
        // 2. construct data intent
        Intent data = new Intent();
        // 3. send data in a bundle
        data.putExtra("newTweet", newTweet);
        // 4. set result
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mnuTweet) {
            Toast.makeText(ComposeActivity.this, "Tweeted!", Toast.LENGTH_SHORT).show();
            onSubmit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
