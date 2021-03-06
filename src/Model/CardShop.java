/**
 * 
 */
package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author victorfeijo
 *
 */


public class CardShop {
	
	private Connection connection;
	private Statement statement;
	
	
	public void connectDatabase(String dbPath) {
		
		try {
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			this.connection.setAutoCommit(false);
			this.statement = this.connection.createStatement();		
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Card> getDeck(DeckEnum type) {
		
		this.connectDatabase("./database/cards.db");
		char deckType;
		List<Card> deck = new ArrayList<Card>();
		if (type.equals(DeckEnum.DC)) {
			deckType = 'd';
		} else {
			deckType = 'm';
		}
		try {
			ResultSet resultSet = this.statement.executeQuery("select * from Card where deckType = " + "'" + deckType + "'");
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				int id = resultSet.getInt("id");
				int attack = resultSet.getInt("attack");
				int defense = resultSet.getInt("defense");
				Card card = new Card(name, id, attack, defense, type);
				deck.add(card);
			}
			resultSet.close();
			this.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deck;
	}
	
	private void close() {
	    try {
	      if (statement != null) {
	        statement.close();
	      }

	      if (this.connection != null) {
	        this.connection.close();
	      }
	    } catch (Exception e) {

	    }
	  }

}
