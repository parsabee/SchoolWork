package edu.uoregon.parsab.tideappvol4;


import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

public class ParseHandler extends DefaultHandler {
    private TideItems tideItems;
    private TideItem item;
    private boolean isCity = false;
    private boolean isDate = false;
    private boolean isDay = false;
    private boolean isTime = false;
    private boolean isPred = false;
    private boolean highlow = false;

    public TideItems getItems() {
        return tideItems;
    }

    @Override
    public void startDocument() throws SAXException {
        tideItems = new TideItems();
        item = new TideItem();
    }

    @Override
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) throws SAXException {

        if (qName.equals("stationname")) {
            isCity = true;;
            return;
        }
        else if (qName.equals("item")) {
            item = new TideItem();
            return;
        }
        else if (qName.equals("date")) {
            isDate = true;
            return;
        }
        else if (qName.equals("day")) {
            isDay = true;
            return;
        }
        else if (qName.equals("time")) {
            isTime = true;
            return;
        }
        else if (qName.equals("pred")) {
            isPred = true;
            return;
        }
        else if (qName.equals("highlow")) {
            highlow = true;
            return;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName,
                           String qName) throws SAXException
    {
        if (qName.equals("item")) {
            tideItems.add(item);
        }
        return;
    }

    @Override
    public void characters(char ch[], int start, int length)
    {
        String s = new String(ch, start, length);
        if (isCity) {
            tideItems.setCity(s);
            isCity = false;
        }
        else if (isDate) {
            item.setDate(s);
            isDate = false;
        }
        else if (isDay) {
            item.setDay(s);
            isDay = false;
        }
        else if (isTime) {
            item.setTime(s);
            isTime = false;
        }
        else if (isPred) {
            item.setPred(s);
            isPred = false;
        }
        else if (highlow) {
            item.setHighlow(s);
            highlow = false;
        }
    }
}

