package com.notes.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.notes.R;
import com.notes.models.RemoveCallback;

public class SwipeToDelete extends ItemTouchHelper.SimpleCallback {
    private RemoveCallback callback;
    private Drawable background, xMark;
    private int xMarkMargin;

    SwipeToDelete(Context context, RemoveCallback callback) {
        super(0, ItemTouchHelper.LEFT);

        this.callback = callback;
        this.background = new ColorDrawable(Color.RED);
        this.xMark = ContextCompat.getDrawable(context, R.drawable.ic_clear_24dp);
        this.xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        this.xMarkMargin = (int) context.getResources().getDimension(R.dimen.ic_clear_margin);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        callback.onRemove(((NoteListViewHolder) viewHolder).getNote());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder != null) {
            // Если предмет уже был swiped , игнорируйте его
            if (viewHolder.getAdapterPosition() == -1) return;

            int vr = viewHolder.itemView.getRight();
            int vt = viewHolder.itemView.getTop();
            int vb = viewHolder.itemView.getBottom();
            int vh = vb - vt;
            int iw = xMark.getIntrinsicWidth();
            int ih = xMark.getIntrinsicHeight();
            background.setBounds(vr + (int)dX, vt, vr, vb);
            background.draw(c);

            int xml = vr - xMarkMargin - iw;
            int xmr = vr - xMarkMargin;
            int xmt = vt + (vh - ih) / 2;
            int xmb = xmt + ih;
            xMark.setBounds(xml, xmt, xmr, xmb);
            xMark.draw(c);
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
