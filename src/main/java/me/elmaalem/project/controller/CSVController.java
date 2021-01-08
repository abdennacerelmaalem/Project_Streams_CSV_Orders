package me.elmaalem.project.controller;

import me.elmaalem.project.helper.CSVHelper;
import me.elmaalem.project.model.Orders;
import me.elmaalem.project.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/csv")
public class CSVController {

        @Autowired
        CSVService fileService;

        @PostMapping("/upload")
        public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
            String message = "";

            if (CSVHelper.hasCSVFormat(file)) {
                fileService.save(file);

                try {
                    fileService.save(file);
                    message = "Uploaded the file successfully: " + file.getOriginalFilename();
                    return ResponseEntity.status(HttpStatus.OK).body( "\" message \": \" "+ message +" \"");
                } catch (Exception e) {
                    message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("\" message \": \" "+ message +" \"");
                }
            }
            message = "Please upload a csv file!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\" message \": \" "+ message +" \"");
        }
    //1- SELECT * FROM Order;
    @GetMapping("/orders")
    public ResponseEntity<List<Orders>> getAllOrders () {
        try {
            List<Orders> orders = fileService.getAllOrders();

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //2-Stream :Get Order By Customer Name
    @GetMapping(value = "/orders/findByCustomerName/{customerName}")
    public ResponseEntity<List<Orders>> getOrdersByCustomerName (@PathVariable String customerName) {
        try {
            List<Orders> orders = fileService.getOrdersByCustomerName(customerName);

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //3-Stream :Get Order By Customer Name and Order Date
    @GetMapping("/orders/findByCustomerNameAndDate/{customerName}/{orderDate}")
    public ResponseEntity<List<Orders>> getOrdersByCustomerNameAndDate (@PathVariable String customerName,@PathVariable String orderDate) {

        try {
            List<Orders> orders = fileService.getOrdersByCustomerNameAndDate(customerName,orderDate);

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //4-Stream :Get Order By Product Category When Profit must be grater than 0
    @GetMapping("/orders/findByProductCategoryAndMustHaveProfitPositive/{productCategory}")
    public ResponseEntity<List<Orders>> getOrdersByProductCategoryAndMustHaveProfitPositive (@PathVariable String productCategory) {

        try {
            List<Orders> orders = fileService.getOrdersByProductCategoryAndMustHaveProfitPositive(productCategory);

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //5-Stream :Get Order By Product Category When Profit must be grater than 0
    @GetMapping("/orders/findOrdersInPeriodWhichHaveProfitPositiveAndSortedBySales/{fromDate}/{toDate}")
    public ResponseEntity<List<Orders>> getOrdersInPeriodWhichHaveProfitPositiveAndSortedBySales (@PathVariable String fromDate, @PathVariable String toDate) {

        try {
            List<Orders> orders = fileService.getOrdersInPeriodWhichHaveProfitPositiveAndSortedBySales(fromDate,toDate);

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
