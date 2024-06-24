package main;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class HotelReservationSystem {
private static final String url ="jdbc:mysql://localhost:3306/hotel_db";

private static final String username="root";

private static final String password="root";

public static void main(String [] args) throws ClassNotFoundException,SQLException {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
//	System.out.println("driver class loaded successfully");
	}
catch(ClassNotFoundException e) {
	System.out.println(e.getMessage());
}

	try {
	    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_db", "root", "root");
//System.out.println("connetion Establish Successfully");
	
	while(true) {
	System.out.println();
	System.out.println("HOTEL MANEGEMENT SYSTEM");
	Scanner sc=new Scanner(System.in);
	System.out.println("1. reserve a room");
	System.out.println("2. view reservation");
	System.out.println("3. Get Room Number");
	System.out.println("4. Update Reservation");	
	System.out.println("5. Delete Reservation");
	System.out.println("0. exit");
	System.out.println("Choose an Option:  ");
	int choice=sc.nextInt();
	switch(choice) {
	case 1:
		reserveroom(con,sc);
		break;
	case 2:
		viewreservation(con);
		break;
	case 3:
		getRoomNumber(con,sc);
		break;
	case 4:
		updateReservation(con,sc);
		break;
	case 5:
		deleteReservation(con,sc);
		break;
	case 0:
		exit();
		sc.close();
		return;
		default:
			System.out.println("Invalid Choice try again.");
	}}
	}
catch(SQLException e)	
{
System.out.println(e.getMessage());
}
catch(InterruptedException e)
{
throw new RuntimeException(e);
}
}
private static void reserveroom(Connection con,Scanner sc) {
	
	try {
	System.out.println("enter the guest name: ");
	String guestname=sc.next();
	System.out.println("enter the room number: ");
	int roomnumber=sc.nextInt();
	System.out.println("enter the contact number: ");
	String contactnumber=sc.next();
	
	String sql="Insert into reservation(guest_name,room_number,contact_number)"+
	"VALUES (' "+ guestname +"',"+roomnumber+", '"+ contactnumber+"')";	
	
	try(java.sql.Statement stmt = con.createStatement()) 
	{
		int  affectedRows = stmt.executeUpdate(sql);
	
	if(affectedRows>0)
	{
	System.out.println("reservation succesfully");	
	}
	else {
		System.out.println("reservation  failed");
	}
	}}
	catch(SQLException e) {
		e.printStackTrace();
		}
	
	}

private static void viewreservation(Connection con) throws SQLException
{
String sql="SELECT reservation_id,guest_name,room_number,contact_number,reservation_date FROM reservation";

try(java.sql.Statement stmt=con.createStatement();
ResultSet resultSet=stmt.executeQuery(sql))
{
	System.out.println("current reservations:");
	while(resultSet.next()) {
		int reservationid=resultSet.getInt("reservation_id");
		String guestname=resultSet.getString("guest_name");
		int roomnumber=resultSet.getInt("room_number");
		String contactnumber=resultSet.getString("contact_number");
		String reservationdate=resultSet.getTimestamp("reservation_date").toString();
	
		System.out.println("reservation_id="+reservationid+" ,"+"guest_name="+guestname+", "+"room_number="+roomnumber+", "+"contact_number="+contactnumber+", "+"reservation_date="+reservationdate);
	}

}
}


private static void getRoomNumber(Connection con,Scanner sc) {
	try {
		System.out.println("Enter reservation ID: ");
		int reservationId=sc.nextInt();
		System.out.println("enter guest name");
		String guestname=sc.next();
		
		String sql="SELECT room_number FROM reservation WHERE reservation_id="+reservationId+" AND guest_name="+"'"+guestname+"'";
	 
	try(java.sql.Statement statement = con.createStatement();
			 ResultSet resultSet = statement.executeQuery(sql)){
		 
		 if(resultSet.next()) {
			 int roomNumber=resultSet.getInt("room_number");
			 System.out.println("room number for reservation id"+reservationId+"and guest"+guestname+"is:"+roomNumber);
		 }
		 else {
			 System.out.println("reservation not found in the given id and guest name");
		 }
	 }
	 } 
	catch(SQLException e) {
		e.printStackTrace();
	}}
	

private static void updateReservation(Connection con,Scanner sc) {
		try {
			System.out.println("Enter reservation ID to  update: ");
		int reservationId=sc.nextInt();
		sc.nextLine();//consume new line character
	
		if(!reservationExists(con,reservationId)) {			
			System.out.println("reseravtion id not found");
		return;
		
		}
		System.out.println("enter new guest name:");
		String newGuestname=sc.nextLine();
		System.out.println("enter new room number");
		int newRoomNumber=sc.nextInt();
		System.out.println("enter new contact number: ");
		String newcontactnumber=sc.next();
		String sql="UPDATE reservation SET guest_name="+"'"+newGuestname+"'"+","
	+"room_number="+newRoomNumber+","+
   "contact_number='"+newcontactnumber+"'"+
   "WHERE reservation_id= "+ reservationId;
		
		try(java.sql.Statement statement=con.createStatement()){
			int affectedRows=statement.executeUpdate(sql);
	if(affectedRows>0) {
		System.out.println("reservation update successfully");
	}
	else {
		System.out.println("reservation failed");
	}
	}}
		catch(SQLException e)
		{
			e.printStackTrace();
		}}  
		

private static void deleteReservation(Connection con,Scanner sc) {
			
			try {
				System.out.println("enter reservation id to delete");
			int reservationid=sc.nextInt();
			
			if(!reservationExists(con,reservationid)) {
				System.out.println("reservation not found for the given id: ");
			return;
			}
			String sql="DELETE FROM reservation WHERE reservation_id ="+reservationid;
				try(java.sql.Statement statement =con.createStatement())
				{
					int affectedrows= statement.executeUpdate(sql);
					 if(affectedrows>0)
					 {
						 System.out.println("reservation delete successfullly");
					 }
					 else
					 {
						 System.out.println("reservation delete failed");
					 }
				}}
			catch(SQLException e) {
				e.printStackTrace();
			}
			}
		private static boolean reservationExists(Connection con,int reservationId) {
			try {
				String sql="SELECT reservation_id FROM reservation WHERE reservation_id="+reservationId;
				try (java.sql.Statement statement =con.createStatement();
				 ResultSet resultSet=statement.executeQuery(sql))
				{
					return resultSet.next(); //if there is a result ,the reservation exists
				}
					}
		catch(SQLException e) {
			e.printStackTrace();
		return false;//handle database errors as needed
		}	
		}
	
		private static void exit() throws InterruptedException{
		System.out.println("Existing System");
		int i=5;
		while(i!=0) {
			System.out.print(". ");
			Thread.sleep(450);
		i--;
		}
		System.out.println();
		System.out.println("thank you for using hotel reservation system!!!");
		}
		}
	
	
	



