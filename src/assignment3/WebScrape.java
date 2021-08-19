package assignment3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScrape {
	
	public static File f_name;
	private static FileWriter f_writer;
	
	
	public static void parse() throws IOException {
		Document d = Jsoup.connect("https://www.ece.utexas.edu/people/faculty/edison-thomaz").get();
		Elements classElem = d.getElementsByClass("field field--name-body field--type-text-with-summary field--label-hidden field__item");
		Elements p = classElem.select("p");
		String bio = p.text();
		bio = bio.toLowerCase();
		StringBuilder bioBuilder = new StringBuilder(bio);
		int spaceCount = 0;
		for(int i=0;i<bioBuilder.length();i++) {
			if(((int)bioBuilder.charAt(i))!=32) {
				spaceCount = 0;
				if(((int)bioBuilder.charAt(i))<97) {
					bioBuilder.deleteCharAt(i);
					i--;
				}else if(((int)bioBuilder.charAt(i))>122) {
					bioBuilder.deleteCharAt(i);
					i--;
				}
			}else {
				spaceCount++;
			}
			if(spaceCount>1) {
				bio = bioBuilder.substring(0, i);
				break;
			}
		}
		if(spaceCount<2) {
			bio = bioBuilder.toString();
		}
		
		f_name = new File("bonus_corpus.txt");
		f_writer = new FileWriter("bonus_corpus.txt");
		PrintWriter printer = new PrintWriter(f_writer);
		printer.print("");
		printer.print(bio);
		printer.close();
		
	}
	
}
