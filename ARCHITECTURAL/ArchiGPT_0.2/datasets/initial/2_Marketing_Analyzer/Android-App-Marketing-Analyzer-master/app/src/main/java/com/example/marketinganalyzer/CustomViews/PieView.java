package com.example.marketinganalyzer.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieView extends View {
    private PieChart pieChart;
    private float startAngle;

    public PieView(Context context,HashMap<String,Float> chartElements,
                   Integer pieDiameter, Float textSize , float startAngle ) {
        super(context);
        pieChart = new PieChart(context, chartElements, pieDiameter, textSize);
        this.setLayoutParams(new LinearLayout.LayoutParams(pieDiameter,pieDiameter));
        this.startAngle = startAngle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pieChart.drawPieChart(canvas, startAngle);
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        invalidate();
    }

    private class PieChart {
        private RectF square;
        private int pieDiameter;
        private HashMap<String,Float> chartElements;
        private float textSize;
        private float startAngle;

        public PieChart(Context context, HashMap<String,Float> chartElements, Integer pieDiameter, Float textSize) {
            this.chartElements = chartElements;
            this.pieDiameter = pieDiameter;
            this.textSize = textSize;

            //Create Drawing Square
            square = new RectF(0,0,pieDiameter,pieDiameter);

        }

        private void drawPieChart(Canvas canvas, float startAngle){
            this.startAngle = startAngle;
            List<Integer> colorPalette = getColorPalette(chartElements.size());
            final int  whiteColorCode = 0xFFFFFFFF;
            int currentElementIndex = 0;
            Paint currentPaint;
            float[] textPositions = {(float) 3*pieDiameter/8, (float) pieDiameter/8};
            int textPositionsIndex = 0;

            //canvas.setBitmap(bitmap);

            for(Map.Entry<String,Float> element: chartElements.entrySet()){
                Float percentageInAngles = element.getValue() * (float)(360.0/100.0);

                //Draw Pie Section
                currentPaint = new Paint();
                currentPaint.setColor(colorPalette.get(currentElementIndex));
                currentPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawArc(square,startAngle,percentageInAngles,true,currentPaint);

                currentElementIndex ++;
                startAngle += percentageInAngles;
            }
            for(Map.Entry<String,Float> element: chartElements.entrySet()){
                String elementName = element.getKey();

                Float percentageInAngles = element.getValue() * (float)(360.0/100.0);

                //Draw Text
                currentPaint = new Paint();
                currentPaint.setColor(whiteColorCode);
                currentPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                currentPaint.setTextSize(textSize);
                currentPaint.setTextAlign(Paint.Align.CENTER);
                currentPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                List<Float> coOrdRes = fromAngleToCoOrd((startAngle * 2 + percentageInAngles)/2, textPositions[textPositionsIndex], square.centerX(), square.centerY());
                float xCoOrd = coOrdRes.get(0);
                float yCoOrd = coOrdRes.get(1);
                canvas.drawText(elementName, xCoOrd, yCoOrd,currentPaint);

                currentElementIndex ++;
                startAngle += percentageInAngles;
                textPositionsIndex = (textPositionsIndex + 1) % 2;
            }
        }

        private List<Integer> getColorPalette(Integer numberOfColors){
            // Color palette source https://diariesofanessexgirl.com/flat-orange-blue-green-pie-chart-color-palette/
            final List<Integer> colorPaletteDefault = new ArrayList<Integer>(Arrays.asList(
                    Color.rgb (1,141,255),
                    Color.rgb (255,48,87),
                    Color.rgb (211,210,228),
                    Color.rgb (30,129,176)));
            List<Integer> retVal = new ArrayList<Integer>();
            int colorpaletteIndex = 0;
            boolean directionIncreasing = true;
            int stepsExecuted = 0;
            while (stepsExecuted < numberOfColors){
                retVal.add(colorPaletteDefault.get(colorpaletteIndex));
                if(directionIncreasing){
                    colorpaletteIndex ++;
                    if(colorpaletteIndex == colorPaletteDefault.size()-1){
                        directionIncreasing = false;
                    }
                }
                else{
                    colorpaletteIndex --;
                    if(colorpaletteIndex == 0){
                        directionIncreasing = true;
                    }
                }
                stepsExecuted ++;
            }
            return retVal;
        }

        private List<Float> fromAngleToCoOrd(Float angle, Float textPosition, float originX, float originY){
            List<Float> result = new ArrayList<Float>();

            double cosRes = Math.cos(- angle * Math.PI/180);
            double sinRes = Math.sin(- angle * Math.PI/180);

            Float xCoOrd = (float) (originX + textPosition * Math.cos(- angle * Math.PI/180));
            Float yCoOrd = (float) (originY - textPosition * Math.sin(- angle * Math.PI/180));

            result.add(xCoOrd);
            result.add(yCoOrd);
            return result;
        }
    }
}
