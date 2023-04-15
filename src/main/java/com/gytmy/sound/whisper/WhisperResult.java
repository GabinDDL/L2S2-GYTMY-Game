package com.gytmy.sound.whisper;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WhisperResult {

    @JsonProperty("text")
    private String text;

    @JsonProperty("segments")
    private List<Segment> segments;

    @JsonProperty("language")
    private String language;

    public WhisperResult() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public static class Segment {

        @JsonProperty("id")
        private int id;

        @JsonProperty("seek")
        private int seek;

        @JsonProperty("start")
        private double start;

        @JsonProperty("end")
        private double end;

        @JsonProperty("text")
        private String text;

        @JsonProperty("tokens")
        private List<Integer> tokens;

        @JsonProperty("temperature")
        private double temperature;

        @JsonProperty("avg_logprob")
        private double avgLogprob;

        @JsonProperty("compression_ratio")
        private double compressionRatio;

        @JsonProperty("no_speech_prob")
        private double noSpeechProb;

        public Segment() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSeek() {
            return seek;
        }

        public void setSeek(int seek) {
            this.seek = seek;
        }

        public double getStart() {
            return start;
        }

        public void setStart(double start) {
            this.start = start;
        }

        public double getEnd() {
            return end;
        }

        public void setEnd(double end) {
            this.end = end;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<Integer> getTokens() {
            return tokens;
        }

        public void setTokens(List<Integer> tokens) {
            this.tokens = tokens;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public double getAvgLogprob() {
            return avgLogprob;
        }

        public void setAvgLogprob(double avgLogprob) {
            this.avgLogprob = avgLogprob;
        }

        public double getCompressionRatio() {
            return compressionRatio;
        }

        public void setCompressionRatio(double compressionRatio) {
            this.compressionRatio = compressionRatio;
        }

        public double getNoSpeechProb() {
            return noSpeechProb;
        }

        public void setNoSpeechProb(double noSpeechProb) {
            this.noSpeechProb = noSpeechProb;
        }

    }
}
