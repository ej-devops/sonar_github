apackage com.ej.alumni.security;
import com.ej.alumni.common.S3Helper;
import com.ej.alumni.common.S3HelperFactory;
import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
public class ImgSrcParserTest {
  @Before
  public void before() throws IllegalStateException, NamingException, IOException {
    SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
    Map<String, Map<String, String>> destinations = buildDestinations();
    ConnectivityConfiguration conf = new EJConnectivityConfiguration(destinations);
    builder.bind("java:comp/env/connectivityConfiguration", conf);
    builder.activate();
  }
  private static class EJConnectivityConfiguration implements ConnectivityConfiguration {
    private Map<String, Map<String, String>> connectivities;
    public EJConnectivityConfiguration(Map<String, Map<String, String>> connectivities) {
      this.connectivities = connectivities;
    }
    @Override
    public DestinationConfiguration getConfiguration(String name) {
      return new EJDestinationConfiguration(connectivities.get(name));
    }
    @Override
    public DestinationConfiguration getConfiguration(String account, String name) {
      return getConfiguration(name);
    }
    @Override
    public Map<String, DestinationConfiguration> getConfigurations(String account) {
      return null;
    }
    @Override
    public void clearCache() {}
  }
  private static class EJDestinationConfiguration implements DestinationConfiguration {
    private Map<String, String> destination;
    public EJDestinationConfiguration(Map<String, String> destination) {
      this.destination = destination;
    }
    @Override
    public String getProperty(String s) {
      return destination.get(s);
    }
    @Override
    public Map<String, String> getAllProperties() {
      return destination;
    }
    @Override
    public KeyStore getKeyStore() {
      return null;
    }
    @Override
    public KeyStore getTrustStore() {
      return null;
    }
  }
  private Map<String, Map<String, String>> buildDestinations() throws IOException {
    File destinations = new File("WebContent-customers/sap/WEB-INF/Destinations");
    Map<String, Map<String, String>> dests = new HashMap<>();
    for (File destination : destinations.listFiles()) {
      HashMap<String, String> dest = new HashMap<>();
      BufferedReader br = new BufferedReader(new FileReader(destination));
      String confLine;
      while ((confLine = br.readLine()) != null) {
        confLine = confLine.trim();
        if (!confLine.startsWith("#") && !confLine.isEmpty()) {
          String[] c = confLine.split("=");
          dest.put(c[0], c.length > 1 ? c[1] : "");
        }
      }
      dests.put(dest.get("Name"), dest);
    }
    return dests;
  }
  @Test
  @Ignore("to test locally only")
  public void testParseHtmlReplaceWithSafeImgSrc() {
    S3Helper s3ImageHelper = new S3HelperFactory().imageSourcesS3Helper();
    S3Helper customerImageHelper = new S3HelperFactory().customerImagesS3Helper();
    EJSanitizer sanitizer = new EJSanitizer();
    ImgSrcParserAttributePolicy ispap = new ImgSrcParserAttributePolicy();
    ispap.setImageSourcesS3Helper(s3ImageHelper);
    sanitizer.setImgSrcAuthInjectionProtected(ispap);
    ArrayList<String> domains = new ArrayList<>();
    domains.add("www.youtube.com");
    domains.add("player.vimeo.com");
    sanitizer.setVideoValidDomain(domains);
    String content =
        "<iframe width=\"640\" height=\"360\" src=\"https:////player.vimeo.com/video/156299091\" frameborder=\"0\" allowfullscreen>doc inside</iframe>"
            + "<iframe width=\"640\" height=\"360\" src=\"https:////ppp.com/video/156299091\" frameborder=\"0\" allowfullscreen>doc inside</iframe><img src=\"http://static.boredpanda.com/blog/wp-content/uploads/2016/08/cute-kittens-30-57b30ad41bc90__605.jpg\"/>";
    //ImgSrcParserAttributePolicy policy = new ImgSrcParserAttributePolicy();
    //policy.setProxyImageHost("s3-eu-west-1.amazonaws.com");
    //String result = policy.apply("", "", "http://www.pngall.com/wp-content/uploads/2016/04/Rose-PNG-Picture.png");
    //System.out.println(result);
    List<String> logs = new ArrayList<>();
    //String r = sanitizer.noSanitizationSanitizer().sanitize(content);
    System.out.println("---");
    //        System.out.println(r);
    Document doc = Jsoup.parseBodyFragment(content);
    doc.outputSettings().prettyPrint(false);
    ImgSrcSignerAttributePolicy imgSrcSignerAttributePolicy = new ImgSrcSignerAttributePolicy();
    ImgSrcParserAttributePolicy imgSrcParserAttributePolicy = new ImgSrcParserAttributePolicy();
    imgSrcParserAttributePolicy.setImageSourcesS3Helper(s3ImageHelper);
    imgSrcParserAttributePolicy.setCustomerImagesS3Helper(customerImageHelper);
    for (Element element : doc.select("img")) {
      String orig = element.attr("src");
      String out = imgSrcParserAttributePolicy.apply("img", "src", orig);
      if (out != null) {
        element.attr("src", out);
      }
    }
    System.out.println(doc.body().html());
    //Assert.assertEquals("<div><img src='/mylocalimage.jpg'/>some context<img src='https://s3-eu-west-1.amazonaws.com/ej-test-imagefiles/cf8d354e45a983162d3f3e74e88bac27da4ae9e45ea435a05fae82508e89564c.png'/>here and there</div>", result);
  }
}

