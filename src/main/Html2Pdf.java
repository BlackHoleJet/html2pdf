package main;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by adam on 16.9.15.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)

public interface Html2Pdf {

	@WebMethod
	String presentation2pdf(String html);

	@WebMethod
	String test2pdf(String html);
}
