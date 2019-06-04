package se.maj7.fx;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

public class Charts {

    private static int blue = Color.rgb(97, 162, 236);
    private static int red = Color.rgb(236, 97 ,135);

    public static Bitmap getLineChart(double[] prices) {

        // test-data
        //double[] prices = { 9.14325, 9.25467, 9.45023, 9.6543, 9.32043, 9.82345, 9.0, 8.9543, 8.85432, 8.7645, 8.45678, 8.85432 };

        ArrayList<Double> yPositions = new ArrayList<>();

        // Calculate Y-positions for path

        double highest = getMaxValue(prices);
        double lowest = getMinValue(prices);
        double differenceTotal = highest - lowest;

        for (double month : prices) {
            double diffFromHigh = highest - month;
            double differenceDivided = differenceTotal / diffFromHigh;
            double yPosition = 100 / differenceDivided;
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

        // Draw line

        float posX = width / 11.0f;
        float heightMultiplier = height / 100.0f;

        Path path = new Path();
        path.moveTo(4, yPositions.get(11).floatValue() * heightMultiplier + 4);

        for (int i = 1; i < 12; i++) {
            path.lineTo(posX * i, yPositions.get(11 - i).floatValue() * heightMultiplier);
        }

        // Line settings

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setColor(Color.parseColor("#0f3375"));
        paint.setStrokeWidth(12);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);

        // Circles

        for (int i = 1; i < 12; i++) {
            paint.setColor(Color.parseColor("#0f3375"));
            canvas.drawCircle(posX * i, yPositions.get(11 - i).floatValue() * heightMultiplier, 8, paint);
            //paint.setColor(Color.WHITE);
            //canvas.drawCircle(posX * i, yPositions.get(11 - i).floatValue() * heightMultiplier, 5, paint);
        }

        return lineChartBitmap;
    }

    public static Bitmap getBarChart(double[] prices) {

        double current = prices[0];
        double previousDay = prices[1];
        double week = prices[2];
        double month = prices[3];
        double year = prices[4];

        /*// test-data
        double current = 9.35552;
        double previousDay = 9.32334;
        double week = 9.75674;
        double month = 9.12355;
        double year = 9.88953;*/

        // Calculate percent up/down

        double percentDay = (current > previousDay) ? ((current / previousDay) - 1) * 100 : (1 - (current / previousDay)) * -100;
        double percentWeek = (current > week) ? ((current / week) - 1) * 100 : (1 - (current / week)) * -100;
        double percentMonth = (current > month) ? ((current / month) - 1) * 100 : (1 - (current / month)) * -100;
        double percentYear = (current > year) ? ((current / year) - 1) * 100 : (1 - (current / year)) * -100;

        double[] percentages = { percentDay, percentWeek, percentMonth, percentYear };
        //double[] percentages = { -3.1, 0.6, 1.8, 2.0 };

        // Calculate baseline and high/low-values

        double highest = getMaxValue(percentages);
        double lowest = getMinValue(percentages);
        double differenceTotal = highest - lowest;
        double percentPerPixel = (differenceTotal / 200);

        double middleY = 0.0;
        if (highest > 0 && lowest < 0) {
            middleY = highest / percentPerPixel;
        } else if (lowest > 0) {
            middleY = 200.0;
        } else if (highest < 0) {
            middleY = 0.0;
        }

        // Calculate bars top and bottom Y-positions

        double[] topBottomValues = new double[8]; // top and bottom for each bar (4 bars, 8 values)

        for (int i = 0; i < 4; i++) {
            if (middleY > 0 && middleY < 200) {
                if (percentages[i] == highest && middleY > 0 && middleY < 200) {
                    topBottomValues[i * 2] = 0.0;
                    topBottomValues[i * 2 + 1] = middleY;
                }
                if (percentages[i] == lowest && middleY > 0 && middleY < 200) {
                    topBottomValues[i * 2] = middleY;
                    topBottomValues[i * 2 + 1] = 200.0;
                }
                if (percentages[i] != highest && percentages[i] != lowest && percentages[i] > 0 && middleY > 0 && middleY < 200) {
                    topBottomValues[i * 2] = middleY - ((middleY / highest) * percentages[i]);
                    topBottomValues[i * 2 + 1] = middleY;
                }
                if (percentages[i] != highest && percentages[i] != lowest && percentages[i] < 0 && middleY > 0 && middleY < 200) {
                    topBottomValues[i * 2] = middleY;
                    topBottomValues[i * 2 + 1] = middleY + (((200 - middleY) / Math.abs(lowest)) * Math.abs(percentages[i]));
                }
            }
            if (middleY == 200) {
                if (middleY == 200 && percentages[i] != highest && percentages[i] != lowest) {
                    topBottomValues[i * 2] = 200 / ((highest - lowest) / (percentages[i] - lowest));
                    topBottomValues[i * 2 + 1] = 200.0;
                }
                if (middleY == 200 && percentages[i] == highest) {
                    topBottomValues[i * 2] = 0.0;
                    topBottomValues[i * 2 + 1] = 200.0;
                }
                if (middleY == 200 && percentages[i] == lowest) {
                    topBottomValues[i * 2] = 200 - (200 / (highest / percentages[i]));
                    topBottomValues[i * 2 + 1] = 200.0;
                }
            }
            if (middleY == 0) {
                if (middleY == 0 && percentages[i] != highest && percentages[i] != lowest) {
                    topBottomValues[i * 2] = 0.0;
                    topBottomValues[i * 2 + 1] = (200 / lowest) * (percentages[i]);
                }
                if (middleY == 0 && percentages[i] == highest) {
                    topBottomValues[i * 2] = 0.0;
                    topBottomValues[i * 2 + 1] = (200 / lowest) * (percentages[i]);
                }
                if (middleY == 0 && percentages[i] == lowest) {
                    topBottomValues[i * 2] = 0.0;
                    topBottomValues[i * 2 + 1] = 200.0;
                }
            }
            if (percentages[i] == 0) {
                topBottomValues[i * 2] = middleY;
                topBottomValues[i * 2 + 1] = middleY;
            }
        }

        // Convert values to float from double for use in drawRect

        float[] topBottomY = convertDoublesToFloats(topBottomValues);

        // DRAW

        Paint paint = new Paint();

        Bitmap barChartBitmap = Bitmap.createBitmap(175, 200, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(barChartBitmap);

        canvas.drawBitmap(barChartBitmap, 0, 0, paint);
        canvas.drawColor(Color.WHITE);

        // Draw bars

        // YEAR
        if (percentages[3] > 0) { paint.setColor(blue); } else { paint.setColor(red); }
        canvas.drawRect(new RectF(0, topBottomY[6],25, topBottomY[7]), paint);

        // MONTH
        if (percentages[2] > 0) { paint.setColor(blue); } else { paint.setColor(red); }
        canvas.drawRect(new RectF(50, topBottomY[4],75, topBottomY[5]), paint);

        // WEEK
        if (percentages[1] > 0) { paint.setColor(blue); } else { paint.setColor(red); }
        canvas.drawRect(new RectF(100, topBottomY[2],125, topBottomY[3]), paint);

        // DAY
        if (percentages[0] > 0) { paint.setColor(blue); } else { paint.setColor(red); }
        canvas.drawRect(new RectF(150, topBottomY[0],175, topBottomY[1]), paint);

        // BASE LINE
        paint.setColor(Color.BLACK);
        canvas.drawRect(new RectF(0, (float) middleY - 1,175,(float) middleY + 1), paint);

        return barChartBitmap;
    }

    public static double getMaxValue(double[] array) {
        double maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return maxValue;
    }

    public static double getMinValue(double[] array) {
        double minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        return minValue;
    }

    public static float[] convertDoublesToFloats(double[] input)
    {
        if (input == null)
        {
            return null;
        }
        float[] output = new float[input.length];
        for (int i = 0; i < input.length; i++)
        {
            output[i] = (float) input[i];
        }
        return output;
    }
}
