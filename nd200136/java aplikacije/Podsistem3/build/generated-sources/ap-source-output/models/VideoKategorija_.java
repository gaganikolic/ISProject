package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Kategorija;
import models.Video;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-07-03T23:11:53")
@StaticMetamodel(VideoKategorija.class)
public class VideoKategorija_ { 

    public static volatile SingularAttribute<VideoKategorija, Video> idVideo;
    public static volatile SingularAttribute<VideoKategorija, Integer> idVideoKategorija;
    public static volatile SingularAttribute<VideoKategorija, Kategorija> idKategorija;

}