package assignment3;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    /**
     * Example program using GraphPoet.
     */


        /**
         * Generate example poetry.
         *
         * @param args unused
         * @throws IOException if a poet corpus file cannot be found or read
         */
        public static void main(String[] args) throws IOException {
        	WebScrape.parse();
            final GraphPoet nimoy = new GraphPoet(new File("bonus_corpus.txt"));
            System.out.println(nimoy.poem(new File("bonus_input.txt")));
            
        }
}
