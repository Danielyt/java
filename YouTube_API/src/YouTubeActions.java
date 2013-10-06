/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
import com.google.gdata.client.*;
import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.client.youtube.*;
import com.google.gdata.data.*;
import com.google.gdata.data.geo.impl.*;
import com.google.gdata.data.media.*;
import com.google.gdata.data.media.mediarss.*;
import com.google.gdata.data.youtube.*;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;
import java.io.IOException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class YouTubeActions {
    public static UserProfileEntry currentUser;
    private static final YouTubeService service = new YouTubeService("PISS Project", "AI39si7CmakvUmzmOXUw_ChWxUE2LpfwSA3iU_vbI227GtM8eWTl3ODFiKnxQMJt4W8OXQPLeL0Mk4UDe1PcGsyfuOuWFC20dw");
    public static void LogIn(String username, char[] password) throws AuthenticationException, MalformedURLException, IOException, ServiceException{       
        service.setUserCredentials(username, new String (password));
        currentUser = service.getEntry(new URL( "http://gdata.youtube.com/feeds/api/users/default" ), UserProfileEntry.class);

    }
    
    public static void getUserProfileInfo(String user) {
        
    }
    
    public static void uploadVideos(YoutubeVideo[] yVideos){
        for (int i = 0; i < yVideos.length; i++){
            uploadVideo(yVideos[i]);
            System.out.println("Video " + i + ": " + yVideos[i].getTitle() + " has finished uploading.");
        }            
    }
    
    public static void uploadVideo(YoutubeVideo yVideo) {
        VideoEntry newEntry = new VideoEntry();
        
        YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
        mg.setTitle(new MediaTitle());
        mg.getTitle().setPlainTextContent(yVideo.getTitle());
        mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, yVideo.getCategory()));
        mg.setKeywords(new MediaKeywords());
   
        String[] kWords = yVideo.getKeywords().split("\\W");
        for (int i = 0; i < kWords.length; i++)
            if( !kWords[i].equals("") )
                mg.getKeywords().addKeyword(kWords[i]);
        
        mg.setDescription(new MediaDescription());
        mg.getDescription().setPlainTextContent(yVideo.getDescription());
        mg.setPrivate(yVideo.isPrivateVideo());

        //mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "mydevtag"));
        //mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "anotherdevtag"));

        //newEntry.setGeoCoordinates(new GeoRssWhere(37.0,-122.0));
        // alternatively, one could specify just a descriptive string
        // newEntry.setLocation("Mountain View, CA");
        
        MediaFileSource ms = new MediaFileSource( yVideo.getSourceFile(), "video/quicktime");
        newEntry.setMediaSource(ms);
        String uploadUrl =  "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";
        
        try{
            VideoEntry createdEntry = service.insert(new URL(uploadUrl), newEntry);
        }
        catch (MalformedURLException exception) {
            System.err.println("Malformed URL exception\n" + exception.getMessage());
        }
        catch (IOException exception) {
            System.err.append("IO Exception\n" + exception.getMessage());
        }
        catch (ServiceException exception) {
            System.err.append("Service Exception\n" + exception.getMessage());
        }
    }
    
    public static Object[] getUploadedVideos(){
        String feedUrl = "http://gdata.youtube.com/feeds/api/users/default/uploads";
        try {            
            VideoFeed videoFeed = service.getFeed(new URL(feedUrl), VideoFeed.class);
            return getVideoFeed(videoFeed);
        } catch (IOException ex) {
            Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Object[] getVideoFeed(Object obj){
        if (obj instanceof VideoFeed) {
            VideoFeed videoFeed = (VideoFeed)obj;
            Object[] result = new Object[videoFeed.getEntries().size()];
            int i = 0;
            for(VideoEntry videoEntry : videoFeed.getEntries() ) {
                result[i++] = videoEntry;
            }
            return result;
        }
        return null;
    }
    
    public static Object[] getVideoEntryInfo(Object obj){
        if (obj instanceof VideoEntry) {
            VideoEntry videoEntry = (VideoEntry) obj;
            YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();
            
            StringBuilder keywordsString = new StringBuilder();
            MediaKeywords keywords = mediaGroup.getKeywords();
            if (keywords != null){
                for(String keyword : keywords.getKeywords()) {
                    keywordsString.append(keyword + ", ");
                }
            }
            
            GeoRssWhere location = videoEntry.getGeoCoordinates();
            Double latitude = 0.0;
            Double longitude = 0.0;
            
            if(location != null) {            
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            
            Rating rating = videoEntry.getRating();
            
            Float averageRating = 0.0f;
            
            if(rating != null) {
                averageRating = rating.getAverage();
            }
            
            YtStatistics stats = videoEntry.getStatistics();
            long viewCount = 0;
            if(stats != null ) {
                viewCount = stats.getViewCount();
            }
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator(' ');
            DecimalFormat df = new DecimalFormat("###,###", symbols);
            
            int duration;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            for (YouTubeMediaContent mediaContent : mediaGroup.getYouTubeContents()) {
                duration = mediaContent.getDuration();
                hours = duration / 3600;
                duration = duration % 3600;
                minutes = duration / 60;
                seconds = duration % 60;
            }
            
            String title = "";
            if (videoEntry.getTitle() != null)
                title = videoEntry.getTitle().getPlainText();
            String uploader = "";
            if (mediaGroup.getUploader() != null)
                uploader = mediaGroup.getUploader();
            String id = "";
            if (mediaGroup.getVideoId() != null)
                id = mediaGroup.getVideoId();
            String description = "";
            if (mediaGroup.getDescription() != null)
                description = mediaGroup.getDescription().getPlainTextContent();
            return new Object[]{
                title,
                uploader,
                id,
                description,
                keywordsString.toString(),
                latitude,
                longitude,
                averageRating,
                viewCount,
                String.format("%02d:%02d:%02d", hours, minutes, seconds)
            };
        }
        return null;
    }
    
    public static void deleteVideoEntry (Object[] videos, String videoID) {
        if (videos != null) {            
            VideoEntry videoEntry;
            for (int i = 0; i < videos.length; i++){
                if (videos[i] instanceof VideoEntry){
                    videoEntry = (VideoEntry) videos[i];
                    if ( videoEntry.getMediaGroup().getVideoId().equals(videoID))
                        try {
                        videoEntry.delete();
                    } catch (IOException ex) {
                        Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServiceException ex) {
                        Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
                    }                    
                }
            }
        }
    }
    
    public static Object searchVideos(String term){
        try {
            YouTubeQuery query = new YouTubeQuery(new URL("http://gdata.youtube.com/feeds/api/videos"));
            // order results by the number of views (most viewed first)
            query.setOrderBy(YouTubeQuery.OrderBy.RELEVANCE);
            
            // search for puppies and include restricted content in the search results
            query.setFullTextQuery(term);
            query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);
            VideoFeed videoFeed = service.query(query, VideoFeed.class);
            return videoFeed;
        } catch (MalformedURLException ex) {
            Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Object getPreviousFeed (Object obj){
        if (obj instanceof VideoFeed){
            VideoFeed videoFeed = (VideoFeed)obj;
            if(videoFeed.getPreviousLink() != null) {
                try {
                    return service.getFeed(new URL(videoFeed.getPreviousLink().getHref()), VideoFeed.class);
                }catch (MalformedURLException ex) {
                    Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServiceException ex) {
                    Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);                    
                }
            }
            else
                return videoFeed;
        }
        return null;
    }
    
    public static Object getNextFeed (Object obj){
        if (obj instanceof VideoFeed){
            VideoFeed videoFeed = (VideoFeed)obj;
            if(videoFeed.getNextLink() != null) {
                try {
                    return service.getFeed(new URL(videoFeed.getNextLink().getHref()), VideoFeed.class);
                }catch (MalformedURLException ex) {
                    Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServiceException ex) {
                    Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);                    
                }
            }
            else
                return videoFeed;
        }
        return null;
    }
    
    public static Object[] getUserPlaylists(){
        String feedUrl = "http://gdata.youtube.com/feeds/api/users/default/playlists";
        
        PlaylistLinkFeed feed;
        
        try {
            feed = service.getFeed(new URL(feedUrl), PlaylistLinkFeed.class);
            Object[] result = new Object[feed.getEntries().size()];
            int i = 0;
            for(PlaylistLinkEntry entry : feed.getEntries()) {
                result[i++] = entry;
            }
            return result;
        }catch (MalformedURLException ex) {
            Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }
    
    public static Object[] getPlaylistInfo(Object obj){
        if (obj instanceof PlaylistLinkEntry) {
            Object[] result = new Object[2];
            PlaylistLinkEntry entry = (PlaylistLinkEntry)obj;
            result[0] = entry.getTitle().getPlainText();
            result[1] = entry.getCountHint();
            return result;
        }
        return null;
    }
    
    public static Object[] getVideosFromPlaylist(Object obj) {
        if (obj instanceof PlaylistLinkEntry) {
            try {
                String playlistUrl = ((PlaylistLinkEntry)obj).getFeedUrl();
                PlaylistFeed playlistFeed = service.getFeed(new URL(playlistUrl), PlaylistFeed.class);
                Object[] result = new Object[playlistFeed.getEntries().size()];
                int i = 0;
                for(PlaylistEntry playlistEntry : playlistFeed.getEntries()) {
                    result[i++] = playlistEntry;                   
                }
                return result;
            }catch (MalformedURLException ex) {
                Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServiceException ex) {
                Logger.getLogger(YouTubeActions.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        return null;
    }
}
