package com.ninespokes.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import dk.nykredit.jackson.dataformat.hal.HALLink;

@JsonInclude(Include.NON_NULL)
public class ConnectionSummaryResponse {

    private String id;
    private String appKey;
    private String status;
    private String createdAt;
    private String lastConnect;
    private String createdBy;

    @JsonProperty("_links")
    private LinksConnectionSummary links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LinksConnectionSummary getLinks() {
        return links;
    }

    public void setLinks(LinksConnectionSummary links) {
        this.links = links;
    }

    public String getLastConnect() {
        return lastConnect;
    }

    public void setLastConnect(String lastConnect) {
        this.lastConnect = lastConnect;
    }

    @JsonInclude(Include.NON_NULL)
    public static class LinksConnectionSummary {

        private HALLink details;

        public HALLink getDetails() {
            return details;
        }

        public void setDetails(HALLink details) {
            this.details = details;
        }

    }

}
