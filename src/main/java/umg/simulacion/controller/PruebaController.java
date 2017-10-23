package umg.simulacion.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import umg.simulacion.model.DocWord;
import umg.simulacion.service.PruebaService;

@RestController
public class PruebaController {
	
	@Autowired
	PruebaService pruebaService;
	
	@RequestMapping(value = "/insert",method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String responseInsert(@RequestParam("file") MultipartFile file){
		String response = "{\"message\": \"Error\"}";
		try {
			DocWord word = new DocWord();
			if(file != null){				
				byte[] byteArr = file.getBytes();
				word.setArchivo(byteArr);
				pruebaService.insertDoc(word);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/word")
	public void getWord(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//final ServletContext servletContext = request.getSession().getServletContext();		
	    //final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");	    
	    //final String temperotyFilePath = tempDirectory.getAbsolutePath();
	    
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "filename=docword.pdf");
	    
	    try {
	    	DocWord word = pruebaService.getWordById(1);
	    	Document doc = new Document();
	    	if(word != null){
	    		 InputStream fileInputStream = new ByteArrayInputStream(word.getArchivo());
	    		 BufferedInputStream buffInputStream = new BufferedInputStream(fileInputStream);
	    		 HWPFDocument document = new HWPFDocument(new POIFSFileSystem(buffInputStream)); 
	    		 Range range = document.getRange(); 
	             range.sanityCheck();
	             range.replaceText("<NUMERO_DE_CONTRATO>", "12345");
	    		
	    		/*ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    		document.write(baos);*/
	    		
		    	//baos = convertToByteArrayOutputStream(document.getArchivo());
		    	OutputStream os = response.getOutputStream();
		    	/*baos.writeTo(os);
		        os.flush();*/
		        
		        WordExtractor we = new WordExtractor(document);
                PdfWriter writter = PdfWriter.getInstance(doc, os);
                doc.open();
                writter.setPageEmpty(true);
                doc.newPage();
                writter.setPageEmpty(true);


                String[] paragraphs = we.getParagraphText();
                for (int i = 0; i < paragraphs.length; i++) {

                        paragraphs[i] = paragraphs[i].replaceAll("\\cM?\r?\n", "");
                         // add the paragraph to the document
                         Paragraph p = new Paragraph(paragraphs[i]);
                         p.setAlignment(Element.ALIGN_JUSTIFIED);
                         doc.add(p);
                }
                doc.close();
                os.flush();
               
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ByteArrayOutputStream convertToByteArrayOutputStream(byte[] file) {
		 
		InputStream inputStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
 
			inputStream = new ByteArrayInputStream(file);
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();
 
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baos;
	}

}
