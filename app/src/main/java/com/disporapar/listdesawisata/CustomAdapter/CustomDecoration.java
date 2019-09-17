package com.disporapar.listdesawisata.CustomAdapter;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class CustomDecoration extends RecyclerView.ItemDecoration {

    private final int mSpace;

    public CustomDecoration(int mSpace) {
        this.mSpace = mSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = mSpace;
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;
    }
}
