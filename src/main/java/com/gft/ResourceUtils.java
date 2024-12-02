package com.gft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ResourceUtils {
    @Value("classpath:notes/*.md")
    private Resource[] files;

    public List<String> getResourceFilenames() {
        List<String> filenames = new ArrayList<>();

        for (int i=0; i<files.length; i++) {
            filenames.add(files[i].getFilename());
        }

        return filenames;
    }

    public List<String> getResourceContentsByPath(String path) throws IOException {
        List<String> contents = new ArrayList<>();

        try (
            InputStream in = getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in))
        ) {
            String resource;

            while ((resource = br.readLine()) != null) {
                contents.add(resource);
            }
        }

        return contents;
    }

    private InputStream getResourceAsStream(String resource) {
        final InputStream in = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? getClass().getResourceAsStream(resource) : in;
    }

    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
