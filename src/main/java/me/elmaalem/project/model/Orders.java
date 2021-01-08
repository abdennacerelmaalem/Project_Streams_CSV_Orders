package me.elmaalem.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class
Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long orderId;
    private LocalDate date;
    private int quantity;
    private double sales;
    private String mode;
    private double profit;
    private double unitPrice;
    private String customerName;
    private String customerSegment;
    private String productCategory;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");

   public Orders(long orderId, String date, int quantity, double sales, String mode, double profit, double unitPrice, String customerName, String customerSegment, String productCategory) {
        this.orderId = orderId;
        this.date = LocalDate.parse(date,FORMATTER);
        this.quantity = quantity;
        this.sales = sales;
        this.mode = mode;
        this.profit = profit;
        this.unitPrice = unitPrice;
        this.customerName = customerName;
        this.customerSegment = customerSegment;
        this.productCategory = productCategory;
    }


}
