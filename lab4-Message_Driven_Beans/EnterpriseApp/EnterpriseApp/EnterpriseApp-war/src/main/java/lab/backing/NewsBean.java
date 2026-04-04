package lab.backing;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.JMSContext;
import lab.ejb.NewsItem;
import lab.ejb.NewsItemFacadeLocal;

import java.util.List;

@RequestScoped
@Named
public class NewsBean {
    @Inject
    private NewsItemFacadeLocal facade;

    private String headingText;
    private String bodyText;

    @Inject
    private JMSContext context;
    @Resource(lookup="java:app/jms/NewsQueue")
    private jakarta.jms.Queue queue;


    void sendNewsItem(String heading, String body) {
        String payload = heading + "|" + body;
        context.createProducer().send(queue, payload);
    }

    public List<
            NewsItem> getNewsItems() {
        return facade.getAllNewsItems();
    }

    public String submitNews() {
        sendNewsItem(getHeadingText(), getBodyText());
        return null;
    }

    public String getHeadingText() {
        return headingText;
    }

    public void setHeadingText(String headingText) {
        this.headingText = headingText;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }
}
