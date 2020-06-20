package com.example.chris.nimbus;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

public class DateView extends View {
    int[] colors = {Color.TRANSPARENT, Color.BLACK,Color.TRANSPARENT};
    String date = "n/a", time = "n/a", day = "n/a";
    float secsSA = 0f, minsSA = 0f, hrsSA = 0f;
    float sSwp = 0.f, mSwp = 0.f, hSwp = 0.f;
    int secStroke, minStroke, hrStroke;
    int sec_min,min_hr;
    RectF secs,mins, hrs;
    int cntrX, cntrY;


    public DateView(Context context) {
        super(context);

    }

    public DateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    protected void onSizeChanged(int w,int h, int prevW, int prevH){
         cntrX = w/2;
         cntrY = h/2;
         minStroke = secStroke*2;
         sec_min = (minStroke-secStroke)/2;
         hrStroke = secStroke*4;
         min_hr = (hrStroke-secStroke)/2;
         secs = new RectF(25, 25, getRight()-25, getHeight()-25);
         mins = new RectF(25+secStroke+sec_min,25+secStroke+sec_min,getRight()-(25+secStroke+sec_min),getHeight()-(25+secStroke+sec_min));
         hrs = new RectF ( 25+secStroke+minStroke+min_hr,25+secStroke+minStroke+min_hr,getRight()-(25+secStroke+minStroke+min_hr),getHeight()-(25+secStroke+minStroke+min_hr));


         System.out.println("secS = " + secStroke + ", minS = " + minStroke);
        System.out.println("secs = " + secs.width() + ", mins = " + mins.width());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawText(canvas,0,0);
        drawEllipses(canvas);
        drawCaps(canvas);
    }
    public void upDateTime(String day, String time, String date){
      this.day = day;
      this.time = time;
      this.date = date;
    }
    public void updateSweepAngles(int s, int m, int h){
        if(h>12){
            h = h-12;
        }
        secsSA = (float)s * 6f;
        minsSA = (float)m * 6f;
        hrsSA = (float)h * 30f;
        sSwp =  secsSA/360f;
        mSwp =  minsSA/360f;
        hSwp =  hrsSA/360f;
    }
    public void drawText(Canvas c, int boxW, int boxH){
        int width = (c.getWidth()-((115*2)))-200;
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setTextAlign(Paint.Align.CENTER);
        Rect txtBnds = new Rect();
        p.setTextSize(256);
        int w = c.getWidth();
        while(w > width){
            p.setTextSize(p.getTextSize()-1);
            p.getTextBounds(date,0,date.length(),txtBnds);
            w = txtBnds.width();
        }

        int h = txtBnds.height();
        p.setAlpha(200);
        c.drawText(date,cntrX,cntrY,p);
        p.setAlpha(150);
        c.drawText(day,cntrX,(cntrY-h)-5,p);
        p.setAlpha(255);
        float txtS = p.getTextSize();
        p.setTextSize(txtS+(txtS*.50f));
        p.getTextBounds(date,0,date.length(),txtBnds);
        h = h+(txtBnds.height()-h);
        c.drawText(time,cntrX,(cntrY+h)+5,p);
    }
    public void drawCaps(Canvas c){
         int secR = Math.round(secs.width()/2);
         int minR = Math.round(mins.width()/2);
         int hrR = Math.round(hrs.width()/2);
         Point seX = new Point();
         Point miX = new Point();
         Point hrX = new Point();
         //perimiter_Y_coord = center_Y_coord - r * sin(theta)
         seX.x = (int)(Math.round(cntrX+secR*Math.cos(secsSA* (Math.PI / 180))));
         seX.x = (int)Math.round((cntrX + secR * Math.cos(secsSA* (Math.PI / 180))));
         seX.y = (int)Math.round((cntrY + secR * Math.sin(secsSA* (Math.PI / 180))));
         miX.x = (int)Math.round((cntrX + minR * Math.cos(minsSA* (Math.PI / 180))));
         miX.y = (int)Math.round((cntrY + minR * Math.sin(minsSA* (Math.PI / 180))));
         hrX.x = (int)Math.round((cntrX + hrR * Math.cos(hrsSA* (Math.PI / 180))));
         hrX.y = (int)Math.round((cntrX + hrR * Math.sin(hrsSA* (Math.PI / 180))));
         Paint p = new Paint();
         p.setColor(Color.BLACK);
         p.setAntiAlias(true);
         c.rotate(-90,cntrX,cntrY);
         c.drawCircle(seX.x,seX.y,secStroke/2,p);
         c.drawCircle(miX.x,miX.y,minStroke/2,p);
         c.drawCircle(hrX.x,hrX.y,hrStroke/2,p);
         c.rotate(+90,cntrX,cntrY);
    }
    public void drawEllipses(Canvas c){
        Paint paint = new Paint();
        Paint dPaint = new Paint();
        float[] s = {0,sSwp,1f};
        float[] m = {0,mSwp,1f};
        float[] h = {0,hSwp,1f};
        int x = cntrX;
        int y = cntrY;
        paint.setAntiAlias(true);
        dPaint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        dPaint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        dPaint.setStrokeCap(Paint.Cap.BUTT);
        dPaint.setColor(Color.BLACK);
        dPaint.setAlpha(30);
        Path path = new Path();
        System.out.println("secR = " +  ((getWidth()-50)/2));
        System.out.println("minR = " +  ((getWidth()-((25+secStroke+minStroke)*2))/2));
        System.out.println("hrR = " + ((getWidth()-((25+minStroke+hrStroke)*2))/2));
        path.arcTo(secs, 0, secsSA);
        paint.setShader(new SweepGradient(x,y,colors,s));
        c.rotate(-90,x,y);
        paint.setStrokeWidth(secStroke);
        dPaint.setStrokeWidth(secStroke);
        c.drawPath(path, paint);
        c.drawCircle(x,y,(getWidth()/2)-25,dPaint);
        c.rotate(+90,x,y);
        path.rewind();
        path.arcTo(mins, 0, minsSA);
        paint.setShader(new SweepGradient(x,y,colors,m));
        c.rotate(-90,x,y);
        paint.setStrokeWidth(minStroke);
        dPaint.setStrokeWidth(minStroke);
        c.drawPath(path, paint);;
        c.drawCircle(x,y,getWidth()/2-(25+secStroke+sec_min),dPaint);
        c.rotate(+90,x,y);
        path.rewind();
        path.arcTo(hrs, 0, hrsSA);
        paint.setShader(new SweepGradient(x,y,colors,h));
        c.rotate(-90,x,y);
        paint.setStrokeWidth(hrStroke);
        dPaint.setStrokeWidth(hrStroke);
        c.drawPath(path,paint);
        c.drawCircle(x,y,getWidth()/2-(25+secStroke+minStroke+min_hr),dPaint);
        c.rotate(+90,x,y);
        path.reset();

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        Display d = findActivity().getWindowManager().getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);
        int w = p.x;
        int h = p.y;
        int dSize = Math.min(w,h);
        dSize = (int)(dSize * .75);
        int mSpec = Math.min(widthMeasureSpec,heightMeasureSpec);
        dSize = resolveSize(dSize,mSpec);
        dSize = measureDimension(dSize,mSpec);
        setMeasuredDimension(dSize,dSize);
    }
    private int measureDimension(int dynamicSize, int measureSpec){
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            result =  specSize;
        }else{
            if(specMode == MeasureSpec.AT_MOST){
                result = Math.min(dynamicSize,specSize);
            }else{
                result = dynamicSize;
            }
        }
        return result;
    }
    private Activity findActivity(){
        Context c = this.getContext();
        while(c instanceof ContextWrapper){
            if( c instanceof Activity){
                return (Activity) c;
            }
            c = ((ContextWrapper)c).getBaseContext();
        }
        return null;
    }
}
