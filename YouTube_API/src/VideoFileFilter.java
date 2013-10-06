/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
import java.io.File;
import javax.swing.filechooser.FileFilter;

class VideoFileFilter extends FileFilter {
  private final String description = "Video files";

  private void toLower(String array[]) {
    for (int i = 0, n = array.length; i < n; i++) {
      array[i] = array[i].toLowerCase();
    }
  }

  public String getDescription() {
    return description;
  }

  public boolean accept(File file) {
    if (file.isDirectory()) {
      return true;
    } else {
      String path = file.getAbsolutePath().toLowerCase();
      if ((path.endsWith("avi") && (path.charAt(path.length() - "avi".length() - 1)) == '.')) {
          return true;
      }
      if ((path.endsWith("3gp") && (path.charAt(path.length() - "3gp".length() - 1)) == '.')) {
          return true;
      }
      if ((path.endsWith("mov") && (path.charAt(path.length() - "mov".length() - 1)) == '.')) {
          return true;
      }
      if ((path.endsWith("mp4") && (path.charAt(path.length() - "mp4".length() - 1)) == '.')) {
          return true;
      }
      if ((path.endsWith("mpeg") && (path.charAt(path.length() - "mpeg".length() - 1)) == '.')) {
          return true;
      }
      if ((path.endsWith("flv") && (path.charAt(path.length() - "flv".length() - 1)) == '.')) {
          return true;
      }
      if ((path.endsWith("swf") && (path.charAt(path.length() - "swf".length() - 1)) == '.')) {
          return true;
      }
      if ((path.endsWith("mkv") && (path.charAt(path.length() - "mkv".length() - 1)) == '.')) {
          return true;
      }
    }
    return false;
  }
}