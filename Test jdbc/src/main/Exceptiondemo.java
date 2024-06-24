package main;

import java.util.Scanner;

public class Exceptiondemo {

	public static void divisiondemo(int a,int b) throws ArithmeticException{
	System.out.println(a/b);

}
public static void main(String [] args)
{
	divisiondemo(10, 0);
	
}}
	