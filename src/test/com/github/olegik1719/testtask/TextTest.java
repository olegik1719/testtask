package com.github.olegik1719.testtask;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class TextTest {

    Text textCurrent;

    @Before
    public void setUp() throws Exception {
        File input = new File ("res/input.test");
        InputStream stream = new FileInputStream(input);
        textCurrent = new Text(stream);
    }

    @Test
    public void getText() {
        OutputStream out = textCurrent.getText();
        System.out.println(out.toString());
    }

    @Test
    public void searchAll() {
        System.out.println(textCurrent.searchAll("asd"));
    }
}