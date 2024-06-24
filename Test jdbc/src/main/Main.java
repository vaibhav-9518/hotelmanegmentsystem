package main;
import java.sql.*;
import java.util.Scanner;

public class Main {
public static void main(String [] args) throws ClassNotFoundException
{
	String url="jdbc:mysql://localhost:3306/mydatabase";
	String username="root";
	String password="root";
	String query="UPDATE employees SET jobtitle='full stack developer',salary=70000 WHERE id=2;";
	
	try {
Class.forName("com.mysql.jdbc.Driver");
	System.out.println("Driver Succesfully loaded");
	}
catch(ClassNotFoundException e)	
	{
	System.out.println(e.getMessage());
	}
try {
	Connection con = DriverManager.getConnection(url, username, password);
System.out.println("Connection Establish Succesfully");

Statement stmt =con.createStatement();
int rows=stmt.executeUpdate(query);

if(rows>0) {
	System.out.println("UPDATE successfull "+rows+" row affected");
}
else {
System.out.println("UPDATE failed");
}

stmt.close();
con.close();
System.out.println("Connection closed Successfully");
}
catch(SQLException e)
{
System.out.println(e.getMessage());	
}

	
Scanner sc=new Scanner(System.in);
}
}
