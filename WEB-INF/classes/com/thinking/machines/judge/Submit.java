package com.thinking.machines.judge;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;
import javax.servlet.annotation.WebServlet;
import com.thinking.machines.judge.languages.*;

@WebServlet("/submit")
public class Submit extends HttpServlet
{
public void doPost(HttpServletRequest rq,HttpServletResponse rs)
{
try
{
    PrintWriter out=rs.getWriter();
    rs.setContentType("text/html");
    String content=null,langCode=null,test=null;
    content=rq.getParameter("content");
    langCode=rq.getParameter("language");
    test=rq.getParameter("testCases");
    String extension="";
    if(langCode.equals("1")) extension="c";
    else if(langCode.equals("2")) extension="cpp";
    else if(langCode.equals("3")) extension="java";
    else if(langCode.equals("4")) extension="py";
    System.out.println(content);
    System.out.println(langCode);
    System.out.println(test); 
    String path="c://tomcat9//webapps//onlineCompiler//WEB-INF//Judge//";
    String fileName="eg1";
    File file=new File(path+fileName+"."+extension);
    if (file.createNewFile())   
    {
        System.out.println("File is created!");
    }   
    else 
    {
        System.out.println("File already exists.");
    }
    FileWriter writer=new FileWriter(file);
    writer.write(content);
    writer.close();
    String inputFile="inp.txt";
    file=new File(path+inputFile);
    if (file.createNewFile())   
    {
        System.out.println("File is created!");
    }   
    else 
    {
        System.out.println("File already exists.");
    }
    writer=new FileWriter(file);
    writer.write(test);
    writer.close();
    Language l=null;
    int time=5000;
    if(extension.equals("c"))
    {
        System.out.println("C ke liye request gyi");
        l=new C(path,fileName,extension,time);
    }
    else if(extension.equals("cpp"))
    {
        System.out.println("cpp ke liye request gyi");
        l=new Cpp(path,fileName,extension,time);
    }
    else if(extension.equals("java"))
    {
        System.out.println("java ke liye request gyi");
        l=new Java(path,fileName,extension,time);
    }
    else
    {
        System.out.println("python ke liye request gyi");
        l=new Python(path,fileName,extension,time);
    }
    System.out.println("compile ke liye request gyi");
    l.compile();
    System.out.println("compile error ke liye request gyi");
    String errors=compileErrors();
    if(!(errors.equals("")))
    {
        System.out.println("compile error wale if me aaya");
        out.write(errors);
    }
    else
    {
        System.out.println("execute ke liye request gyi");
        l.execute();
        System.out.println("runTimeError ke liye request gyi");
        errors=runTimeErrors();
        if(!(errors.equals("")))
        {
            out.println(errors);
        }
        else if(l.timeOut)
        {
            out.write("Time Limit Exceed (Your code get more than 5 seconds..)");
        }
        else
        {
            out.write(outputMsg());
        }
    }
}
catch(Exception e)
{
    System.out.println("Judge se exception aai");
    System.out.println(e);
}
}
public String compileErrors()
{
    String content="";
    try
    {
        BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream("c://tomcat9//webapps//onlineCompiler//WEB-INF//Judge//compileErr.txt")));
        String line=fin.readLine();
        while(line!=null)
        {
            content+=line+"<br/>";
            line=fin.readLine();
        }
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
    return content.trim();
}
public String runTimeErrors()
{
    String content="";
    try
    {
        BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream("c://tomcat9//webapps//onlineCompiler//WEB-INF//Judge//runTimeErr.txt")));
        String line=fin.readLine();
        while(line!=null)
        {
            content+=line+"<br/>";
            line=fin.readLine();
        }
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
    return content.trim();
}

public String outputMsg()
{
    String content="";
    try
    {
        BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream("c://tomcat9//webapps//onlineCompiler//WEB-INF//Judge//out.txt")));
        String line=fin.readLine();
        while(line!=null)
        {
            content+=line+"<br/>";
            line=fin.readLine();
        }
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
    return content.trim();
}
}