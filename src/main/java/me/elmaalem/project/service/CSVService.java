package me.elmaalem.project.service;

import me.elmaalem.project.helper.CSVHelper;
import me.elmaalem.project.model.Orders;
import me.elmaalem.project.repository.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CSVService {

    @Autowired
    OrdersRepo repository;

    public void save(MultipartFile file) {
        try {
            List<Orders> orders = CSVHelper.csvToOrders(file.getInputStream());
            repository.saveAll(orders);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<Orders> getAllOrders() {
        return repository.findAll();
    }


    public List<Orders> getOrdersByCustomerName(String customerName){

        return repository.findAll().stream()
                .filter(s->s.getCustomerName().contentEquals(customerName))
                .collect(Collectors.toList());
    }

    public List<Orders> getOrdersByCustomerNameAndDate(String name,String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate dateFormat= LocalDate.parse(date,formatter);

        return repository.findAll().stream()
                .filter(s->s.getCustomerName().contentEquals(name))
                .filter(s->s.getDate().isEqual(dateFormat))
                .collect(Collectors.toList());
    }

    public List<Orders> getOrdersByProductCategoryAndMustHaveProfitPositive(String productCategory) {

        return repository.findAll().stream()
                .filter(s->s.getProductCategory().contentEquals(productCategory))
                .filter(s->s.getProfit() > 0)
                .collect(Collectors.toList());
    }

    public List<Orders> getOrdersInPeriodWhichHaveProfitPositiveAndSortedBySales(String fromDate, String toDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate FromDate= LocalDate.parse(fromDate,formatter);
        LocalDate ToDate= LocalDate.parse(toDate,formatter);

        return repository.findAll().stream()
                .filter(s -> s.getProfit() > 0 && s.getDate().isAfter(FromDate) && s.getDate().isBefore(ToDate))
                .sorted(Comparator.comparing(Orders::getSales))
                .collect(Collectors.toList());
    }
}

