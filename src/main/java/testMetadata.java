/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.util.SafeClose;

/**
 *
 * @author Dimitri
 */
public class testMetadata {

	private static Attributes object = null;
	private static DicomInputStream din = null;

	public static void main(String args[]) throws IOException {

		File inputFile = new File("C:\\Users\\Dimitri\\Desktop\\test-SR.dcm");

		object = loadDicomObject(inputFile);

		String io = chooserTagDicom(inputFile);

		System.out.println(io);

		// DicomMetaData metaData = new DicomMetaData( attr, attr3);

		// String str = attr3.toString();

	}

	public static String keywordOf(int tag) {
		for (java.lang.reflect.Field field : Tag.class.getDeclaredFields()) {
			try {
				if (field.getInt(null) == tag)
					// return field.getName();
					return field.toString();
			} catch (Exception ignore) {
			}
			;
		}
		return null;
	}

	/**
	 * Affiche les donnees lu pour un tag choisi /Display Tag
	 * Sauf pour les parametres qui utilise vr=SQ/ not VR = SQ
	 * 
	 * @param file
	 *            : donne le nom d'un fichier/Giving noun file
	 * @param tag
	 *            : int tag, le tag que nous voulons afficher
	 * @return String : donne la valeur du tag choisi
	 * @throws IOException
	 */

	/*
	 * public String affichageTagDonnee(File file,int tag) throws IOException{
	 * DicomInputStream dis = new DicomInputStream(file);
	 * BulkData data =dis.createBulkData();
	 * if(object.contains(tag)==true ){
	 * if (object.vm(tag)!=1 ){
	 * String tagValue2[] = ((DicomObject) object).getStrings(tag);//Conversion table in List to String
	 * String tagValue =displayTag.arrayToString(tagValue2,"\\");
	 * return this.setNameTag(tagValue);
	 * }else{
	 * String nameTag=object.getString(tag);
	 * return this.setNameTag(nameTag);
	 * }
	 * }
	 * else
	 * return this.setNameTag(null);
	 * }
	 */

	/**
	 * Choosing dicomObjet Item VR= SQ
	 * 
	 * @param file
	 *            : file inout
	 * @param TAG
	 *            : new tag
	 * @throws IOException
	 */
	public static String chooserTagDicom(File file) throws IOException {

		din = new DicomInputStream(file);

		object = din.readFileMetaInformation();

		String value = object.toString();

		object = din.readDataset(-1, -1);
		String value2 = object.toString(0x00020002, Tag.PixelData);

		return value + value2;

	}

	private static Attributes loadDicomObject(File f) throws IOException {
		if (f == null)
			return null;
		DicomInputStream dis = new DicomInputStream(f);
		try {
			return dis.readDataset(-1, -1);
		} finally {
			SafeClose.close(dis);
		}
	}

}
