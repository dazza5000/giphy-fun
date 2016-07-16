package ly.generalassemb.giphy;

/**
 * Created by darrankelinske on 7/15/16.
 */
public class Giphy {
    private String id;
    private String gifUrl;

    public Giphy(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getGifUrl() {
        return gifUrl;
    }
}
