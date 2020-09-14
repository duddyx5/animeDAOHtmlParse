package nz.co.duddyx5.animedao.models;

import java.io.Serializable;

public class Anime implements Serializable {
    static final long serialVersionUID = 727566175075960653L;
    public String animeTitle;
    public String animeURL;
    public String animeEpiURL;
    public String animeEpiTitle;
    public String animeImage;

    public Anime() {
    }


    @Override
    public String toString() {
        return "Anime{" +
                ", title='" + animeTitle + '\'' +
                ", showUrl='" + animeURL + '\'' +
                ", cardImageUrl='" + animeImage + '\'' +
                '}';
    }
}
