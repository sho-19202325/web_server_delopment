package com.webserverdevelopment.henacat.util;
import java.io.*;

public interface ResponseHeaderGenerator {
  void generate(OutputStream output) throws IOException;
}
