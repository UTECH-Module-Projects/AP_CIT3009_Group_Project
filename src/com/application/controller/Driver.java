package com.application.controller;

import com.application.models.Date;
import com.application.models.tables.Customer;
import com.application.models.tables.Employee;

import java.util.Objects;

public class Driver {
    public static void main(String[] args) {
        Employee emp1 = new Employee("John Brown", new Date(23, 07, 2000), "5 Curry Fat, Chicken Town", "876 123-4567", "johnnybrown43@gmail.com", "Cashier", "ACC12");
        Employee emp2 = new Employee("Harry Potter", new Date(23, 07, 2000), "5 Curry Fat, Chicken Town", "876 123-4567", "johnnybrown43@gmail.com", "Cashier", "ACC12");
        Employee emp3 = new Employee("Mary Jane", new Date(23, 07, 2000), "5 Curry Fat, Chicken Town", "876 123-4567", "johnnybrown43@gmail.com", "Cashier", "ACC12");

        emp1.create();
        emp2.create();
        emp3.create();

        Objects.requireNonNull(Employee.getList()).forEach(System.out::println);
    }
}
