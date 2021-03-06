package com.mohitpaudel.cnnnewsfeed;


public class FeedEntry {
    private String title, description, link, pubDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return
                "title='" + title + '\'' +
                        ", description='" + description + '\'' +
                        ", link='" + link + '\'' +
                        ", pubDate='" + pubDate + '\'';
    }
}

