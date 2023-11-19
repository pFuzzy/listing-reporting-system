package hu.fazekas.util;

import hu.fazekas.dto.ListingReportDto;
import hu.fazekas.dto.MonthlyListingReportDto;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.SocketException;
import java.util.List;

public class JsonHandler {

    public void createJsonReport(ListingReportDto listingReport, List<MonthlyListingReportDto> monthlyListingReports, List<Integer> monthsWithoutListing){
        JSONObject listingReportJson = new JSONObject();

        JSONArray monthlyListingReportsJson = new JSONArray();
        JSONArray monthsWithoutListingJson = new JSONArray();

        for (MonthlyListingReportDto monthlyListingReport :
                monthlyListingReports) {
            JSONObject monthlyListingReportJson = new JSONObject();

            monthlyListingReportJson.put("Month: ", monthlyListingReport.getMonth());
            monthlyListingReportJson.put("Total eBay listing count per month: ", monthlyListingReport.getEbayListingCount());
            monthlyListingReportJson.put("Total eBay listing price per month: ", monthlyListingReport.getEbayTotalListingPrice());
            monthlyListingReportJson.put("Average eBay listing price per month: ", monthlyListingReport.getAverageEbayListingPrice());
            monthlyListingReportJson.put("Total Amazon listing count per month: ", monthlyListingReport.getAmazonListingCount());
            monthlyListingReportJson.put("Total Amazon listing price per month: ", monthlyListingReport.getAmazonTotalListingPrice());
            monthlyListingReportJson.put("Average Amazon listing price per month: ", monthlyListingReport.getAverageAmazonListingPrice());
            monthlyListingReportJson.put("Best lister email of the month: ", monthlyListingReport.getBestListerEmailAddress());

            monthlyListingReportsJson.add(monthlyListingReport);
        }

        monthsWithoutListingJson.addAll(monthsWithoutListing);

        listingReportJson.put("Total listing count: ", listingReport.getTotalCount());
        listingReportJson.put("Total eBay listing count: ", listingReport.getEbayListingCount());
        listingReportJson.put("Total eBay listing price: ", listingReport.getEbayTotalListingPrice());
        listingReportJson.put("Average eBay listing price: ", listingReport.getAverageEbayListingPrice());
        listingReportJson.put("Total Amazon listing count: ", listingReport.getAmazonListingCount());
        listingReportJson.put("Total Amazon listing price: ", listingReport.getAmazonTotalListingPrice());
        listingReportJson.put("Average Amazon listing price: ", listingReport.getAverageAmazonListingPrice());
        listingReportJson.put("Best lister email address: ", listingReport.getBestListerEmailAddress());
        listingReportJson.put("Montly reports: ", monthlyListingReports);
        listingReportJson.put("Months without listing: ", monthsWithoutListingJson);


        File file = new File("jsonReport.json");

        try(FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(listingReportJson.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        uploadToFTP();

    }

    public void uploadToFTP(){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("127.0.0.1", 21);
            ftpClient.login("fazi", "12345");
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String fileName = "C:\\Users\\Fazi\\Desktop\\listing-reporting-system\\ListingReportingSystem\\jsonReport.json";
            File file = new File(fileName);

            InputStream inputStream = new FileInputStream(file);

            ftpClient.storeFile("jsonReport.json", inputStream);

            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
