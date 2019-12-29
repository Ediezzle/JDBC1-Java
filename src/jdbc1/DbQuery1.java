package jdbc1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbQuery1
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		try 
		(
			Connection connection = DbConnector1.connectToDb();
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("SELECT firstName, "
					+ "email FROM contact WHERE firstName=\"Michael\"");
		) 
			{
				System.out.println("fName \temail");
				while (resultset.next())
				{
					System.out.println(resultset.getString("firstName") + "\t"
					+ resultset.getObject(2));
				}
			} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("------------------------------------------------------------");
		
		//UPDATE DATABASE
		try 
		(
			Connection connection = DbConnector1.connectToDb();
			// create a statement from which the created ResultSets
			// are "scroll sensitive" as well as "updatable"
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
					ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery("SELECT * FROM contact "
					+ "WHERE firstName=\"Michael\"");
		) 
		{
			// first fetch the data and display it before the update operation
			System.out.println("Before the update");
			System.out.println("id \tfName \tlName \temail \t\tphoneNo");
			while (resultSet.next()) 
			{
				System.out.println(resultSet.getInt("id") + "\t"
				+ resultSet.getString("firstName") + "\t"
				+ resultSet.getString("lastName") + "\t"
				+ resultSet.getString("email") + "\t"
				+ resultSet.getString("phoneNo"));
			}
			System.out.println();
			// now update the resultSet and display the modified data
			resultSet.absolute(1);
			resultSet.updateString("phoneNo", "+27620472001");
			// reflect those changes back to the database by calling updateRow() method
			resultSet.updateRow();
			System.out.println("After the update");
			System.out.println("id \tfName \tlName \temail \t\tphoneNo");
			resultSet.beforeFirst();
			while (resultSet.next()) 
			{
				System.out.println(resultSet.getInt("id") + "\t"
				+ resultSet.getString("firstName") + "\t"
				+ resultSet.getString("lastName") + "\t"
				+ resultSet.getString("email") + "\t"
				+ resultSet.getString("phoneNo"));
			}
			
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("--------------------------------------------------------------");
		
		//INSERT ROW
		try 
		(
			Connection connection = DbConnector1.connectToDb();
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery("SELECT * FROM contact")
		) 
		{
			System.out.println("Before the insert");
			System.out.println("id \tfName \tlName \temail \t\tphoneNo");
			while (resultSet.next())
			{
				System.out.println(resultSet.getInt("id") + "\t"
				+ resultSet.getString("firstName") + "\t"
				+ resultSet.getString("lastName") + "\t"
				+ resultSet.getString("email") + "\t"
				+ resultSet.getString("phoneNo"));
			}
			
			System.out.println();
			
			resultSet.moveToInsertRow();
			resultSet.updateString("firstName", "John");
			resultSet.updateString("lastName", "K.");
			resultSet.updateString("email", "john@abc.com");
			resultSet.updateString("phoneNo", "+19753186420");
			resultSet.insertRow();
			System.out.println("After the insert");
			System.out.println("id \tfName \tlName \temail \t\tphoneNo");
			resultSet.beforeFirst();
			while (resultSet.next())
			{
				System.out.println(resultSet.getInt("id") + "\t"
				+ resultSet.getString("firstName") + "\t"
				+ resultSet.getString("lastName") + "\t"
				+ resultSet.getString("email") + "\t"
				+ resultSet.getString("phoneNo"));
			}
			
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		//DELETE ROW
		try 
		(
				Connection connection = DbConnector1.connectToDb();
				Statement statement = 
						connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				ResultSet resultSet1 = statement.executeQuery ("SELECT * FROM contact "
						+ "WHERE firstName=\"John\"")
		) 
		{
				if(resultSet1.next())
				{
				// delete the first row
				resultSet1.deleteRow();
				}
				resultSet1.close();
				
				// now fetch again from the database
				try  //try with resource declarations can be used without catch or finall block
				(
					ResultSet resultSet2 = statement.executeQuery("SELECT * FROM contact")
				) 
				{
					System.out.println("After the deletion");
					System.out.println("id \tfName \tlName \temail \t\tphoneNo");
					while (resultSet2.next())
					{
						System.out.println(resultSet2.getInt("id") + "\t"
						+ resultSet2.getString("firstName") + "\t"
						+ resultSet2.getString("lastName") + "\t"
						+ resultSet2.getString("email") + "\t"
						+ resultSet2.getString("phoneNo"));
					}
				}
			} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		//CREATE TABLE
		try (
				Connection connection = DbConnector1.connectToDb();
				Statement statement = connection.createStatement())
		{
				// use CREATE TABLE SQL statement to create table familyGroup
				int result = statement.executeUpdate("CREATE TABLE familyGroup (id int not null"
						+ " auto_increment, nickName varchar(30) not null, primary key(id));");
				System.out.println(result);
				System.out.println("Table created successfully");
				
		}
		
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

}


