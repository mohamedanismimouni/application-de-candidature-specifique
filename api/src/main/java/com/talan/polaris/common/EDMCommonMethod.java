package com.talan.polaris.common;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Imen Mechergui
 * @since 1.0.0
 */
@NoArgsConstructor
public class EDMCommonMethod {
    private static final Logger LOGGER = LoggerFactory.getLogger(EDMCommonMethod.class);


    /**
     * @param file
     * @return
     * @throws IOException
     */
    public static File convert(MultipartFile file) throws IOException {
        File convFile = null;
        FileOutputStream fos = null;
        try {
            convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (fos != null)
                fos.close();
        }
        return convFile;
    }


}
