package com.github.olegik1719.testtask;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

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
        String substring = "asd";
        //System.out.println(textCurrent.searchAll(substring));
        System.out.println(textCurrent.getResults(substring,2,3));
    }
}