package com.example.spotifyfestival.NewFeatures;

public class Enums {
    public enum TimeRange {
        SHORT_TERM("short_term"),
        MEDIUM_TERM("medium_term"),
        LONG_TERM("long_term");

        private final String timeRange;

        TimeRange(String timeRange) {
            this.timeRange = timeRange;
        }

        public String getTimeRange() {
            return timeRange;
        }
    }
}
