package com.jiajia.mypractisedemos.module.trainrecyclerview;

/**
 * <pre>
 *  Created by fanjiajia on 2019/3/21.
 *  desc:
 */

public class ItemBean {

    private String content;
    private boolean selected;
    private boolean middle;
    private boolean handled;

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isMiddle() {
        return middle;
    }

    public void setMiddle(boolean middle) {
        this.middle = middle;
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public ItemBean(String content, boolean selected, boolean middle, boolean handled) {
        this.content = content;
        this.selected = selected;
        this.middle = middle;
        this.handled = handled;
    }

    public ItemBean(String content) {
        this(content,false, false, false);
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "content='" + content + '\'' +
                ", selected=" + selected +
                ", middle=" + middle +
                ", handled=" + handled +
                '}';
    }
}
