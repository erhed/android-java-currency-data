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

    private static int blue = Color.rgb(97, 162, 236);
    private static int red = Color.rgb(236, 97 ,135);

    public static Bitmap getLineChart(/*Double[] monthlyPrices*/) {

        Double[] prices = { 9.14325, 9.25467, 9.45023, 9.6543, 9.32043, 9.82345, 9.0, 8.9543, 8.85432, 8.7645, 8.45678, 8.85432 };

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

        // DRAW

        int width = 1000;
        int height = 600;

        Paint paint = new Paint();
        Bitmap lineChartBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(lineChartBitmap);

        canvas.drawBitmap(lineChartBitmap, 0, 0, paint);
        canvas.drawColor(Color.WHITE);

        float posX = width / 11.0f;
        float heightMultiplier = height / 100.0f;

        Path path = new Path();
        path.moveTo(0, yPositions.get(0).floatValue());

        for (int i = 1; i < 12; i++) {
            path.lineTo(posX * i, yPositions.get(i).floatValue() * heightMultiplier);
        }

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setColor(blue);
        paint.setStrokeWidth(14);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);

        return lineChartBitmap;
    }

    public static Bitmap getBarChart(/*Double current, Double previousDay, Double week, Double month, Double year*/) {

        Double current = 9.35552;
        Double previousDay = 9.32334;
        Double week = 9.45674;
        Double month = 9.12355;
        Double year = 8.78953;

        Double percentDay = (current > previousDay) ? ((current / previousDay) - 1) * 100 : (1 - (current / previousDay)) * -100;
        Double percentWeek = (current > week) ? ((current / previousDay) - 1) * 100 : (1 - (current / week)) * -100;
        Double percentMonth = (current > month) ? ((current / previousDay) - 1) * 100 : (1 - (current / month)) * -100;
        Double percentYear = (current > year) ? ((current / previousDay) - 1) * 100 : (1 - (current / year)) * -100;

        //Double[] percentages = { percentDay, percentWeek, percentMonth, percentYear };
        Double[] percentages = { 0.1, -0.6, 1.8, 2.0 };

        Double highest = getMaxValue(percentages);
        Double lowest = getMinValue(percentages);
        Double differenceTotal = highest - lowest;
        Double percentPerPixel = (differenceTotal / 200);

        Double middleY = 0.0;
        if (highest > 0 && lowest < 0) {
            middleY = highest / percentPerPixel;
        } else if (lowest > 0) {
            middleY = 200.0;
        } else if (highest < 0) {
            middleY = 0.0;
        }

        // DAY
        Double[] dayValues = { 0.0, 0.0 }; // top, bottom
        if (percentages[0] == highest && middleY > 0 && middleY < 200) {
            dayValues[0] = 0.0;
            dayValues[1] = middleY;
        }
        if (percentages[0] == lowest && middleY > 0 && middleY < 200) {
            dayValues[0] = middleY;
            dayValues[1] = 200.0;
        }
        if (percentages[0] != highest && percentages[0] != lowest && percentages[0] > 0 && middleY > 0 && middleY < 200) {
            dayValues[0] = middleY - ((middleY / highest) * percentages[0]);
            dayValues[1] = middleY;
        }
        if (percentages[0] != highest && percentages[0] != lowest && percentages[0] < 0 && middleY > 0 && middleY < 200) {
            dayValues[0] = middleY;
            dayValues[1] = middleY + (((200 - middleY) / Math.abs(lowest)) * Math.abs(percentages[0]));
        }
        if (middleY == 200 && percentages[0] != highest && percentages[0] != lowest) {
            dayValues[0] = 200 / ((highest - lowest) / (percentages[0] - lowest));
            dayValues[1] = 200.0;
        }
        if (middleY == 200 && percentages[0] == highest) {
            dayValues[0] = 0.0;
            dayValues[1] = 200.0;
        }
        if (middleY == 200 && percentages[0] == lowest) {
            dayValues[0] = 200 - (200 / (highest / percentages[0]));
            dayValues[1] = 200.0;
        }
        if (middleY == 0 && percentages[0] != highest && percentages[0] != lowest) {
            dayValues[0] = 0.0;
            dayValues[1] = (200 / differenceTotal) * (percentages[0] - lowest);
        }
        if (middleY == 0 && percentages[0] == highest) {
            dayValues[0] = 0.0;
            dayValues[1] = (200 / lowest) * (percentages[0]);
        }
        if (middleY == 0 && percentages[0] == lowest) {
            dayValues[0] = 0.0;
            dayValues[1] = 200.0;
        }
        if (percentages[0] == 0) {
            dayValues[0] = middleY;
            dayValues[1] = middleY;
        }








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

        // middle line
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
