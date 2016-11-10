package com.mohitpaudel.cnnnewsfeed;


import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class ParseNewsFeed {
    private static final String TAG = "ParseNewsFeed";
    ArrayList<FeedEntry> newsList;

    public ParseNewsFeed() {
        this.newsList = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getNewsList() {
        return newsList;
    }


    public boolean parse(String xmlData) {
        boolean status = true;
        boolean inItem = false;
        FeedEntry currentRecord = null;
        String textContent = "";
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("item")) {
                            currentRecord = new FeedEntry();
                            inItem = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textContent=xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inItem)
                        {
                            if(tagName.equalsIgnoreCase("item"))
                            {
                                newsList.add(currentRecord);
                                inItem=false;
                            }else if(tagName.equalsIgnoreCase("title"))
                            {
                                currentRecord.setTitle(textContent);
                            }else if(tagName.equalsIgnoreCase("description"))
                            {
                                currentRecord.setDescription(textContent);
                            }else if(tagName.equalsIgnoreCase("link"))
                            {
                                currentRecord.setLink(textContent);
                            }else if(tagName.equalsIgnoreCase("pubDate"))
                            {
                                currentRecord.setPubDate(textContent);
                            }
                        }
                        break;
                    default:
                        //no code to write
                }
                eventType=xpp.next();
            }

            /*for(FeedEntry e:getNewsList())
            {
                Log.d(TAG, "parse: The result after parsing is\t"+e.toString());
            }*/

        } catch (XmlPullParserException ex) {
            status=false;
            Log.e(TAG, "parse: The exception that occured while parsing the application is " + ex.getLocalizedMessage());
        }catch(IOException ex)
        {
            status=false;
            Log.e(TAG, "parse: The error is "+ex.getLocalizedMessage() );
        }
        return status;
    }


}

