package com.talan.polaris.controllers;

import com.talan.polaris.services.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.IOException;
import java.nio.ByteOrder;

import com.aspose.pdf.*;
import com.aspose.pdf.internal.html.rendering.image.ImageFormat;

import javax.imageio.stream.IIOByteBuffer;
import javax.imageio.stream.ImageOutputStream;

@RestController
@RequestMapping("/img")
public class CvController {

    @Autowired
    CvService cvService;

   @GetMapping("/extract/{id}")
    public void ExtractImageFromPdf(@PathVariable("id") String id ) throws IOException {

      // String path=cvService.findPathCvById(id);
       String path="C:/Users/hp/OneDrive/Bureau/CV_2021-07-31_Rihem_Garrouch (2).pdf";
       System.out.println(path);

       // Open document
       Document pdfDocument = new Document(path);
     //  Document pdfDocument = new Document("C:/Users/hp/OneDrive/Bureau/CV_2021-07-31_Rihem_Garrouch (2).pdf");
       // Extract a particular image
       XImage xImage = pdfDocument.getPages().get_Item(1).getResources().getImages().get_Item(1);
       FileOutputStream outputImage = new FileOutputStream("C:/Users/hp/OneDrive/Bureau/output.png");

       // Save output image
       xImage.save(outputImage, ImageFormat.Png);
       outputImage.close();
       System.out.println("succees");



    }

}
