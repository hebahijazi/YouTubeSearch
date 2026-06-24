package com.example.youtubesearch;

public class VideoItem {
    public Id id;
    public Snippet snippet;

    public static class Id {
        public String videoId;
    }

    public static class Snippet {
        public String title;
        public String description;
        public String channelTitle;
        public String publishedAt;
        public Thumbnails thumbnails;

        public static class Thumbnails {
            public DefaultThumb medium;

            public static class DefaultThumb {
                public String url;
            }
        }
    }
}