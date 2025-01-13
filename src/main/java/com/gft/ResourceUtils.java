package com.gft;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.Optional;

@Service
public class ResourceUtils {
  @Value("classpath:notes/*.md")
  private Resource[] files;

  public List<String> getResourceFilenames() {
    List<String> filenames = new ArrayList<>();

    for (int i = 0; i < files.length; i++) {
      filenames.add(files[i].getFilename().replaceAll(".md$", ""));
    }

    return filenames;
  }

  public List<String> getResourceContentsByPath(String path) throws IOException {
    List<String> contents = new ArrayList<>();

    try (InputStream in = getResourceAsStream(path + ".md");
      BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
        String resource;

        while ((resource = br.readLine()) != null) {
          contents.add(resource);
        }
      }

    return contents;
  }

  public void writeContentsToResource(String title, List<String> contents, String path) throws IOException, Exception {
    File file = null;

    for (int i = 0; i < files.length; i++) {
      if (files[i].getFilename().replaceAll(".md$", "").equals(title)) file = files[i].getFile();
    }

    if (file == null) throw new Exception("Couldn't find the given resource");
    file.delete();

    ClassLoader classLoader = getClass().getClassLoader();
    File newf = new File(classLoader.getResource(".").getFile() + path);
    if (!newf.createNewFile()) {
      throw new Exception("Couldn't write to the the given resource");
    }

    FileWriter f2 = new FileWriter(newf, false);
    Optional<String> opt = contents.stream().reduce((tot, line) -> tot + "\n" + line);
    f2.write(opt.get());
    f2.close();
  }

  private InputStream getResourceAsStream(String resource) {
    final InputStream in = getContextClassLoader().getResourceAsStream(resource);

    return in == null ? getClass().getResourceAsStream(resource) : in;
  }

  private ClassLoader getContextClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }
}
