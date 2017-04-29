package com.example.rmedhi.chalk_cards_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
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
        return true;
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

    public class ViewAdapter extends RecyclerView.Adapter<com.example.rmedhi.chalk_cards_1.ViewAdapter.ViewHolder> {
        private List<Bitmap> mDataSet;
        private Context mContext;
        private Random mRandom = new Random();
        private int count=0;
        private int addCardid;

        public ViewAdapter(Context context,List<Bitmap> list){
            mDataSet = list;
            mContext = context;
        }

        /* view holder of the card */
        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mTextView;
            public Button mCameraButton;
            public Button mRemoveButton;
            public Button mGallerybtn;
            public Button mAddButton;
            public RelativeLayout mRelativeLayout;
            public ViewHolder(View v){
                super(v);
                mTextView = (TextView) v.findViewById(R.id.tv);
                mRemoveButton = (Button) v.findViewById(R.id.ib_remove);
                mCameraButton = (Button) v.findViewById(R.id.camera_btn);
                mGallerybtn = (Button) v.findViewById(R.id.gallery_btn);
                mRelativeLayout = (RelativeLayout) v.findViewById(R.id.rl);
                ImageView mImageView = (ImageView) v.findViewById(R.id.imageView);

            }

        }

        @Override
        public com.example.rmedhi.chalk_cards_1.ViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            // Create a new View
            View v = LayoutInflater.from(mContext).inflate(R.layout.custom_view,parent,false);
            com.example.rmedhi.chalk_cards_1.ViewAdapter.ViewHolder vh = new com.example.rmedhi.chalk_cards_1.ViewAdapter.ViewHolder(v);
            return vh;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final com.example.rmedhi.chalk_cards_1.ViewAdapter.ViewHolder holder, final int position) {

            int w = 100, h = 100;
            Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
            final Bitmap bmp = Bitmap.createBitmap(w, h, conf);


            // setting click listener for camera launch and fetch clicked image
            holder.mCameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            });


            // Set a click listener for item remove button
            holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Remove the item on remove/button click
                    mDataSet.remove(position);

                /*
                    public final void notifyItemRemoved (int position)
                        Notify any registered observers that the item previously located at position
                        has been removed from the data set. The items previously located at and
                        after position may now be found at oldPosition - 1.

                        This is a structural change event. Representations of other existing items
                        in the data set are still considered up to date and will not be rebound,
                        though their positions may be altered.

                    Parameters
                        position : Position of the item that has now been removed
                */
                    notifyItemRemoved(position);

                /*
                    public final void notifyItemRangeChanged (int positionStart, int itemCount)
                        Notify any registered observers that the itemCount items starting at
                        position positionStart have changed. Equivalent to calling
                        notifyItemRangeChanged(position, itemCount, null);.

                        This is an item change event, not a structural change event. It indicates
                        that any reflection of the data in the given position range is out of date
                        and should be updated. The items in the given range retain the same identity.

                    Parameters
                        positionStart : Position of the first item that has changed
                        itemCount : Number of items that have changed
                */
                    notifyItemRangeChanged(position,mDataSet.size());

                    // Show the removed item label
//                Toast.makeText(mContext,"Removed : " + itemLabel,Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount(){
            return mDataSet.size();
        }

    }
}
