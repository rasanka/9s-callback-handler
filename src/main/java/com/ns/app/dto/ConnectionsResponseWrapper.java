package com.ns.app.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import dk.nykredit.jackson.dataformat.hal.HALLink;

public class ConnectionsResponseWrapper {

    @JsonProperty("_embedded")
    private EmbeddedConnections embedded;

    private int count;

    @JsonProperty("_links")
    private LinksConnections links;

    public EmbeddedConnections getEmbedded() {
        return embedded;
    }

    public void setEmbedded(EmbeddedConnections embedded) {
        this.embedded = embedded;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LinksConnections getLinks() {
        return links;
    }

    public void setLinks(LinksConnections links) {
        this.links = links;
    }

    public static class EmbeddedConnections {

        @JsonProperty("connections")
        private Collection<ConnectionSummaryResponse> connections;

        public Collection<ConnectionSummaryResponse> getConnections() {
            return connections;
        }

        public void setConnections(Collection<ConnectionSummaryResponse> connections) {
            this.connections = connections;
        }

    }

    @JsonInclude(Include.NON_NULL)
    public static class LinksConnections {

        private HALLink self;

        public HALLink getSelf() {
            return self;
        }

        public void setSelf(HALLink self) {
            this.self = self;
        }

    }

}
