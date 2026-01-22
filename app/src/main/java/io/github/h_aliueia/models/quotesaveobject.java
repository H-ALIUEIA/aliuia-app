package io.github.h_aliueia.models;

import java.util.ArrayList;
import java.util.List;

public class quotesaveobject
{
    private List<String> id = new ArrayList<String>();

    public quotesaveobject(String id)
    {

        this.id.add(id);
    }

    public quotesaveobject()
    {

    }

    public void addid(String id)
    {
        this.id.add(id);
    }

    public void removeid(String id)
    {
        int checker = this.id.indexOf(id);
        if(checker != -1)
        {
            this.id.remove(checker);
        }
    }

    public List<String> getId() {
        return id;
    }

    public void setId(String id) {
        this.id.add(id);
    }
}
