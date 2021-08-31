package com.test.SAXParser;

import com.test.model.People;
import com.test.model.Root;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SaxParserHandler extends DefaultHandler {
    private static final String TAG_NAME_MAIN = "name";
    private static final String TAG_PEOPLE = "people";
    private static final String TAG_ELEMENT = "element";
    private static final String TAG_NAME = "name";
    private static final String TAG_AGE = "age";

    private Root root = new Root();
    private List<People> peopleList = new ArrayList<>();
    private String currentTagName;
    private boolean isPeople = false;
    private boolean isElement = false;

    private String name;
    private int age;

    public Root getRoot() {
        return root;
    }

    @Override
    public void startDocument() throws SAXException {
      //  System.out.println("Start doc");
    }

    @Override
    public void endDocument() throws SAXException {
       // System.out.println("End doc");
        root.setPeople(peopleList);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentTagName = qName;
        if (currentTagName.equals(TAG_PEOPLE)) {
            isPeople = true;
        } else if (currentTagName.equals(TAG_ELEMENT)) {
            isElement = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(TAG_PEOPLE)) {
            isPeople = false;
        } else if (qName.equals(TAG_ELEMENT)) {
            isElement = false;
            People people = new People(name, age);
            peopleList.add(people);
        }
        currentTagName = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //System.out.println("ch" + new String(ch, start, length));
        if (currentTagName == null) return;
        if (!isPeople && currentTagName.equals(TAG_NAME_MAIN)) {
            root.setName(new String(ch, start, length));
        }
        if(isPeople && isElement) {
            if(currentTagName.equals(TAG_AGE)) {
                age = Integer.valueOf(new String(ch, start, length));
            } else if (currentTagName.equals(TAG_NAME)) {
                name = new String(ch, start, length);
            }
        }
    }
}
