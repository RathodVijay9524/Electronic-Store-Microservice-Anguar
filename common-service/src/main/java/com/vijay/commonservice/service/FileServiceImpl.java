package com.vijay.commonservice.service;

import com.vijay.commonservice.exception.BadApiRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Log4j2
public class FileServiceImpl implements FileService{

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        //abc.png
        String originalFilename = file.getOriginalFilename();
        log.info("Filename : {}", originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = path  + fileNameWithExtension;

        log.info("full image path: {} ", fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            //file save
            log.info("file extension is {} ", extension);
            File folder = new File(path);
            if (!folder.exists()) {
                //create the folder
                folder.mkdirs();

            }

            //upload
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;

        } else {
            throw new BadApiRequestException("File with this " + extension + " not allowed !!");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
