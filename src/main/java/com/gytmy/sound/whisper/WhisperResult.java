package com.gytmy.sound.whisper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Segment {


        @JsonProperty("start")
        private double start;

        @JsonProperty("end")
        private double end;

        @JsonProperty("text")
        private String text;

        @JsonProperty("words")
        private List<Object> words;

        @JsonProperty("avg_log_prob")
        private double avgLogprob;

        @JsonProperty("no_speech_prob")
        private double noSpeechProb;

        public Segment() {
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

        public List<Object> getWords() {
            return words;
        }

        public void setWords(List<Object> words) {
            this.words = words;
        }

        public double getAvgLogprob() {
            return avgLogprob;
        }

        public void setAvgLogprob(double avgLogprob) {
            this.avgLogprob = avgLogprob;
        }

        public double getNoSpeechProb() {
            return noSpeechProb;
        }

        public void setNoSpeechProb(double noSpeechProb) {
            this.noSpeechProb = noSpeechProb;
        }

    }
}
