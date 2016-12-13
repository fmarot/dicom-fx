import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DicomLoader {
	public static void main(String[] args) throws Exception {

		try (DicomInputStream dis = new DicomInputStream(DicomLoader.class.getResourceAsStream("/1010_brain_mr_02_lee/I121"))) {
			Attributes attr = dis.readDataset(-1, -1);

			int width = attr.getInt(Tag.Columns, 0);
			int height = attr.getInt(Tag.Rows, 0);
			int samples = attr.getInt(Tag.SamplesPerPixel, 0);
			int bitsAllocated = attr.getInt(Tag.BitsAllocated, 0);

			String value2 = attr.toString(0x00020002, Tag.PixelData);
			log.info(value2);

			SwingUtilities.invokeLater( () -> {
				JFrame frame = new JFrame("FrameDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new DicomPanel(attr), BorderLayout.CENTER);
				frame.setSize(500, 500);
				frame.setVisible(true);
			});
		}
	}
}
