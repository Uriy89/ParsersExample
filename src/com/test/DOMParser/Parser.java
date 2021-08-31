package com.test.DOMParser;

import com.test.model.People;
import com.test.model.Root;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final String TAG_NAME_MAIN = "name";
    private static final String TAG_PEOPLE = "people";
    private static final String TAG_ELEMENT = "element";
    private static final String TAG_NAME = "name";
    private static final String TAG_AGE = "age";

    public Root parse() {
        Root root = new Root();

        Document doc;
        try {
            doc = buildDocument();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Node node = doc.getFirstChild();
        NodeList list = node.getChildNodes();

        //ходит по содержимому root
        String mainName = null;
        Node peopleNode = null;
        for(int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
            switch (list.item(i).getNodeName()) {
                case TAG_NAME_MAIN : {
                    mainName = list.item(i).getTextContent();
                    break;
                }
                case TAG_PEOPLE : {
                    peopleNode = list.item(i);
                    break;
                }
            }
        }

        if(peopleNode == null) return null;

        List<People> peopleList = parsePeople(peopleNode);

        root.setName(mainName);
        root.setPeople(peopleList);
        try {
            createXMLNameFile(peopleList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    private Document buildDocument() throws Exception{
        File file = new File("test.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        return dbf.newDocumentBuilder().parse(file);
    }

    private void createXMLNameFile(List<People> peopleList) throws IOException {
        Writer writer = null;
        for (int n = 0; n < peopleList.size(); n++) {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(peopleList.get(n).getName() + ".xml"), "utf-8"));
            writer.write(peopleList.get(n).toString());
            writer.close();
        }
    }

    private List<People> parsePeople(Node peopleNode) {
        List<People> peopleList = new ArrayList<>();
        NodeList peopleChilds = peopleNode.getChildNodes();
        for(int i = 0; i < peopleChilds.getLength(); i++) {
            if (peopleChilds.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
            if(!peopleChilds.item(i).getNodeName().equals(TAG_ELEMENT)) continue;
            People people = parseElement(peopleChilds.item(i));
            peopleList.add(people);
        }
        return peopleList;
    }

    private People parseElement(Node elNode) {
        String name = "";
        int age = 0;
        NodeList elChild = elNode.getChildNodes();
        for(int j = 0; j < elChild.getLength(); j++) {
            if (elChild.item(j).getNodeType() != Node.ELEMENT_NODE) continue;
            switch (elChild.item(j).getNodeName()) {
                case TAG_NAME : {
                    name = elChild.item(j).getTextContent();
                    break;
                }
                case TAG_AGE : {
                    age = Integer.valueOf(elChild.item(j).getTextContent());
                    break;
                }
            }
        }
        People people = new People(name, age);
        return people;
    }
}