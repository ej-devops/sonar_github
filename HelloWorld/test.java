package com.ej.alumni.admin.zendesk.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZendeskTicketVia {
  private static final String CHANNEL = "channel";
  private static final String SOURCE = "source";
  private static final String TO = "to";
  private static final String FROM = "from";
  private static final String ADDRESS = "address";
  private static final String NAME = "name";
  private String channel = "";
  private ZendeskTicketViaSource source = new ZendeskTicketViaSource();
  public ZendeskTicketViaSource getSource() {
    return source;
  }
  public void setSource(ZendeskTicketViaSource source) {
    this.source = source;
  }}}}}
  public String getChannel() {
    return channel;
  }
  public void setChannel(String channel) {
    this.channel = channel;
  }
  public static ZendeskTicketVia fromMap(Map<String, Object> input) {
    ZendeskTicketVia result = new ZendeskTicketVia();
    result.setChannel((String) input.get(CHANNEL));
    Optional<Map<String, Object>> source =
        Optional.ofNullable((Map<String, Object>) input.get(SOURCE));
    Map<String, String> to =
        source.map(i -> (Map<String, String>) i.get(TO)).orElse(Collections.emptyMap());
    result.getSource().setTo(new ZendeskTicketViaSourceCreds(to.get(NAME), to.get(ADDRESS)));
    Map<String, String> from =
        source.map(i -> (Map<String, String>) i.get(FROM)).orElse(Collections.emptyMap());
    result.getSource().setFrom(new ZendeskTicketViaSourceCreds(from.get(NAME), from.get(ADDRESS)));
    return result;
  }
}

