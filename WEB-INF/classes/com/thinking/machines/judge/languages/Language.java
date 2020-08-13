package com.thinking.machines.judge.languages;

public abstract class Language
{
    public boolean timeOut=false;
    public abstract void execute();
    public abstract void compile();
}