package fr.alma.middleware1314.services;

import java.net.URL;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class TestRSS {

    public static void main(String[] args) throws Exception {
       // URL source = new URL("http://linuxfr.org/news.atom");
        URL source = new URL("https://sites.google.com/site/usagymancenis/infos-pratiques/messages/posts.xml");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(source));
        System.out.println(feed.getTitle());
    }

}
