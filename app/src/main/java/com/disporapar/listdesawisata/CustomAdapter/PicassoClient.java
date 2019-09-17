package com.disporapar.listdesawisata.CustomAdapter;

import android.content.Context;
import android.widget.ImageView;

import com.disporapar.listdesawisata.R;
import com.squareup.picasso.Picasso;

public class PicassoClient {

    public static void downloadImage(Context c, String urlAddress, ImageView imageList) {
        if (urlAddress != null && urlAddress.length() > 0) {
            Picasso
                    .with(c)
                    .load(urlAddress)
                    .placeholder(R.drawable.invisible)
                    .into(imageList);
        } else {
            Picasso.with(c).load(R.drawable.invisible).into(imageList);
        }
    }
}
