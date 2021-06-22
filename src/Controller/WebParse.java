package Controller;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

import java.io.IOException;

/**
 *This class is used to parse the link we got from the user using the Jsoup library
 */
public abstract class WebParse {
    public Document doc;
    public Elements metaTags;
    private ArrayList<Element> metaElements = new ArrayList<Element>();

    public WebParse(String connectURL) throws IOException {
        this.doc = Jsoup.connect(connectURL).ignoreHttpErrors(true).get();
        metaTags = doc.getElementsByTag("meta");
    }


    public ArrayList<Element> getMetaElements() {
        return metaElements;
    }

    /**
     * This methods parse the needed metatags and stores them in arraylist to be later used by the mediator {@link Mediator}
     * to extract string from it.
     */
    public void parse() {
        metaElements.clear();

        Element link = doc.select("a").first();
        metaElements.add(link);
        for (Element metaTag : metaTags) {

            String name = metaTag.attr("name");

            if(name.equals("keywords"))
            {
                metaElements.add(metaTag);
            }

            if(name.equals("description"))
            {
                metaElements.add(metaTag);
            }

            if(name.equals("title"))
            {
                metaElements.add(metaTag);
            }

        }
        Elements title = doc.getElementsByTag("title");
        for (Element e : title)
        {
            metaElements.add(e);
        }
        System.out.println(metaElements);

    }

}
