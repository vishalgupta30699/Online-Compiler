package com.thinking.machines.judge.languages;
import com.thinking.machines.judge.languages.*;
import com.thinking.machines.judge.*;
import java.io.*;

public class Cpp extends Language
{
    String path;
    String fileName;
    String extension;
    int timeOut;
    int time;
    public Cpp(String path,String fileName,String extension,int time)
    {
        this.path=path;
        this.fileName=fileName;
        this.extension=extension;
        this.time=time;
        this.timeOut=0;
    }
    public void compile()
    {
      try 
        {
            File file=new File(this.path+"compileErr.txt");
            if (file.createNewFile())   
            {
                System.out.println("File is created!");
            }   
            else 
            {
                System.out.println("File already exists.");
            }
            String compileFileCommand="g++ "+this.path+this.fileName+"."+this.extension+" -std=c++11 -o "+ path+this.fileName+".exe";
            Process processCompile = Runtime.getRuntime().exec(compileFileCommand);
            BufferedReader brCompileError = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
            String errorCompile = brCompileError.readLine();
            String errorString="";
            while(errorCompile != null)
            {
                errorString += errorCompile +"\n";
                errorCompile=brCompileError.readLine();
            }
            FileWriter writer=new FileWriter(file);
            writer.write(errorString);
            writer.close();
        }
        catch (Exception e) 
        {
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
            System.out.println(e.getMessage());
        }
    }

}