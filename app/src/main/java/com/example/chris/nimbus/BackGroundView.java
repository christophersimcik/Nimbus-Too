package com.example.chris.nimbus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import org.w3c.dom.Node;

import java.util.TimeZone;


public class BackGroundView extends View {
    Handler hndlr = new Handler();
    TimeNode timeOfDay,morning,noon,dusk,midnight;
    int timer = 0;
    int tstColor;
    final int sr = 429, nn = 714, ss = 999, mn = 1440;
    public Runnable r;
    int w,h,cntrX,cntrY;
    int[] colors = new int[]{Color.RED,Color.YELLOW,Color.WHITE};
    float[] pos = new float[]{0f,.50f,1f};
    int[] mornColors, aftrColors, nghtColors;

    public BackGroundView(Context context) {
        super(context);
        getColorArrays();
        getColorArrays();
        morning = new TimeNode(mornColors);
        noon = new TimeNode(aftrColors);
        dusk = new TimeNode(mornColors);
        midnight = new TimeNode(nghtColors);
        timeOfDay = morning;
        morning.nextN = noon;
        morning.prevN = midnight;
        noon.nextN = dusk;
        noon.prevN = morning;
        dusk.nextN = midnight;
        dusk.prevN = noon;
        midnight.nextN = morning;
        midnight.prevN = dusk;
        morning.setTime(sr);
        morning.start = sr;
        morning.end = nn;
        noon.setTime(nn);
        noon.start = nn;
        noon.end = ss;
        dusk.setTime(ss);
        dusk.start = ss;
        dusk.end = mn;
        midnight.setTime(mn);
        midnight.start = 0;
        midnight.end = sr;

    }

    public BackGroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getColorArrays();
        morning = new TimeNode(mornColors);
        noon = new TimeNode(aftrColors);
        dusk = new TimeNode(mornColors);
        midnight = new TimeNode(nghtColors);
        timeOfDay = morning;
        morning.nextN = noon;
        morning.prevN = midnight;
        noon.nextN = dusk;
        noon.prevN = morning;
        dusk.nextN = midnight;
        dusk.prevN = noon;
        midnight.nextN = morning;
        midnight.prevN = dusk;
        morning.setTime(sr);
        morning.start = sr;
        morning.end = nn;
        noon.setTime(nn);
        noon.start = nn;
        noon.end = ss;
        dusk.setTime(ss);
        dusk.start = ss;
        dusk.end = mn;
        midnight.setTime(mn);
        midnight.start = 0;
        midnight.end = sr;
    }

    public BackGroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getColorArrays();
        morning = new TimeNode(mornColors);
        noon = new TimeNode(aftrColors);
        dusk = new TimeNode(mornColors);
        midnight = new TimeNode(nghtColors);
        timeOfDay = morning;
        morning.nextN = noon;
        morning.prevN = midnight;
        noon.nextN = dusk;
        noon.prevN = morning;
        dusk.nextN = midnight;
        dusk.prevN = noon;
        midnight.nextN = morning;
        midnight.prevN = dusk;
        morning.setTime(sr);
        morning.start = sr;
        morning.end = nn;
        noon.setTime(nn);
        noon.start = nn;
        noon.end = ss;
        dusk.setTime(ss);
        dusk.start = ss;
        dusk.end = mn;
        midnight.setTime(mn);
        midnight.start = 0;
        midnight.end = sr;
    }

    public BackGroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }
    @Override
    protected void onSizeChanged(int w, int h, int prevW, int prevH){
        this.w = w;
        this.h = h;
        this.cntrX = w/2;
        this.cntrY = h/2;

        r = new Runnable() {

            int pm = 1;
            TimeNode tod;
            TimeNode pre = tod;
            @Override
            public void run() {
                System.out.println("******* PM -> " + pm);
                if(timer < 1440){
                    timer++;
                }else{
                    timer = 0;
                }
                tod = getTimeOfDay(timer);
                System.out.println("******* TIME -> " + tod.time);
                if(tod != pre){
                    pre = tod;
                    pm = 1;
                }
                pm ++;
                mornColors = updateCols(pm,tod);
                hndlr.postDelayed(r,10);
                refresh();

            }
        };
        hndlr.post(r);
    }

    @Override
    protected  void onDraw(Canvas c){
        Paint p = new Paint();
        Paint n = new Paint();
        n.setColor(tstColor);
        RectF morn = new RectF(0,0,getWidth()/3,getHeight());
        RectF aft = new RectF(getWidth()/3,0,(getWidth()/3)*2,getHeight());
        RectF nght = new RectF((getWidth()/3)*2,0,getWidth(),getHeight());
        p.setShader(new LinearGradient(cntrX,0,cntrX,h,aftrColors,pos, Shader.TileMode.CLAMP));
        c.drawRect(aft,p);
        p.setShader(new LinearGradient(cntrX,0,cntrX,h,mornColors,pos, Shader.TileMode.CLAMP));
        c.drawRect(morn,p);
        p.setShader(new LinearGradient(cntrX,0,cntrX,h,nghtColors,pos, Shader.TileMode.CLAMP));
        c.drawRect(nght,p);
        c.drawCircle(getWidth()/3,getWidth()/3,200,n);
    }

    protected int[] updateCols(int pm, TimeNode tod) {
        int timeConstant = tod.dur();
        System.out.println("***********TIME C*********** = " + timeConstant);
        float difR,difG,difB;
        int t,m,b;
        int re,gr,bl;
        difR = (float)((tod.next().redB()-tod.redB()))/(float)timeConstant;
        difG = (float)((tod.next().grnB()-tod.grnB()))/(float)timeConstant;
        difB = (float)((tod.next().bluB()-tod.bluB()))/(float)timeConstant;
        re = Math.round(tod.redB()+(pm*difR));
        gr = Math.round(tod.grnB()+(pm*difG));
        bl = Math.round(tod.bluB()+(pm*difB));
        b = Color.rgb(re,gr,bl);
        System.out.println("red = " + re+ " green = " + gr + " blue = " + bl);
        difR = (float)((tod.next().redM()-tod.redM()))/(float)timeConstant;
        difG = (float)((tod.next().grnM()-tod.grnM()))/(float)timeConstant;
        difB = (float)((tod.next().bluM()-tod.bluM()))/(float)timeConstant;
        re = Math.round(tod.redM()+(pm*difR));
        gr = Math.round(tod.grnM()+(pm*difG));
        bl = Math.round(tod.bluM()+(pm*difB));
        m = Color.rgb(re,gr,bl);
        System.out.println("red = " + re+ " green = " + gr + " blue = " + bl);
        difR = (float)((tod.next().redT()-tod.redT()))/(float)timeConstant;
        difG = (float)((tod.next().grnT()-tod.grnT()))/(float)timeConstant;
        difB = (float)((tod.next().bluT()-tod.bluT()))/(float)timeConstant;
        re = Math.round(tod.redT()+(pm*difR));
        gr = Math.round(tod.grnT()+(pm*difG));
        bl = Math.round(tod.bluT()+(pm*difB));
        t = Color.rgb(re,gr,bl);
        System.out.println("red = " + re+ " green = " + gr + " blue = " + bl);


        return new int[]{t,m,b};



    }
    public void refresh(){
        this.invalidate();
    }
    public TimeNode getTimeOfDay(int t){
        TimeNode tn = morning;
        while( t > tn.end || t < tn.start){
            tn = tn.next();
        }

        return tn;
    }
    public void getColorArrays(){
        mornColors = new int[]{
                this.getResources().getColor(R.color.m_top,null),
                this.getResources().getColor(R.color.m_mid,null),
                this.getResources().getColor(R.color.m_bot,null)
        };
        aftrColors = new int[]{
                this.getResources().getColor(R.color.n_top,null),
                this.getResources().getColor(R.color.n_mid,null),
                this.getResources().getColor(R.color.n_bot,null),
        };
        nghtColors = new int[]{
                this.getResources().getColor(R.color.mn_top,null),
                this.getResources().getColor(R.color.mn_mid,null),
                this.getResources().getColor(R.color.mn_bot,null),
        };
    }




}
