import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DicomPanel extends JPanel {
	private Attributes attr;
	private BufferedImage image;
	private int width;
	private int height;

	public DicomPanel(Attributes attr) {
		this.attr = attr;

		width = attr.getInt(Tag.Columns, 0);
		height = attr.getInt(Tag.Rows, 0);
		try {
			byte[] bytes = attr.getBytes(Tag.PixelData);
			log.info("w*h={} - bytes.length={}", width * height, bytes.length);

			image = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);
			int index = 0;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					index = (y * width + x) * 2;
					image.setRGB(x, y, bytes[index] * Short.MAX_VALUE / 105);
				}
			}
			log.error("index = {}", index);
		} catch (Exception e) {
			log.error("", e);
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, null);
		g.drawString("This is my custom Panel!", 10, 20);
	}
}
