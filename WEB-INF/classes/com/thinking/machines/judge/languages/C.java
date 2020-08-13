package com.thinking.machines.judge.languages;
import com.thinking.machines.judge.languages.*;
import com.thinking.machines.judge.*;
import java.io.*;

public class C extends Language
{
    String path;
    String fileName;
    String extension;
    int time;
    public C(String path,String fileName,String extension,int time)
    {
        System.out.println("C kaa constructor");
        this.path=path;
        this.fileName=fileName;
        this.extension=extension;
        this.time=time;
    }
    public void compile()
    {
      try 
        {
            File file=new File(this.path+"compileErr.txt");
            if (file.createNewFile())   
            {
                System.out.println("compileErr.txt File is created!");
            }   
            else 
            {
                System.out.println("compileErr.txt File already exists.");
            }
            String compileFileCommand="gcc "+this.path+this.fileName+"."+this.extension+" -o "+ path+this.fileName+".exe";
            System.out.println(compileFileCommand);
            Process processCompile = Runtime.getRuntime().exec(compileFileCommand);
            BufferedReader brCompileError = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
            String errorCompile = brCompileError.readLine();
            String errorString="";
            while(errorCompile != null)
            {
                errorString += errorCompile +"\n";
                errorCompile=brCompileError.readLine();
            }
            System.out.println(errorString);
            FileWriter writer=new FileWriter(file);
            writer.write(errorString);
            writer.close();
        }
        catch (Exception e) 
        {
            System.out.println("C me compile ke exception me aaya");
            System.out.println(e.getMessage());
        }

    }
    public void execute()
    {
        try 
        {
            File file1=new File(this.path+"runTimeErr.txt");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.path+"run.bat")));
            out.write("cd \\ && cd "+this.path+" && eg1.exe <inp.txt> out.txt");
            out.close();
            Runtime r = Runtime.getRuntime();
            Process processRun = r.exec(this.path+"run.bat");
            TimedShell ts=new TimedShell(this,processRun,this.time);
            ts.start();
            processRun.waitFor();
            BufferedReader brRun = new BufferedReader(new InputStreamReader(processRun.getErrorStream()));
            String errorRun = brRun.readLine();
            String errorString="";
            while(errorRun != null)
            {
                errorString+=errorRun;
                errorRun = brRun.readLine();
            }
            System.out.println(errorString);
            FileWriter writer=new FileWriter(file1);
            writer.write(errorString);
            writer.close();            
        }
        catch (Exception e) 
        {
            System.out.println("execute ke exception me aaya");
            System.out.println(e.getMessage());
        }
    }

}