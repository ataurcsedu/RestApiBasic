/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.utils;

/**
 *
 * @author Ataur Rahman
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.rest.exception.FieldErrorDTO;
import com.rest.exception.Errors;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.validation.FieldError;

/**
 *
 * @author Maverick
 */
public class Utils {

    public static final String DATE_FORMAT_REGEX = "\\d\\d\\d\\d[/](0[1-9]|1[012]|[1-9])[/](0[1-9]|[1-9]|[12][0-9]|3[01])"; //yyyy/MM/dd
    public static final String DATE_FORMAT_DATE_ONLY = "yyyy/MM/dd";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_NO_REGEX = "[0-9]+";

    public static Date getStringToDate(String stringDate) {


        if (stringDate == null || stringDate.length() < 1) {
            return null;
        } else {

            String[] dt = stringDate.split("/");

            if (dt[0].length() < 4) {
                stringDate = dt[2] + "/" + dt[1] + "/" + dt[0];
            }

            String dateOfBirth = stringDate.trim();

            if (!dateOfBirth.matches(DATE_FORMAT_REGEX)) {
                return null;
            }

        }

        try {
            SimpleDateFormat formatter;

            formatter = new SimpleDateFormat(DATE_FORMAT_DATE_ONLY);
            return formatter.parse(stringDate);

        } catch (Exception x) {
            //x.printStackTrace();
        }
        return null;
    }

    public static String getDateToString(Date date) {
        try {
            if (date == null) {
                return "0000/00/00";
            }
            return (new SimpleDateFormat("yyyy/MM/dd")).format(date);
        } catch (Exception ex) {
            return "";
        }
    }

    public static byte[] extractBytes(String ImageName) throws IOException {
        // open image
        File imgPath = new File(ImageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
    }

    public static String buildLikeQuery(String colName, String value) {
        if (!Utils.isEmpty(colName) && !Utils.isEmpty(value)) {
            value = value.replaceAll("'", "");
            return " UPPER(" + colName + ") LIKE '%" + value.toUpperCase() + "%' AND ";
        }

        return "";
    }

    public static String buildJPQLLikeQuery(String colName, String value) {
        if (!Utils.isEmpty(colName) && !Utils.isEmpty(value)) {
            value = value.replaceAll("'", "");
            return colName + " LIKE '%" + value + "%' and ";
        }

        return "";
    }

    public static String buildEqualQuery(String colName, String value) {
        if (Utils.isEmpty(colName) || Utils.isEmpty(value)) {
            return "";
        }
        value = value.replaceAll("'", "");
        return colName + " = '" + value + "' AND ";
        //return " UPPER(" + colName + ") = '" + value.toUpperCase() + "' AND ";
    }

    public static String buildDateCompareQuery(String colName, String compare, Date date) {
        if (!Utils.isEmpty(colName) && !Utils.isEmpty(compare) && date != null) {
            return " TRUNC(" + colName + ") " + compare + " TO_DATE('" + getDateToString(date) + "','yyyy/MM/dd') AND";
        }

        return "";
    }

    public static boolean isEmpty(String s) {
        return ((s == null) || s.isEmpty());
    }

    public static boolean compareLong(Long value1, Long value2) {
        if (value1 == null && value2 == null) {
            return true;
        }
        if (value1 == null || value2 == null) {
            return false;
        }
        if (value1.compareTo(value2) == 0) {
            return true;
        }
        return false;
    }

    public static XMLGregorianCalendar getGregorianDate(String dateTime) {
        if (dateTime == null || dateTime.length() < 1) {
            return null;
        } else {

            String[] dt = dateTime.split("/");

            if (dt[0].length() < 4) {
                dateTime = dt[2] + "/" + dt[1] + "/" + dt[0];
            }

            String dateOfBirth = dateTime.trim();

            if (!dateOfBirth.matches(DATE_FORMAT_REGEX)) {
                return null;
            }

        }
        XMLGregorianCalendar dateTimeXMLGregorianCalendar;
        try {
            //personalInformation.setDateOfBirth(DatatypeFactory.newInstance().newXMLGregorianCalendar());
            dateTimeXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        } catch (DatatypeConfigurationException e) {

//            LOGGER.debug("Date of Birth Errors");
            return null;
        }
        if (dateTimeXMLGregorianCalendar != null) {
            dateTimeXMLGregorianCalendar.setMinute(0);
            dateTimeXMLGregorianCalendar.setSecond(0);
            dateTimeXMLGregorianCalendar.setHour(0);
        }
        if (dateTime != null) {

            String[] birthDateArray = dateTime.split("/");
            dateTimeXMLGregorianCalendar.setYear(Integer.parseInt(birthDateArray[0]));
            dateTimeXMLGregorianCalendar.setMonth(Integer.parseInt(birthDateArray[1]));
            dateTimeXMLGregorianCalendar.setDay(Integer.parseInt(birthDateArray[2]));

        }

        return dateTimeXMLGregorianCalendar;
    }

    public static void printSoapRequestResponse() {
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    }

    public static BufferedImage createFlipped(BufferedImage image) {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
        return createTransformed(image, at);
    }

    public static BufferedImage createTransformed(BufferedImage image, AffineTransform at) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static BufferedImage convertToGray(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static byte[] bufferedImageToByte(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "bmp", baos);
//        baos.flush();
        byte[] imageInByte = baos.toByteArray();
//        baos.close();
        return imageInByte;
    }

    public static void writeWsqInFileSystem(byte[] outbyte, String filePath) {
        if (outbyte == null) {
            return;
        }

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(filePath + ".wsq");
            DataOutputStream dos = new DataOutputStream(fos);
            dos.write(outbyte, 0, outbyte.length);
            dos.close();
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }




    }

    public static String buildJPQLLikeQueryOR(String colName, String value, boolean isAnd) {
        if (!Utils.isEmpty(colName) && !Utils.isEmpty(value)) {
            value = value.replaceAll("'", "");
            if (!isAnd) {
                return colName + " LIKE '%" + value + "%' or ";
            } else {
                return colName + " LIKE '%" + value + "%' and ";
            }
        }

        return "";
    }

    public static void showErrorMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error Message", JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccessMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Success Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public static int showConfirmMsg(String msg) {
        int result = JOptionPane.showConfirmDialog(null, msg);
        return result;
    }

    public static boolean validateEmailAddress(String email_address) {

        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);

        if (email_address.length() > 0) {

            matcher = pattern.matcher(email_address);
            return matcher.matches();
        }

        return false;
    }

    public static boolean is_validate_telephone(String telephone_no) {
        String tmpPhone = "";
        if (telephone_no != null && !telephone_no.equalsIgnoreCase("")) {
            if(telephone_no.startsWith("+"))
                tmpPhone = telephone_no.substring(1, telephone_no.length()-1);
            else 
                tmpPhone = telephone_no;
            Pattern pattern = Pattern.compile(PHONE_NO_REGEX);
            Matcher matcher = pattern.matcher(tmpPhone);
            return matcher.matches();
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("result is " +is_validate_telephone(null));
    }
    
    public static Errors processApiError(List<FieldError> fieldError,HttpServletResponse resp){
        if(fieldError!=null && fieldError.size() > 0){
            resp.setStatus(resp.SC_BAD_REQUEST);
            Errors v = new Errors();
            for (FieldError f : fieldError) {
                String message = f.getDefaultMessage();
                message = message.split(";")[0].replaceAll("java.lang.", "");
                FieldErrorDTO d = new FieldErrorDTO(f.getField(), message);
                v.setFieldErrors(d);
            }
            return v;
        }
        return null;
    }
    
    public static Errors processApiError(String message,int code){
        Errors errors = new Errors();
        errors.setInternalErrors(message, code);
        return errors;
    }
    
}

