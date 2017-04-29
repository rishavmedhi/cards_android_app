package com.example.rmedhi.chalk_cards_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;


import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private Button mButtonAdd;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final List<Bitmap> cardsList = new ArrayList();

    int w = 100, h = 100;
    Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
    private final Bitmap bmp = Bitmap.createBitmap(w, h, conf);

    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mymenu = getMenuInflater();
        mymenu.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                int position = 0;
                cardsList.add(position,bmp);
                mAdapter.notifyItemInserted(0);
                mRecyclerView.scrollToPosition(position);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request window feature action bar

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
//        mButtonAdd = (Button) findViewById(R.menu.);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        // Define a layout for RecyclerView
        mLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new ViewAdapter(mContext,cardsList);

        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        /* initialising a blank image */
        cardsList.add(bmp);

        // Set a click listener for add item button
//        mButtonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Specify the position
//                int position = 0;
//
//
//                // Add an item to animals list
//                cardsList.add(position,bmp);
//
//                /*
//                    public final void notifyItemInserted (int position)
//                        Notify any registered observers that the item reflected at position has been
//                        newly inserted. The item previously at position is now at position position + 1.
//
//                        This is a structural change event. Representations of other existing items
//                        in the data set are still considered up to date and will not be rebound,
//                        though their positions may be altered.
//
//                    Parameters
//                    position : Position of the newly inserted item in the data set
//
//                */
//
//                // Notify the adapter that an item inserted
////                mAdapter.notifyItemInserted(position);
//
//                /*
//                    public void scrollToPosition (int position)
//                        Convenience method to scroll to a certain position. RecyclerView does not
//                        implement scrolling logic, rather forwards the call to scrollToPosition(int)
//
//                    Parameters
//                    position : Scroll to this adapter position
//
//                */
//                // Scroll to newly added item position
//                mRecyclerView.scrollToPosition(position);
//
//                // Show the added item label
////                Toast.makeText(mContext,"Added : " + itemLabel,Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
