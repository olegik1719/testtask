package com.github.olegik1719.testtask;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class TextTest {

    Text textCurrent;

    @Before
    public void setUp() throws Exception {
        //String string = "Hello World!";
        String string = "asdasdaasdasfasdfsdf";
        InputStream stream = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
        textCurrent = new Text(stream);
        //textCurrent;
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