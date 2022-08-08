package com.spotify.app;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import javax.swing.ImageIcon;

public interface Utility {
    /**
     *  Get date format "Day, Month DD, YYYY" by providing
     *  dateInput.
     *
     *  @param dateInput MM/DD/YYYY
     *  @return
     */
    default String getDateFormat(String dateInput) {
        String[] dayOfWeek = {
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        }, monthOfYear = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        }, splitStr = dateInput.split("/");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(splitStr[2]), Integer.parseInt(splitStr[0]) - 1, Integer.parseInt(splitStr[1]));

        return(
            dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " +
            monthOfYear[calendar.get(Calendar.MONTH)] + " " +
            calendar.get(Calendar.DATE) + ", " + calendar.get(Calendar.YEAR)
        );
    }

    /**
     *  Get simple date format "Month DD, YYYY" by providing
     *  dateInput.
     *
     *  @param dateInput MM/DD/YYYY
     *  @return
     */
    default String getSimpleDateFormat(String dateInput) {
        String[] monthOfYear = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        }, splitStr = dateInput.split("/");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(splitStr[2]), Integer.parseInt(splitStr[0]) - 1, Integer.parseInt(splitStr[1]));

        return(
            monthOfYear[calendar.get(Calendar.MONTH)] + " " +
            calendar.get(Calendar.DATE) + ", " + calendar.get(Calendar.YEAR)
        );
    }

    /**
     *  Rescaling imageIcon by using filepath as a String and given size
     *  of the imageIcon.
     *
     *  @param filepath Path to image (String)
     *  @param width Icon width
     *  @param height Icon height
     *  @return
     */
    default ImageIcon scaleImageIcon(String filepath, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(filepath);
        Image getImage = imageIcon.getImage();
        Image newImage = getImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);

        return imageIcon;
    }

    /**
     *  Resizing large image on desired size using BufferedImage and
     *  using advance rendering method to get better image in cost of
     *  higher processing time.
     *
     *  @param image Image to be processed
     *  @param width Image width
     *  @param height Image height
     *  @return
     */
    default BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();

        graphics2D.setComposite(AlphaComposite.Src);

	graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.drawImage(image, 0, 0, width, height, null);
	graphics2D.dispose();

	return bufferedImage;
    }
}
