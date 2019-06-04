package se.maj7.fx;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


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

        CurrencyPairInfoData data = FXDatabase.shared.getInfoData();

        // Price row
        if (position == 0) {
            holder.price_currency1.setText(data.getCurrency1());
            holder.price_currency2.setText(data.getCurrency2());
            String priceFormatted = String.format("%.5f", data.getCurrentPrice());
            String priceCropped = priceFormatted.substring(0,7);
            holder.price_price.setText(priceCropped);
        }

        // Line chart row
        if (position == 1) {
            holder.chart_chart.setImageBitmap(Charts.getLineChart(data.getPricesForLineChart()));
            for (int i=0; i<12; i++) {
                double[] hm = data.getPricesForLineChart();
                Log.i("PRICES FOR CHART", String.valueOf(hm[i]));
            }
        }

        // Bar chart row
        if (position == 2) {
            holder.bars_bars.setImageBitmap(Charts.getBarChart(data.getPricesForBars()));

            String[] percent = FXDatabase.shared.getInfoData().getPercentagesForBars();

            String day = (percent[0].charAt(0) != '-') ? "+" + percent[0] + "%" : percent[0] + "%";
            String week = (percent[1].charAt(0) != '-') ? "+" + percent[1] + "%" : percent[1] + "%";
            String month = (percent[2].charAt(0) != '-') ? "+" + percent[2] + "%" : percent[2] + "%";
            String year = (percent[3].charAt(0) != '-') ? "+" + percent[3] + "%" : percent[3] + "%";

            holder.bars_day.setText(day);
            holder.bars_week.setText(week);
            holder.bars_month.setText(month);
            holder.bars_year.setText(year);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Price row
        TextView price_currency1;
        TextView price_currency2;
        TextView price_price;

        // Line chart row
        ImageView chart_chart;
        TextView chart_date1;
        TextView chart_date2;

        // Bar chart row
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
        }
    }
}
