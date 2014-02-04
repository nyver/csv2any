package com.nyver.csv2any;

import java.io.*;

public class Main {

    public static final String INPUT_FILENAME = "csv2any.csv";
    public static final String OUTPUT_FILENAME = "csv2any.out";
    public static final String TEMPLATE_FILENAME = "csv2any.tpl";

    public static final String DELIMITER = ";";

    public static void main(String[] args) {
        File templateFile = new File(TEMPLATE_FILENAME);
        if (templateFile.exists() && templateFile.canRead()) {

            try(BufferedReader templateBufferedReader = new BufferedReader(new FileReader(templateFile))) {

                StringBuilder templateBuffer = new StringBuilder(templateFile.length() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) templateFile.length());
                String line;

                while((line = templateBufferedReader.readLine()) != null) {
                    templateBuffer.append(line).append("\n");
                }

                templateBufferedReader.close();

                File inputFile = new File(INPUT_FILENAME);
                if (inputFile.exists() && inputFile.canRead()) {
                    try(BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))) {

                        StringBuilder builder = new StringBuilder();
                        String templateString;

                        while ((line = bufferedReader.readLine()) != null) {
                            templateString = templateBuffer.toString();
                            String[] parts = line.split(DELIMITER);

                            if (parts.length > 0) {
                                for(int i = 0; i < parts.length; i++) {
                                    templateString = templateString.replaceAll(String.format("\\$\\{%d\\}", i), parts[i]);
                                }
                            }

                            builder.append(templateString);
                        }

                        bufferedReader.close();

                        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(OUTPUT_FILENAME)))) {
                            bufferedWriter.write(builder.toString());
                            bufferedWriter.close();
                        }

                        System.out.print(builder.toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    System.out.println(String.format("CSV file %s is not exists or not readable.", INPUT_FILENAME));
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println(String.format("Template file %s is not exists or not readable.", TEMPLATE_FILENAME));
        }
    }
}
