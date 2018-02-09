package com.bigfont.obj;


/**
 * Created by d on 9/6/2017.
 */

public class ItemFont  {
    public String title;
    public float size;
    public boolean isSelect;
    public String idItem;

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public ItemFont() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public ItemFont(String title, float size, boolean isSelect) {

        this.title = title;
        this.size = size;
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "ItemFont{" +
                "title='" + title + '\'' +
                ", size='" + size + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}
