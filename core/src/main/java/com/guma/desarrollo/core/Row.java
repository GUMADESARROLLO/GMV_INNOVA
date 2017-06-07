package com.guma.desarrollo.core;

/**
 * Created by luis.perez on 03/04/2017.
 */

public class Row {

    private String title;
    private String subtitle;
    private String subsubtitle;
    private boolean checked;

    public String getSubsubtitle() {
        return subsubtitle;
    }

    public void setSubsubtitle(String subsubtitle) {
        this.subsubtitle = subsubtitle;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
