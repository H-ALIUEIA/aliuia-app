package io.github.h_aliueia.utils;

import io.github.h_aliueia.models.MediaObject;

public class Resources {

    private MediaObject[] media_objects; /*= {//public static final
        new MediaObject("Sending Data to a New Activity with Intent Extras",
            "http://192.168.1.4/file/filename.m3u8",
            "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png",
            "Description for media object #1"),
    };*/

    public void setMedia_objects(MediaObject[] media_objects)
    {
        this.media_objects = media_objects;
    }

    public MediaObject[] getMedia_objects()
    {
        return media_objects;
    }

}
