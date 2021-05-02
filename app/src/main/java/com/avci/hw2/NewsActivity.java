package com.avci.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avci.hw2.data.ImageReplacement;
import com.avci.hw2.data.Utility;
import com.avci.hw2.data.entities.Item;

public class NewsActivity extends AppCompatActivity {

    Intent intent;
    Item selectedNewsItem;
    TextView articleTV, notifyTV, titleTV;
    ImageButton addBtn;
    ImageView newsImage;
    FrameLayout frameLayout;
    ImageReplacement ir;
    private GestureDetectorCompat gDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Declarations
        articleTV = findViewById(R.id.articleTV);
        notifyTV = findViewById(R.id.notifyTV);
        titleTV = findViewById(R.id.titleTV);
        addBtn = findViewById(R.id.addBtn);
        newsImage = findViewById(R.id.newsImage);
        frameLayout = findViewById(R.id.frameLayout);
        NewsGestureListener ngl = new NewsGestureListener();
        gDetector = new GestureDetectorCompat(this, ngl);

        // Intent Item Transfer
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        selectedNewsItem = b.getParcelable("itemsKey");

        // Setting the View
        ir = new ImageReplacement(this);
        ir.loadImage(selectedNewsItem.thumbnail, newsImage);
        titleTV.setText(selectedNewsItem.title);
        // Html Parse
        articleTV.setText(Utility.htmlToText(selectedNewsItem.content));


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, FeedHolderActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("savedItems", selectedNewsItem);
                intent.putExtras(b);
                startActivity(intent);

            }
        });

        newsImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gDetector.onTouchEvent(event);
            }
        });

    }


    class NewsGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Toast.makeText(NewsActivity.this, "U have Invoked Double Tap Event!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(NewsActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
    }




}