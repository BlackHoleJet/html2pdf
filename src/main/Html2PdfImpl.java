package main;

import sun.misc.BASE64Encoder;
import sun.misc.IOUtils;

import javax.jws.WebService;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Created by adam on 16.9.15.
 */
@WebService(endpointInterface = "main.Html2Pdf")
public class Html2PdfImpl implements Html2Pdf {

	@Override
	public String presentation2pdf(String html) {

		try {
			File generatedXHTML = new File("/home/adam/Plocha/html2pdf-ws/prezentace.html");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(generatedXHTML), "UTF-8"));
			out.write(html);
			out.flush();
			out.close();

			String cmd = "wkhtmltopdf --orientation Landscape --print-media-type --dpi 600 --margin-top 2 --margin-bottom 2 --margin-right 2 --margin-left 2 --page-size A5 --disable-smart-shrinking /home/adam/Plocha/html2pdf-ws/prezentace.html /home/adam/Plocha/html2pdf-ws/prezentace.pdf";
			try {
				Process p = Runtime.getRuntime().exec(cmd);
				p.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Path inputPdf = Paths.get("/home/adam/Plocha/html2pdf-ws/prezentace.pdf");
			byte[] pdfByteArray = Files.readAllBytes(inputPdf);
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(pdfByteArray);
		} catch (Exception e) {
			return "FAILED";
		}
	}

	@Override
	public String test2pdf(String html) {
		System.out.println("TEST TO PDF -> incomming");
		try {
			File generatedXHTML = new File("/home/adam/Plocha/html2pdf-ws/test.html");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(generatedXHTML), "UTF-8"));
			out.write(html);
			out.flush();
			out.close();
			System.out.println("Spoustim konverzi ve wkhtmltopdf");
			String cmd = "wkhtmltopdf /home/adam/Plocha/html2pdf-ws/test.html /home/adam/Plocha/html2pdf-ws/test.pdf";
			try {
				Process p = Runtime.getRuntime().exec(cmd);
				p.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Zkonvertovano");
			Path inputPdf = Paths.get("/home/adam/Plocha/html2pdf-ws/test.pdf");
			byte[] pdfByteArray = Files.readAllBytes(inputPdf);
			BASE64Encoder encoder = new BASE64Encoder();
			System.out.println("Vracim pdf soubor v kodovani BASE64");
			return encoder.encode(pdfByteArray);
		} catch (Exception e) {
			System.out.println("FAILED");
			return "FAILED";
		}
	}
}
