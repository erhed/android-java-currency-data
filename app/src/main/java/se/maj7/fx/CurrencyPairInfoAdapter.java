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

        switch (viewType) {
            case 0:
                View price = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_price, parent, false);
                return new ListItemHolder(price);
            case 1:
                View chart = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_chart, parent, false);
                return new ListItemHolder(chart);
            case 2:
                View bars = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_bars, parent, false);
                return new ListItemHolder(bars);
        }

        return new ListItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_price, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {

        if (position == 1) {
            holder.chart_chart.setImageBitmap(Charts.getLineChart());
        }

        if (position == 2) {
            holder.bars_bars.setImageBitmap(Charts.getBarChart());
        }

        /*// List item
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
        }*/
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView price_currency1;
        TextView price_currency2;
        TextView price_price;

        ImageView chart_chart;
        TextView chart_date1;
        TextView chart_date2;

        ImageView bars_bars;
        TextView bars_day;
        TextView bars_week;
        TextView bars_month;
        TextView bars_year;

        public ListItemHolder(View view) {
            super(view);

            price_currency1 = (TextView) view.findViewById(R.id.currency1row1);
            price_currency2 = (TextView) view.findViewById(R.id.currency2Row1);
            price_price = (TextView) view.findViewById(R.id.priceRow1);

            chart_chart = (ImageView) view.findViewById(R.id.chartImage);
            chart_date1 = (TextView) view.findViewById(R.id.chartDateFromText);
            chart_date2 = (TextView) view.findViewById(R.id.chartDateToText);

            bars_bars = (ImageView) view.findViewById(R.id.barImage);
            bars_day = (TextView) view.findViewById(R.id.percentDayText);
            bars_week = (TextView) view.findViewById(R.id.percentWeekText);
            bars_month = (TextView) view.findViewById(R.id.percentMonthText);
            bars_year = (TextView) view.findViewById(R.id.percentYearText);

            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //mActivity.showDetailView();
        }
    }
}
