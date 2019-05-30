package se.maj7.fx;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;


public class CurrencyPairInfoAdapter extends RecyclerView.Adapter<CurrencyPairInfoAdapter.ListItemHolder> {

    private CurrencyPairInfoActivity mActivity;

    public CurrencyPairInfoAdapter(CurrencyPairInfoActivity activity) {

        mActivity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public CurrencyPairInfoAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        switch (viewType) {
            case 0:
                
        }

        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        // List item
        FXListItem item = FXDatabase.shared.getItem(position);

        // Pair
        String pair = item.getCurrency1() + "/" + item.getCurrency2();
        holder.mPair.setText(pair);

        // Price
        String price = formatPrice(String.valueOf(item.getPrice()));
        holder.mPrice.setText(price);

        // Arrow
        if (item.getIsUp() == true) {
            holder.mArrow.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.arrow_up));
        } else {
            holder.mArrow.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.arrow_down));
        }
    }

    @Override
    public int getItemCount() {
        return 3;
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
            //mActivity.showDetailView();
        }
    }

    private String formatPrice(String result) {
        String price;
        if (result.length() == 6) {
            price = result + "0";
        } else {
            price = result.substring(0, 7);
        }
        return price;
    }
}
