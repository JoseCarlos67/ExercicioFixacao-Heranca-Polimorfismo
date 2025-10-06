package application;

import entities.ImportedProduct;
import entities.Product;
import entities.UsedProduct;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        ByteArrayInputStream fakeInput = autofillingOfData();
        System.setIn(fakeInput);

        Scanner scanner = new Scanner(System.in);

        List<Product> productList = new ArrayList<>();
        registerProduct(productList, scanner);

        showPriceTags(productList);

        scanner.close();
    }

    private static ByteArrayInputStream autofillingOfData() {
        String fakeInputString = """
                3
                i
                Tablet
                260.00
                20.00
                c
                Notebook
                1100.00
                u
                Iphone
                400.00
                15/03/2017
                """;
        return new ByteArrayInputStream(fakeInputString.getBytes());
    }

    private static void registerProduct(List<Product> productList, Scanner scanner) {
        System.out.print("Enter the number of products: ");
        byte quantityItems = scanner.nextByte();

        for (byte i = 1; i <= quantityItems; i++) {
            System.out.println("Product #" + i + "  data:");

            System.out.print("Common, used or imported (c/u/i): ");
            char productType = scanner.next().charAt(0);

            System.out.print("Name: ");
            scanner.nextLine();
            String productName = scanner.nextLine();

            System.out.print("Price: ");
            double productPrice = scanner.nextDouble();

            if (productType == 'u') {
                System.out.print("Manufacture date (DD/MM/YYYY): ");
                LocalDate dateProduct = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                productList.add(new UsedProduct(productName, productPrice, dateProduct));
            } else if (productType == 'c') {
                productList.add(new Product(productName, productPrice));
            } else {
                System.out.print("Customs fee: ");
                double productCustomsFee = scanner.nextDouble();
                productList.add(new ImportedProduct(productName, productPrice, productCustomsFee));
            }
        }
    }

    private static void showPriceTags(List<Product> productList) {
        System.out.println("\n\nPRICE TAGS");
        for (Product product : productList) {
            System.out.println(product.priceTag());
        }
    }
}
