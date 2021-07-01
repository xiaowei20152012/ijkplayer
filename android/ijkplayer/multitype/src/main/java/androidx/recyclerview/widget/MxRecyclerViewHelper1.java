package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;

public class MxRecyclerViewHelper1 {

    public static RecyclerView recyclerView(RecyclerView.ViewHolder holder) {
        return holder.mOwnerRecyclerView;
    }
}
