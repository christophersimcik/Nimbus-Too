package com.example.chris.nimbus;

import android.graphics.Color;

class TimeNode {
    public TimeNode nextN = null, prevN = null;
    public int start, end;
    int[] dataN = new int[3];
    int time = 0;
    public TimeNode(int[] d){
        this.dataN = d;
    }
    public TimeNode next(){
        return this.nextN;
    }
    public TimeNode prev(){
        return this.prevN;
    }
    public int[] data(){
        return this.dataN;
    }
    public int top(){
        return dataN[0];
    }
    public int mid(){
        return dataN[1];
    }
    public int bottom(){
        return dataN[2];
    }
    public void setTime(int f){
        this.time = f;
    }
    public int redB(){
        return Color.red(dataN[2]);
    }
    public int grnB(){
        return Color.green(dataN[2]);
    }
    public int bluB(){
        return Color.blue(dataN[2]);
    }
    public int redM(){
        return Color.red(dataN[1]);
    }
    public int grnM(){
        return Color.green(dataN[1]);
    }
    public int bluM(){
        return Color.blue(dataN[1]);
    }
    public int redT(){
        return Color.red(dataN[0]);
    }
    public int grnT(){
        return Color.green(dataN[0]);
    }
    public int bluT(){
        return Color.blue(dataN[0]);
    }
    public int dur(){
      return end-start;
    }

}
