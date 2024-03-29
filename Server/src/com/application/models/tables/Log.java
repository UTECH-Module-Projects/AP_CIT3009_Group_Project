/*
 * Advance Programming Group Project
 * Date of Submission: 11/11/2022
 * Lab Supervisor: Christopher Panther
 *
 * Group Members:-
 * ~ Gabrielle Johnson      2005322
 * ~ Jazmin Hayles          2006754
 * ~ Rushawn White          2002469
 * ~ Barrignton Patterson  2008034
 *
 */

//Package
package com.application.models.tables;

//Imported Libraries

import com.application.generic.DBTable;
import com.application.models.misc.EntityDate;
import com.application.utilities.Utilities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * <h1>Logger Class</h1>
 * <p>
 * This Class is designed to store the entity record of a Log which is stored in the database.
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
@Getter
@Entity
@Table(name = "Log")
public class Log implements Serializable, DBTable<String> {
    /**
     * {@link String[]} Used to store the headers to be displayed in the invoiceItem table of the application
     */
    public static final String[] headers = {"Timestamp", "Type", "Level", "Message", "Client ID"};
    public static final String[] tblHeaders = {"Date", "Time", "Type", "Level", "Message", "Client ID"};

    /**
     * Stores the order of fields of logs in the table
     */
    public static final int DATE = 0;
    public static final int TIME = 1;
    public static final int TYPE = 2;
    public static final int LEVEL = 3;
    public static final int MESSAGE = 4;
    public static final int CLIENT_ID = 5;

    /**
     * {@link String} - The timestamp the log was recorded
     */
    @Id
    @Column(name = "timestamp")
    private String timestamp;

    /**
     * {@link Date} - The date the log was recorded
     */
    @Column(name = "date")
    private Date date;

    /**
     * {@link String} - The type of application (Server/ Client) where the log was recorded
     */
    @Column(name = "type")
    private String type;

    /**
     * {@link String} - The level (trace, debug, info, warn, error, fatal) of log recorded
     */
    @Column(name = "level")
    private String level;

    /**
     * {@link String} - The message that was logged
     */
    @Column(name = "message")
    private String message;

    /**
     * {@link String} - The unique id of the client
     */
    @Column(name = "clientID", insertable = false, updatable = false)
    private String clientID;

    /**
     * Default Constructor
     */
    public Log() {
        this.timestamp = "";
        this.date = null;
        this.type = "";
        this.level = "";
        this.message = "";
        this.clientID = "";
    }

    /**
     * Primary Constructor - Used to store all data for the log
     *
     * @param timestamp  The timestamp the log was recorded
     * @param type       The type of application (Server/ Client) where the log was recorded
     * @param level      The level (trace, debug, info, warn, error, fatal) of log recorded
     * @param message    The message that was logged
     * @param clientID   The unique id of the client
     */
    public Log(String timestamp, Date date, String type, String level, String message, String clientID) {
        this.timestamp = timestamp;
        this.date = date;
        this.type = type;
        this.level = level;
        this.message = message;
        this.clientID = clientID;
    }

    /**
     * @return the timestamp
     */
    @Override
    public String getIdNum() {
        return timestamp;
    }

    /**
     * Check if the imformation is vaild
     *
     * @return Returns whether it is (true/false)
     * @throws ParseException return when fail to return string properly
     */
    @Override
    public boolean isValid() throws ParseException {
        return !Utilities.isEmpty(timestamp, type, level, message);
    }

    /**
     * Converts Log Object to a String Array Format for Table Printing
     *
     * @return The log object in string array format
     */
    public String[] toArray() {
        return new String[]{timestamp, type, level, message.trim(), Utilities.checkNull(clientID)};
    }

    public Object[] toTableArray() {
        return new Object[]{date, timestamp, type, level, message.trim(), Utilities.checkNull(clientID)};
    }
}
