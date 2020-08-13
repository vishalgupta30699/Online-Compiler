package com.thinking.machines.judge;
import com.thinking.machines.judge.languages.*;
public class TimedShell extends Thread
{
    Language lan;
    Process p;
    long time;
    public TimedShell(Language lan,Process p,long time)
    {
        this.lan=lan;
        this.p=p;
        this.time=time;
    }
    public void run()
    {
        try {
            sleep(this.time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            p.exitValue();
            lan.timeOut=false;
        }
        catch(IllegalThreadStateException e)
        {
            lan.timeOut=true;
            p.destroy();
        }
    }
}