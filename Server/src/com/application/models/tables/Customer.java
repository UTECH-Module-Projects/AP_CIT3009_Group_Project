package com.application.models.tables;

import com.application.models.misc.Date;
import com.application.models.misc.Person;
import com.application.utilities.Utilities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Getter
@Entity
@Table(name = "Customer")
public class Customer extends Person {
    public static final String[] fields = {"ID Number", "Full Name", "Date of Birth", "Address", "Phone Number", "Email", "Date of Membership", "Date of Expiry"};
    public static final int idLength = 8;

    @Column(name = "dom")
    private Date dom;
    @Column(name = "dome")
    private Date dome;

    public Customer() {
        super();
        this.dom = new Date();
        this.dome = new Date();
    }

    public Customer(String idNum, String name, Date dob, String address, String phoneNum, String email, Date dom, Date dome) {
        super(idNum, name, dob, address, phoneNum, email);
        this.dom = dom;
        this.dome = dome;
    }

    public Customer(String idNum, String name, Date dob, String address, String phoneNum, String email) {
        super(idNum, name, dob, address, phoneNum, email);
        this.dom = null;
        this.dome = null;
    }

    public Customer(Customer customer) {
        super(customer);
        this.dom = new Date(customer.getDom());
        this.dome = new Date(customer.getDome());
    }

    /**
     * Convert customer Object to string
     * @return
     */
    @Override
    public String toString() {
        return "Customer{" +
                "idNum = '" + idNum + '\'' +
                ", dom = " + dom +
                ", dome = " + dome +
                ", name = '" + name + '\'' +
                ", dob = " + dob +
                ", address = '" + address + '\'' +
                ", phoneNum = '" + phoneNum + '\'' +
                ", email = '" + email + '\'' +
                '}';
    }

    public String[] toArray() {
        return new String[]{idNum, name, Utilities.checkNull(dob), address, phoneNum, email, Utilities.checkNull(dom), Utilities.checkNull(dome)};
    }
}
