package se.maj7.fx;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;


public class FXListAdapter extends RecyclerView.Adapter<FXListAdapter.ListItemHolder> {

    private FXListActivity mActivity;

    public FXListAdapter(FXListActivity activity) {

        mActivity = activity;
    }

    @NonNull
    @Override
    public FXListAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fx_list, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        // List item
        //FXListItem item = mList.get(position);

        // Type
        //holder.mType.setText(item.getType());

        // Amount
        //holder.mAmount.setText(String.valueOf(item.getAmount()));

        // Set style for checked/unchecked
        /*if (item.isChecked()) {
            holder.mContainer.setBackgroundResource(R.color.itemBackgroundChecked);
            holder.mType.setTextColor(Color.parseColor("#bababa"));
            holder.mAmount.setTextColor(Color.parseColor("#bababa"));
        } else {
            holder.mContainer.setBackgroundResource(0);
            holder.mType.setTextColor(Color.parseColor("#000000"));
            holder.mAmount.setTextColor(Color.parseColor("#000000"));
        }*/
    }

    @Override
    public int getItemCount() {
        return FXDatabase.shared.getListSize();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mPair;
        TextView mPrice;
        //LinearLayout mContainer;

        public ListItemHolder(View view) {
            super(view);

            mPair = (TextView) view.findViewById(R.id.fxListPairText);
            mPrice = (TextView) view.findViewById(R.id.fxListPriceText);
            //mContainer = (LinearLayout) view.findViewById(R.id.shoppingListItemContainer);

            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //mActivity.showDetailView();
        }
    }
}