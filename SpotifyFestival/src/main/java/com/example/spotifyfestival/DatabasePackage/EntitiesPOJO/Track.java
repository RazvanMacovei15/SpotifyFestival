package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

import java.util.List;

public class Track extends Entity{

    protected String name;
    protected String spotifyID;
    protected String spotifyLink;
    protected String imageURL;
    protected List<Artist> artists;

    public Track(int id, String name, String spotifyID, String spotifyLink, String imageURL, List<Artist> artists) {
        super(id);
        this.name = name;
        this.spotifyID = spotifyID;
        this.spotifyLink = spotifyLink;
        this.imageURL = imageURL;
        this.artists = artists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpotifyID() {
        return spotifyID;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }

    public String getSpotifyLink() {
        return spotifyLink;
    }

    public void setSpotifyLink(String spotifyLink) {
        this.spotifyLink = spotifyLink;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(", performed by: ");
        if (artists.size() == 1){
            sb.append(artists.get(0).getName());
        }else{
            for(int i = 0 ; i < artists.size() - 1; i++){
                sb.append(artists.get(i).getName());
                sb.append(", ");
            }
            sb.append(artists.get(artists.size()-1).getName());
        }
        return sb.toString();
    }



    public String getConstantJSON() {
        return constantJSON;
    }

    public String constantJSON = "{\n" +
            "  \"items\": [\n" +
            "    {\n" +
            "      \"album\": {\n" +
            "        \"album_type\": \"SINGLE\",\n" +
            "        \"artists\": [\n" +
            "          {\n" +
            "            \"external_urls\": {\n" +
            "              \"spotify\": \"https://open.spotify.com/artist/46pWGuE3dSwY3bMMXGBvVS\"\n" +
            "            },\n" +
            "            \"href\": \"https://api.spotify.com/v1/artists/46pWGuE3dSwY3bMMXGBvVS\",\n" +
            "            \"id\": \"46pWGuE3dSwY3bMMXGBvVS\",\n" +
            "            \"name\": \"Rema\",\n" +
            "            \"type\": \"artist\",\n" +
            "            \"uri\": \"spotify:artist:46pWGuE3dSwY3bMMXGBvVS\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"external_urls\": {\n" +
            "              \"spotify\": \"https://open.spotify.com/artist/0C8ZW7ezQVs4URX5aX7Kqx\"\n" +
            "            },\n" +
            "            \"href\": \"https://api.spotify.com/v1/artists/0C8ZW7ezQVs4URX5aX7Kqx\",\n" +
            "            \"id\": \"0C8ZW7ezQVs4URX5aX7Kqx\",\n" +
            "            \"name\": \"Selena Gomez\",\n" +
            "            \"type\": \"artist\",\n" +
            "            \"uri\": \"spotify:artist:0C8ZW7ezQVs4URX5aX7Kqx\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"available_markets\": [\n" +
            "          \"AD\",\n" +
            "          \"AE\",\n" +
            "          \"AR\",\n" +
            "          \"AT\",\n" +
            "          \"AU\",\n" +
            "          \"BE\",\n" +
            "          \"BG\",\n" +
            "          \"BH\",\n" +
            "          \"BO\",\n" +
            "          \"BR\",\n" +
            "          \"CA\",\n" +
            "          \"CH\",\n" +
            "          \"CL\",\n" +
            "          \"CO\",\n" +
            "          \"CR\",\n" +
            "          \"CY\",\n" +
            "          \"CZ\",\n" +
            "          \"DE\",\n" +
            "          \"DK\",\n" +
            "          \"DO\",\n" +
            "          \"DZ\",\n" +
            "          \"EC\",\n" +
            "          \"EE\",\n" +
            "          \"EG\",\n" +
            "          \"ES\",\n" +
            "          \"FI\",\n" +
            "          \"FR\",\n" +
            "          \"GB\",\n" +
            "          \"GR\",\n" +
            "          \"GT\",\n" +
            "          \"HK\",\n" +
            "          \"HN\",\n" +
            "          \"HU\",\n" +
            "          \"ID\",\n" +
            "          \"IE\",\n" +
            "          \"IL\",\n" +
            "          \"IN\",\n" +
            "          \"IS\",\n" +
            "          \"IT\",\n" +
            "          \"JO\",\n" +
            "          \"JP\",\n" +
            "          \"KW\",\n" +
            "          \"LB\",\n" +
            "          \"LI\",\n" +
            "          \"LT\",\n" +
            "          \"LU\",\n" +
            "          \"LV\",\n" +
            "          \"MA\",\n" +
            "          \"MC\",\n" +
            "          \"MT\",\n" +
            "          \"MX\",\n" +
            "          \"MY\",\n" +
            "          \"NI\",\n" +
            "          \"NL\",\n" +
            "          \"NO\",\n" +
            "          \"NZ\",\n" +
            "          \"OM\",\n" +
            "          \"PA\",\n" +
            "          \"PE\",\n" +
            "          \"PH\",\n" +
            "          \"PL\",\n" +
            "          \"PS\",\n" +
            "          \"PT\",\n" +
            "          \"PY\",\n" +
            "          \"QA\",\n" +
            "          \"RO\",\n" +
            "          \"SA\",\n" +
            "          \"SE\",\n" +
            "          \"SG\",\n" +
            "          \"SK\",\n" +
            "          \"SV\",\n" +
            "          \"TH\",\n" +
            "          \"TN\",\n" +
            "          \"TR\",\n" +
            "          \"TW\",\n" +
            "          \"US\",\n" +
            "          \"UY\",\n" +
            "          \"VN\",\n" +
            "          \"ZA\"\n" +
            "        ],\n" +
            "        \"external_urls\": {\n" +
            "          \"spotify\": \"https://open.spotify.com/album/2b2GHWESCWEuHiCZ2Skedp\"\n" +
            "        },\n" +
            "        \"href\": \"https://api.spotify.com/v1/albums/2b2GHWESCWEuHiCZ2Skedp\",\n" +
            "        \"id\": \"2b2GHWESCWEuHiCZ2Skedp\",\n" +
            "        \"images\": [\n" +
            "          {\n" +
            "            \"height\": 640,\n" +
            "            \"url\": \"https://i.scdn.co/image/ab67616d0000b273a3a7f38ea2033aa501afd4cf\",\n" +
            "            \"width\": 640\n" +
            "          },\n" +
            "          {\n" +
            "            \"height\": 300,\n" +
            "            \"url\": \"https://i.scdn.co/image/ab67616d00001e02a3a7f38ea2033aa501afd4cf\",\n" +
            "            \"width\": 300\n" +
            "          },\n" +
            "          {\n" +
            "            \"height\": 64,\n" +
            "            \"url\": \"https://i.scdn.co/image/ab67616d00004851a3a7f38ea2033aa501afd4cf\",\n" +
            "            \"width\": 64\n" +
            "          }\n" +
            "        ],\n" +
            "        \"name\": \"Calm Down (with Selena Gomez)\",\n" +
            "        \"release_date\": \"2022-08-25\",\n" +
            "        \"release_date_precision\": \"day\",\n" +
            "        \"total_tracks\": 1,\n" +
            "        \"type\": \"album\",\n" +
            "        \"uri\": \"spotify:album:2b2GHWESCWEuHiCZ2Skedp\"\n" +
            "      },\n" +
            "      \"artists\": [\n" +
            "        {\n" +
            "          \"external_urls\": {\n" +
            "            \"spotify\": \"https://open.spotify.com/artist/46pWGuE3dSwY3bMMXGBvVS\"\n" +
            "          },\n" +
            "          \"href\": \"https://api.spotify.com/v1/artists/46pWGuE3dSwY3bMMXGBvVS\",\n" +
            "          \"id\": \"46pWGuE3dSwY3bMMXGBvVS\",\n" +
            "          \"name\": \"Rema\",\n" +
            "          \"type\": \"artist\",\n" +
            "          \"uri\": \"spotify:artist:46pWGuE3dSwY3bMMXGBvVS\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"external_urls\": {\n" +
            "            \"spotify\": \"https://open.spotify.com/artist/0C8ZW7ezQVs4URX5aX7Kqx\"\n" +
            "          },\n" +
            "          \"href\": \"https://api.spotify.com/v1/artists/0C8ZW7ezQVs4URX5aX7Kqx\",\n" +
            "          \"id\": \"0C8ZW7ezQVs4URX5aX7Kqx\",\n" +
            "          \"name\": \"Selena Gomez\",\n" +
            "          \"type\": \"artist\",\n" +
            "          \"uri\": \"spotify:artist:0C8ZW7ezQVs4URX5aX7Kqx\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"available_markets\": [\n" +
            "        \"AD\",\n" +
            "        \"AE\",\n" +
            "        \"AR\",\n" +
            "        \"AT\",\n" +
            "        \"AU\",\n" +
            "        \"BE\",\n" +
            "        \"BG\",\n" +
            "        \"BH\",\n" +
            "        \"BO\",\n" +
            "        \"BR\",\n" +
            "        \"CA\",\n" +
            "        \"CH\",\n" +
            "        \"CL\",\n" +
            "        \"CO\",\n" +
            "        \"CR\",\n" +
            "        \"CY\",\n" +
            "        \"CZ\",\n" +
            "        \"DE\",\n" +
            "        \"DK\",\n" +
            "        \"DO\",\n" +
            "        \"DZ\",\n" +
            "        \"EC\",\n" +
            "        \"EE\",\n" +
            "        \"EG\",\n" +
            "        \"ES\",\n" +
            "        \"FI\",\n" +
            "        \"FR\",\n" +
            "        \"GB\",\n" +
            "        \"GR\",\n" +
            "        \"GT\",\n" +
            "        \"HK\",\n" +
            "        \"HN\",\n" +
            "        \"HU\",\n" +
            "        \"ID\",\n" +
            "        \"IE\",\n" +
            "        \"IL\",\n" +
            "        \"IN\",\n" +
            "        \"IS\",\n" +
            "        \"IT\",\n" +
            "        \"JO\",\n" +
            "        \"JP\",\n" +
            "        \"KW\",\n" +
            "        \"LB\",\n" +
            "        \"LI\",\n" +
            "        \"LT\",\n" +
            "        \"LU\",\n" +
            "        \"LV\",\n" +
            "        \"MA\",\n" +
            "        \"MC\",\n" +
            "        \"MT\",\n" +
            "        \"MX\",\n" +
            "        \"MY\",\n" +
            "        \"NI\",\n" +
            "        \"NL\",\n" +
            "        \"NO\",\n" +
            "        \"NZ\",\n" +
            "        \"OM\",\n" +
            "        \"PA\",\n" +
            "        \"PE\",\n" +
            "        \"PH\",\n" +
            "        \"PL\",\n" +
            "        \"PS\",\n" +
            "        \"PT\",\n" +
            "        \"PY\",\n" +
            "        \"QA\",\n" +
            "        \"RO\",\n" +
            "        \"SA\",\n" +
            "        \"SE\",\n" +
            "        \"SG\",\n" +
            "        \"SK\",\n" +
            "        \"SV\",\n" +
            "        \"TH\",\n" +
            "        \"TN\",\n" +
            "        \"TR\",\n" +
            "        \"TW\",\n" +
            "        \"US\",\n" +
            "        \"UY\",\n" +
            "        \"VN\",\n" +
            "        \"ZA\"\n" +
            "      ],\n" +
            "      \"disc_number\": 1,\n" +
            "      \"duration_ms\": 239317,\n" +
            "      \"explicit\": false,\n" +
            "      \"external_ids\": {\n" +
            "        \"isrc\": \"NGA3B2214021\"\n" +
            "      },\n" +
            "      \"external_urls\": {\n" +
            "        \"spotify\": \"https://open.spotify.com/track/0WtM2NBVQNNJLh6scP13H8\"\n" +
            "      },\n" +
            "      \"href\": \"https://api.spotify.com/v1/tracks/0WtM2NBVQNNJLh6scP13H8\",\n" +
            "      \"id\": \"0WtM2NBVQNNJLh6scP13H8\",\n" +
            "      \"is_local\": false,\n" +
            "      \"name\": \"Calm Down (with Selena Gomez)\",\n" +
            "      \"popularity\": 90,\n" +
            "      \"preview_url\": \"https://p.scdn.co/mp3-preview/b61b32e2928081fc98d438fd8c72919ab4fb12a4?cid=74049b5e4e7c4b8597aa2e4909071382\",\n" +
            "      \"track_number\": 1,\n" +
            "      \"type\": \"track\",\n" +
            "      \"uri\": \"spotify:track:0WtM2NBVQNNJLh6scP13H8\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"total\": 50,\n" +
            "  \"limit\": 1,\n" +
            "  \"offset\": 0,\n" +
            "  \"href\": \"https://api.spotify.com/v1/me/top/tracks?limit=1&offset=0&time_range=long_term\",\n" +
            "  \"next\": \"https://api.spotify.com/v1/me/top/tracks?limit=1&offset=1&time_range=long_term\",\n" +
            "  \"previous\": null\n" +
            "}";
}
