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

import java.text.ParseException;

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
public class Log implements DBTable<String> {
    /**
     * {@link String[]} Used to store the headers to be displayed in the invoiceItem table of the application
     */
    public static final String[] headers = {"Timestamp", "Type", "Level", "Message", "Client ID"};

    /**
     * Stores the order of fields of logs in the table
     */
    public static final int TIMESTAMP = 0;
    public static final int TYPE = 1;
    public static final int LEVEL = 2;
    public static final int MESSAGE = 3;
    public static final int CLIENT_ID = 4;

    /**
     * {@link String} - The timestamp the log was recorded
     */
    @Id
    @Column(name = "timestamp")
    private String timestamp;

    /**
     * {@link EntityDate} - The date the log was recorded
     */
    @Column(name = "date")
    private EntityDate entityDate;

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
        this.entityDate = null;
        this.type = "";
        this.level = "";
        this.message = "";
        this.clientID = "";
    }

    /**
     * Primary Constructor - Used to store all data for the log
     *
     * @param timestamp The timestamp the log was recorded
     * @param entityDate      The date the log was recorded
     * @param type      The type of application (Server/ Client) where the log was recorded
     * @param level     The level (trace, debug, info, warn, error, fatal) of log recorded
     * @param message   The message that was logged
     * @param clientID  The unique id of the client
     */
    public Log(String timestamp, EntityDate entityDate, String type, String level, String message, String clientID) {
        this.timestamp = timestamp;
        this.entityDate = entityDate;
        this.type = type;
        this.level = level;
        this.message = message;
        this.clientID = clientID;
    }

    @Override
    public String getIdNum() {
        return timestamp;
    }

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
        return new String[]{timestamp, type, level, message, clientID};
    }
}
