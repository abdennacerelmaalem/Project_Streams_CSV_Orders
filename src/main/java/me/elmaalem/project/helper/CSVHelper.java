package me.elmaalem.project.helper;

import me.elmaalem.project.model.Orders;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Order ID","Order Date","Order Quantity","Sales","Ship Mode","Profit","Unit Price","Customer Name","Customer Segment","Product Category" };

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Orders> csvToOrders(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){

            List<Orders> orders = new ArrayList<Orders>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Orders order = new Orders(
                        Long.parseLong(csvRecord.get(0)),
                        csvRecord.get(1),
                        Integer.parseInt(csvRecord.get(2)),
                        Double.parseDouble( !csvRecord.get(3).isEmpty() ? csvRecord.get(3) : "0.00"),
                        csvRecord.get(4),
                        Double.parseDouble( !csvRecord.get(5).isEmpty() ? csvRecord.get(5) : "0.00"),
                        Double.parseDouble( !csvRecord.get(6).isEmpty() ? csvRecord.get(6) : "0.00"),
                        csvRecord.get(7),
                        csvRecord.get(8),
                        csvRecord.get(9)
                );
                orders.add(order);
            }
            return orders;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
