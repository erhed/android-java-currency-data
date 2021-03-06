package se.maj7.fx;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FXListAdapter extends RecyclerView.Adapter<FXListAdapter.ListItemHolder> {

    private FXListActivity mActivity;

    public FXListAdapter(FXListActivity activity) {

        mActivity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public FXListAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FXDatabase.shared.getListSize()) {
            View itemViewFooter = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fx_list_footer, parent, false);
            return new ListItemHolder(itemViewFooter);
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fx_list, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        if (position != FXDatabase.shared.getListSize()) {
            // List item
            FXListItem item = FXDatabase.shared.getItem(position);

            // Pair title
            String pair = item.getCurrency1() + "/" + item.getCurrency2();
            holder.mPair.setText(pair);

            // Price
            String price = formatPrice(String.valueOf(item.getPrice()));
            holder.mPrice.setText(price);

            // Arrow
            if (item.getIsUp()) {
                holder.mArrow.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.arrow_up));
            } else {
                holder.mArrow.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.arrow_down));
            }
        }
    }

    @Override
    public int getItemCount() {
        return FXDatabase.shared.getListSize() + 1; // +1 for footer
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mPair;
        TextView mPrice;
        ImageView mArrow;

        public ListItemHolder(View view) {
            super(view);

            mPair = (TextView) view.findViewById(R.id.fxListPairText);
            mPrice = (TextView) view.findViewById(R.id.fxListPriceText);
            mArrow = (ImageView) view.findViewById(R.id.fxListArrowImage);

            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mActivity.showDetailView(getAdapterPosition());
        }
    }

    // Price with decimal point should always be 7 chars
    private String formatPrice(String result) {
        String price;
        if (result.length() == 6) {
            price = result + "0";
        } else if (result.length() >= 7) {
            price = result.substring(0, 7);
        } else {
            price = result;
        }
        return price;
    }
}