package com.test.SAXParser;

import com.test.model.Root;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class SaxMyParser {
    public Root parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SaxParserHandler handler = new SaxParserHandler();
        File file = new File("test.xml");
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            parser.parse(file, handler);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return handler.getRoot();
    }
}
