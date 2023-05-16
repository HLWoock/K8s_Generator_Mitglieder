package de.woock.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Antwort (@JsonProperty("bonitaet") Bonitaet bonitaet,
                       @JsonProperty("name")     String name) {}