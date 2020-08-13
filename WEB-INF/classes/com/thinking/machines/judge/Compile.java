package com.thinking.machines.judge;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;
import javax.servlet.annotation.WebServlet;
import com.thinking.machines.judge.languages.*;

@WebServlet("/compile")
public class Compile extends HttpServlet
{
public void doPost(HttpServletRequest rq,HttpServletResponse rs)
{
try
{
    PrintWriter out=rs.getWriter();
    rs.setContentType("text/html");
    String content=rq.getParameter("content");
    String langCode=rq.getParameter("language");
    String extension="";
    if(langCode.equals("1")) extension="c";
    else if(langCode.equals("2")) extension="cpp";
    else if(langCode.equals("3")) extension="java";
    else if(langCode.equals("4")) extension="py";
    System.out.println(content);
    System.out.println(langCode); 
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
    Language l=null;
    int time=10000;
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
        out.write("Compiled Successfully ! Ready to Run...");
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
        String [] str;
        while(line!=null)
        {
            str=line.split(":",3);
            if(str.length>2)   content+=str[2]+"<br/>";
            else content+=str[0]+"<br/>";
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