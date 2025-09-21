package ca.sheridancollege.sin12559;

public class FeaturedCar {
    private final String name;
    private final String subtitle;
    private final String spec;
    private final String image;
    private final String blurb;

    public FeaturedCar(String name, String subtitle, String spec, String image, String blurb) {
        this.name = name; this.subtitle = subtitle; this.spec = spec; this.image = image; this.blurb = blurb;
    }
    public String getName()     { return name; }
    public String getSubtitle() { return subtitle; }
    public String getSpec()     { return spec; }
    public String getImage()    { return image; }
    public String getBlurb()    { return blurb; }
}
