package com.spotify.ui;

public class ContentPanel {
    private Content content;
    private ContentDraggable titleBarContent;
    private int button;

    public ContentPanel(Content content, ContentDraggable titleBarContent, int button) {
        this.content = content;
        this.titleBarContent = titleBarContent;
        this.button = button;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public ContentDraggable getTitleBarContent() {
        return titleBarContent;
    }

    public void setTitleBarContent(ContentDraggable titleBarContent) {
        this.titleBarContent = titleBarContent;
    }
}
