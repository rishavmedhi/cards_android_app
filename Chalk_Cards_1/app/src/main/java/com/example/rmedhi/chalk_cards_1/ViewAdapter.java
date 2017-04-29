package com.example.rmedhi.chalk_cards_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
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
    public static class ViewHolder extends RecyclerView.ViewHolder{
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

        }

    }

    @Override
    public ViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_view,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){

        int w = 100, h = 100;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        final Bitmap bmp = Bitmap.createBitmap(w, h, conf);

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
