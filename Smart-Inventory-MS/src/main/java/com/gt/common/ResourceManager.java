package com.gt.common;

import com.gt.common.constants.StrConstants;
import com.gt.common.utils.CryptographicUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * com.gt.common-ResourceManager.java<br/>
 *
 * @author Irshad
 * <p>
 * Created on: Nov 1, 2024<br/>
 */
public class ResourceManager {

    public static final String resourceMapFile = "string-resource.ini";
    private static final String a = "gt?Pass,e#. ";
    private static Map<String, String> stringConstantsMap;

    /**
     * Fetch a string constant by key from the resource file.
     *
     * @param key the key of the string constant.
     * @return the corresponding value or null if not found.
     */
    public static synchronized String getString(String key) {
        if (stringConstantsMap == null) {
            try {
                stringConstantsMap = readMap(resourceMapFile, false);
            } catch (Exception e) {
                stringConstantsMap = StrConstants.getMap();
            }
        }
        return stringConstantsMap.get(key);
    }

    /**
     * Reads a map of key-value pairs from a file.
     *
     * @param file   the file to read.
     * @param isEncry true if the file is encrypted.
     * @return the map of key-value pairs.
     * @throws IOException if an error occurs while reading the file.
     */
    public static Map<String, String> readMap(String file, boolean isEncry) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        Map<String, String> map = new HashMap<>();
        String str;
        while ((str = in.readLine()) != null) {
            String[] spl = str.split(":");
            if (isEncry) {
                map.put(CryptographicUtil.decryptText(spl[0].trim(), a), CryptographicUtil.decryptText(spl[1].trim(), a));
            } else {
                map.put(spl[0].trim(), spl[1].trim());
            }
        }
        in.close();
        return map;
    }

    /**
     * Fetch an image from the resources folder.
     *
     * @param fileName the name of the image file.
     * @return the Image or null if not found.
     */
    public static Image getImage(String fileName) {
        URL resource = ResourceManager.class.getResource("/images/" + fileName);
        if (resource == null) {
            System.err.println("Image resource not found: /images/" + fileName);
            return null;
        }
        return new ImageIcon(resource).getImage();
    }

    /**
     * Fetch an ImageIcon from the resources folder.
     *
     * @param resourcePath the path of the image file relative to the resources folder.
     * @return the ImageIcon or an empty icon if not found.
     */
    public static ImageIcon getImageIcon(String resourcePath) {
        URL resource = ResourceManager.class.getResource("/images/" + resourcePath);
        if (resource == null) {
            System.err.println("ImageIcon resource not found: /images/" + resourcePath);
            return new ImageIcon(); // Return an empty icon to prevent null pointer
        }
        return new ImageIcon(resource);
    }

    /**
     * Read a BufferedImage from the resources folder.
     *
     * @param imageName the name of the image file.
     * @return the BufferedImage or null if not found.
     */
    public static BufferedImage readImage(String imageName) {
        try {
            URL resource = ResourceManager.class.getResource("/images/" + imageName);
            if (resource == null) {
                System.err.println("BufferedImage resource not found: /images/" + imageName);
                return null;
            }
            return ImageIO.read(resource);
        } catch (IOException ie) {
            System.err.println("Error reading image: " + ie.getMessage());
        }
        return null;
    }

    /**
     * Test the image loading functionality.
     */
    public static void main(String[] args) {
        String[] testImages = {"logout-on.png", "nonexistent.png"};
        for (String img : testImages) {
            ImageIcon icon = getImageIcon(img);
            if (icon.getIconWidth() == -1) {
                System.err.println("Failed to load image: " + img);
            } else {
                System.out.println("Successfully loaded image: " + img);
            }
        }
    }
}
