package Controller;

import Model.MongoConnnection;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;


/**
 * This class extends Webparse an abstract class which goal is to parse maetadata , the reason for this added layer of abstraction
 * is in case another class wants to use the jsoup parse but wants to handle the data differently allowing for flexibility and scalibility
 */
public class Mediator extends WebParse {
    private final MongoConnnection mongo;

    public Mediator(String connectionURL) throws IOException {
        super(connectionURL);
        this.mongo = new MongoConnnection();
    }

    /**
     * This method take a link and use it to call the method parse in the class WebParse{@link WebParse#parse()}
     * then takes the parsed data using the method getMetaElements {@link WebParse#getMetaElements()}
     * and breaks them in a way to insert them into a document in the MongoConnection cals using append method {@link MongoConnnection#append(String, String)}
     * Finally the method connect is called to insert the document we have been appending. {@link MongoConnnection#connect()}
     * @throws IOException We need this because of the connect method of Jsoup
     */
    public void AddWebToMongo() throws IOException {

        super.parse();
        ArrayList<Element> metaElements = super.getMetaElements();
        System.out.println(metaElements);
        for (Element metaTag : metaElements)
        {
           String title = metaTag.tagName();
           String link = metaTag.attr("abs:href");

           if(title.equals("title"))
           {
              String content = metaTag.text();
              mongo.append("meta"+title,content);
           }
           else if (!link.isEmpty())
           {
               mongo.append("URL",link);
           }
           else {
               String name = metaTag.attr("name");
               String content = metaTag.attr("content");
               mongo.append(name, content);
           }

        }
        mongo.connect();
    }

}
