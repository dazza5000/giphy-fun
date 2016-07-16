package ly.generalassemb.giphy;

/**
 * Created by darrankelinske on 7/15/16.
 */
public class Giphy {
    private String id;
    private String gifUrl;
    private String thumbnailUrl;

    public Giphy(String gifUrl, String thumbnailUrl) {
        this.gifUrl = gifUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
