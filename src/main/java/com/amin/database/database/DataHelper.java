package com.amin.database.database;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author afsal
 */
public class DataHelper {

    private final static Logger LOGGER = LogManager.getLogger(DatabaseHandler.class.getName());


    public static boolean isBookExists(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM BOOK WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        }
        catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isMemberExists(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM MEMBER WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        }
        catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static ResultSet getBookInfoWithIssueData(String id) {
        try {
            String query = "SELECT BOOK.title, BOOK.author, BOOK.isAvail, ISSUE.issueTime FROM BOOK LEFT JOIN ISSUE on BOOK.id = ISSUE.bookID where BOOK.id = ?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(query);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs;
        }
        catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return null;
    }
}
