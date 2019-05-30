package se.maj7.fx;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Charts {

    public Bitmap getLineChart(/*Double[] monthlyPrices*/) {

        Double[] prices = { 9.14325, 9.25467, 9.45023, 9.6543, 9.32043, 9.12345, 9.0, 8.9543, 8.85432, 8.7645, 8.45678, 8.85432 };
        ArrayList<Double> yPositions = new ArrayList<>();

        Double highest = getMaxValue(prices);
        Double lowest = getMinValue(prices);
        Double differenceTotal = highest - lowest;

        for (Double month : prices) {
            Double diffFromHigh = highest - month;
            Double differenceDivided = differenceTotal / diffFromHigh;
            Double yPosition = 100 / differenceDivided;
            yPositions.add(yPosition);
        }

        int blue = Color.rgb(97, 162, 236);
        int red = Color.rgb(236, 97 ,135);

        Paint paint = new Paint();

        Bitmap lineChartBitmap = Bitmap.createBitmap(1000, 600, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(lineChartBitmap);

        canvas.drawBitmap(lineChartBitmap, 0, 0, paint);
        canvas.drawColor(Color.WHITE);

        float width = 1000 / 11;
        float res = 6;

        Path path = new Path();
        path.moveTo(0, yPositions.get(0).floatValue());
        path.lineTo(width, yPositions.get(1).floatValue() * res);
        path.lineTo(width * 2, yPositions.get(2).floatValue() * res);
        path.lineTo(width * 3, yPositions.get(3).floatValue()*res);
        path.lineTo(width * 4, yPositions.get(4).floatValue()*res);
        path.lineTo(width * 5, yPositions.get(5).floatValue()*res);
        path.lineTo(width * 6, yPositions.get(6).floatValue()*res);
        path.lineTo(width * 7, yPositions.get(7).floatValue()*res);
        path.lineTo(width * 8, yPositions.get(8).floatValue()*res);
        path.lineTo(width * 9, yPositions.get(9).floatValue()*res);
        path.lineTo(width * 10, yPositions.get(10).floatValue()*res);
        path.lineTo(width * 11, yPositions.get(11).floatValue()*res);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setColor(blue);
        paint.setStrokeWidth(14);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);

        return lineChartBitmap;
    }

    public Bitmap getBarChart(Double day, Double week, Double month, Double year) {

        int blue = Color.rgb(97, 162, 236);
        int red = Color.rgb(236, 97 ,135);

        Paint paint = new Paint();

        Bitmap barChartBitmap = Bitmap.createBitmap(175, 200, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(barChartBitmap);

        canvas.drawBitmap(barChartBitmap, 0, 0, paint);
        canvas.drawColor(Color.WHITE);

        paint.setColor(red);
        canvas.drawRect(new RectF(0,100,25,200), paint);

        paint.setColor(blue);
        canvas.drawRect(new RectF(50,0,75,100), paint);

        paint.setColor(red);
        canvas.drawRect(new RectF(100,100,125,170), paint);

        paint.setColor(blue);
        canvas.drawRect(new RectF(150,50,175,100), paint);

        paint.setColor(Color.BLACK);
        canvas.drawRect(new RectF(0,100,175,101), paint);

        return barChartBitmap;
    }

    public static Double getMaxValue(Double[] array) {
        Double maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return maxValue;
    }

    public static Double getMinValue(Double[] array) {
        Double minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        return minValue;
    }
}
