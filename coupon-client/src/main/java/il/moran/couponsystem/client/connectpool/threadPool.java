package il.moran.couponsystem.client.connectpool;

import java.sql.Connection;

import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;

public class threadPool extends Thread {

	
	/*
	 *  threadPool check the pool connection by taking a connection and wait.
	 */
	@Override
	public void run() {
		super.run();
		try {
			System.out.println("I am " + this.getName());
			ConnectionPool pool = ConnectionPool.getInstance();
			Connection conn = pool.getConnection();
			System.out.println(this.getName() + ": Got new Connection! Going to sleep status..");
			sleep(10000);
			System.out.println(this.getName() + ": I turned on!");
			pool.returnConnection(conn);
			System.out.println(this.getName() + ": Good bye and have a nice day:)");
			
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
