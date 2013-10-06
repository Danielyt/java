
import java.io.File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class YoutubeVideo {
    private File sourceFile;
    private String title;
    private String category;
    private String keywords;
    private String description;
    private boolean privateVideo;
    
    public YoutubeVideo() {
        this (null, null, null, null, null, false);
    }
    
    public YoutubeVideo(File sourceFile){
        this (sourceFile, null, null, null, null, false);
    }
    
    public YoutubeVideo (YoutubeVideo yt) {
        this(yt.getSourceFile(), yt.getTitle(), yt.getCategory(), yt.getKeywords(), yt.getDescription(), yt.isPrivateVideo() );
        
    }
    
    public YoutubeVideo (File sourceFile, String title, String category, String keywords, String description, boolean privateVideo){
        if (sourceFile != null) {
            this.sourceFile = new File (sourceFile.getAbsolutePath());
        }
        else {
            this.sourceFile = new File ("");
        }

        setTitle(title);
        setCategory(category);
        setKeywords(keywords);
        setDescription(description);
        setPrivateVideo(privateVideo);
    }
    
    public File getSourceFile(){
        return new File(sourceFile.getAbsolutePath());
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPrivateVideo() {
        return privateVideo;
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }
        else if (sourceFile != null) {
            this.title = sourceFile.getName();
            int pos = this.title.lastIndexOf(".");
            if (pos > 0) {
                this.title = this.title.substring(0, pos);
            }
        }
        else {
            this.title = "";
        }
    }

    public void setCategory(String category) {
        if (category != null) {
            this.category = category;
        }
        else {
            this.category = "People";
        }
    }

    public void setKeywords(String keywords) {
        if (keywords != null) {
            this.keywords = keywords;            
        }
        else {
            this.keywords = "";
        }
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
        else {
            this.description = "";
        }
    }

    public void setPrivateVideo(boolean privateVideo) {
        this.privateVideo = privateVideo;
    }
 
}
