package com.rbkmoney.binbase.batch.reader.factory;

import org.springframework.batch.item.file.BufferedReaderFactory;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipBufferedReaderFactory implements BufferedReaderFactory {

    @Override
    public BufferedReader create(Resource resource, String encoding)
            throws IOException {
        ZipFile zip = new ZipFile(resource.getFile());
        for (Enumeration e = zip.entries(); e.hasMoreElements(); ) {
            ZipEntry zipEntry = (ZipEntry) e.nextElement();
            return new BufferedReader(new InputStreamReader(zip.getInputStream(zipEntry)));
        }
        throw new FileNotFoundException("File not found in " + resource);
    }

}
