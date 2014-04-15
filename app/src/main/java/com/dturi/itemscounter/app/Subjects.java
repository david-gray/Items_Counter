package com.dturi.itemscounter.app;

public class Subjects {
	String Name;
	int Counter;
	Subjects(String name)
	{
		this.Name = name;
		this.Counter = 0;
	}
	public void AddOne()
	{
		this.Counter +=1;
	}
	public void RemoveOne()
	{
		this.Counter -=1;
	}
    public String GetName() {
        return this.Name;
    }
    public int GetCounter()
    {
        return this.Counter;
    }
}