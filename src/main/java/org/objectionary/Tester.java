package org.objectionary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This file is temporary here.
 * It will be removed once the integration tests are implemented.
 */
public class Tester {

    /**
     * Runs the integration tests.
     * @param args the command line arguments
     */
      public static void main(String[] args) {
          final List<String> lines = new ArrayList<>();
          try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
              String line;
              while ((line = reader.readLine()) != null) {
                  lines.add(line);
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
          final Parser parser = new Parser(String.join("\n", lines));
          final Flatter flatter = new Flatter(parser.parse());
          final String output = flatter.flat().toString();
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[0].replace("input", "flat")))) {
              writer.write(output);
              writer.flush();
          } catch (IOException e) {
              System.out.println("An error occurred while writing to the file: " + e.getMessage());
          }
      }
}
