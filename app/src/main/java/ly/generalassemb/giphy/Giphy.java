package ly.generalassemb.giphy;

/**
 * Created by darrankelinske on 7/15/16.
 */
public class Giphy {
    private String giphyUrl;
    private String gifUrl;
    private String thumbnailUrl;

    public Giphy(String giphyUrl, String gifUrl, String thumbnailUrl) {
        this.giphyUrl = giphyUrl;
        this.gifUrl = gifUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getGiphyUrl() {
        return giphyUrl;
    }
}
