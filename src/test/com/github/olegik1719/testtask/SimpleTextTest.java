package com.github.olegik1719.testtask;

import org.junit.Test;

public class SimpleTextTest {

    @Test
    public void searchAll() {
        SimpleText simpleText = new SimpleText("asdasdaasdasfasdfsdf");
        System.out.println(simpleText.searchAll("asd"));
    }

    @Test
    public void searchFirsts() {
    }

    @Test
    public void charAt() {
    }
}