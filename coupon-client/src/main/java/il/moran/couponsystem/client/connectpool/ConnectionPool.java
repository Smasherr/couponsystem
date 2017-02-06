package il.moran.couponsystem.client.connectpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import il.moran.couponsystem.client.exceptions.ManagerSQLException;
import il.moran.couponsystem.client.exceptions.ThreadException;

/*
 * A connection pool is a container for a collection of connections to the database.
 * it is a singleton that allow object to get connections to the database.
 */
public class ConnectionPool {

		// The single object of the singleton.
		private static ConnectionPool instance = null;
		// The set collection that will contain the the available connections.
		private Set<Connection> pool = null;
		// An array that will reference to the initial database connections to shut
		// them up when needed (see "closeAllConnections" method)
		private Connection[] connectionArr;
		// Constant of the number of connections
		private static final int NUM_OF_CONNECTIONS = 10;
		// Connection details
		private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/couponProjDB";
		private static final String DATABASE_USERNAME = "root";
		private static final String DATABASE_PASSWORD = "password";

		/***
		 * This function give the instance of the connection pool. if its first use
		 * the object will initialized.
		 * 
		 * @return ConnectionPool instance. the object is singleton.
		 * @throws ManagerSQLException
		 */
		public static ConnectionPool getInstance() throws ManagerSQLException {
			// Check if the instance was initialized. if so it will return the
			// instance.
			if (instance == null) {
				// Safe thread method to initialize the instance once.
				synchronized (ConnectionPool.class) {
					if (instance == null) {
						instance = new ConnectionPool();
					}
				}

			}

			return instance;

		}

		// The construction is private so it will be initiate only with in the code.
		// The construction initialize the connections in the set.
		private ConnectionPool() throws ManagerSQLException {
			pool = new HashSet<Connection>();
			connectionArr = new Connection[NUM_OF_CONNECTIONS];

			for (int i = 0; i < NUM_OF_CONNECTIONS; i++) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn = DriverManager.getConnection(CONNECTION_STRING, DATABASE_USERNAME, DATABASE_PASSWORD);
					pool.add(conn);
					connectionArr[i] = conn;
				} catch (SQLException e) {
					throw new ManagerSQLException(e.getMessage());
				} catch (ClassNotFoundException e) {
					throw new ManagerSQLException(e.getMessage());
				}
			}
		}

		/***
		 * The connection is a connection available in the ConnectionPool. if there
		 * isn't a connection available, the Thread will wait until another one will
		 * be released
		 * 
		 * @return Connection
		 * @throws ThreadException
		 *             - for Threads issues
		 * @throws ManagerSQLException
		 *             - for SQL issues
		 */
		public synchronized Connection getConnection() throws ThreadException, ManagerSQLException {
			// Check if the set is empty
			while (pool.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new ThreadException("Thread Exception: " + e.getMessage());
				}
			}
//			System.out.println(pool.size());
			Iterator<Connection> iterator = pool.iterator();
			if (iterator.hasNext()) {
				Connection conn = iterator.next();
				pool.remove(conn);
				return conn;
			} else {
				throw new ThreadException("Couldn't fetch next connection");
			}

		}

		/**
		 * This function returns a connection to the pool.
		 * This method will notify waiting threads that waits for a
		 * database connection from the pool
		 * @param conn
		 * @throws ThreadException
		 */
		public synchronized void returnConnection(Connection conn) throws ThreadException {
			// Check if the Connection is null or if there a problem adding the
			// connection in the pool.
			if (conn == null || !pool.add(conn)) {
				throw new ThreadException("Error ");
			}
//			System.out.println(pool.size());
			notify();

		}

		/**
		 * This methods will close any database connection with in the system.
		 * 
		 * @throws ManagerSQLException
		 */
		public void closeAllConnections() throws ManagerSQLException {
			for (int i = 0; i < NUM_OF_CONNECTIONS; i++) {
				try {
					connectionArr[i].close();
				} catch (SQLException e) {
					throw new ManagerSQLException(e.getMessage());
				}
			}
		}

	}
