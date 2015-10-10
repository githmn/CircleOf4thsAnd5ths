package com.yhamano.co4a5.music;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Circle {

	public int th = 0;
	public static final int FOURTH = 5;
	public static final int FIFTH = 7;

	public Circle(int th){
		this.th = th;
	}

	private static final double OMEGA = 2.0 * Math.PI / 12;
	private static final int RADIUS = 128;
	private static final int OFFSET = 32;
	private static final int W = 2*RADIUS + 2*OFFSET;
	private static final int H = 2*RADIUS + 2*OFFSET;

	private static final double MAT[][] = {
			{Math.cos(3 * OMEGA), -Math.sin(3 * OMEGA)},
			{Math.sin(3 * OMEGA), Math.cos(3 * OMEGA)}
	};

	public BufferedImage draw(Chord chord) {

		BufferedImage dstImg = new BufferedImage(W, H, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D dstG = dstImg.createGraphics();
		dstG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// fill background
		dstG.setPaint(Color.white);
		dstG.fillRect(0, 0, W, H);

		// drawCircle
		dstG.setPaint(Color.black);
		dstG.drawOval(W/2-RADIUS, H/2-RADIUS, 2*RADIUS, 2*RADIUS);

		// Draw Diagonal
		dstG.setPaint(Color.gray);
		for(int i = 0; i < 8; i++){

			double dx1 = Math.cos(i * OMEGA);
			double dy1 = Math.sin(i * OMEGA);
			double dx2 = Math.cos(i * OMEGA + Math.PI);
			double dy2 = Math.sin(i * OMEGA + Math.PI);

			int ix1 = (int) (dx1 * RADIUS);
			int iy1 = (int) (dy1 * RADIUS);
			int ix2 = (int) (dx2 * RADIUS);
			int iy2 = (int) (dy2 * RADIUS);

			dstG.drawLine(W/2+ix1, H-1-(H/2+iy1), W/2+ix2, H-1-(H/2+iy2));
		}

		// Draw caption
		dstG.setPaint(Color.black);
		for(int i = 0; i < Note.values().length; i++) {

			// Calculate clockwise
			double dx = Math.cos(i * -OMEGA);
			double dy = Math.sin(i * -OMEGA);

			// Rotate 90-degree counterclockwise
			double rdx = MAT[0][0]*dx + MAT[0][1]*dy;
			double rdy = MAT[1][0]*dx + MAT[1][1]*dy;

			int ix = (int) (rdx * (RADIUS + OFFSET/2));
			int iy = (int) (rdy * (RADIUS + OFFSET/2));

			// Calculate circle value
			int noteIdx = i*this.th%12;
			int noteIdxThisRoot = (noteIdx+chord.root.ordinal())%12;
			printString(dstG, Note.values()[noteIdxThisRoot].toString(), 0, W/2+ix, H-1-(H/2+iy));

		}

		// Draw polygon
		dstG.setPaint(Color.red);
		for(int i = 0; i < chord.notes.length; i++){

			int note1 = chord.notes[i].ordinal();
			int note2 = chord.notes[(i+1)%chord.notes.length].ordinal();

			// Calculate circle value
			int noteIdx1 = note1*this.th%12;
			int noteIdx2 = note2*this.th%12;

			// Calculate clockwise
			double dx1 = Math.cos(noteIdx1 * -OMEGA);
			double dy1 = Math.sin(noteIdx1 * -OMEGA);
			double dx2 = Math.cos(noteIdx2 * -OMEGA);
			double dy2 = Math.sin(noteIdx2 * -OMEGA);

			double rdx1 = MAT[0][0]*dx1 + MAT[0][1]*dy1;
			double rdy1 = MAT[1][0]*dx1 + MAT[1][1]*dy1;
			double rdx2 = MAT[0][0]*dx2 + MAT[0][1]*dy2;
			double rdy2 = MAT[1][0]*dx2 + MAT[1][1]*dy2;

			int ix1 = (int) (rdx1 * RADIUS);
			int iy1 = (int) (rdy1 * RADIUS);
			int ix2 = (int) (rdx2 * RADIUS);
			int iy2 = (int) (rdy2 * RADIUS);

			dstG.drawLine(W/2+ix1, H-1-(H/2+iy1), W/2+ix2, H-1-(H/2+iy2));

		}

		// Draw CodeName
		dstG.setPaint(Color.black);
		dstG.setFont(new Font("", Font.BOLD, 16));
		printString(dstG, chord.name, 0, OFFSET, OFFSET);

		return dstImg;
	}

    private void printString(Graphics2D g2d, String s, int width, int XPos, int YPos){
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(s, g2d);
        int x = XPos - ((int) r.getWidth()) / 2;
        int y = YPos - ((int) r.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(s, x, y);
   }

}
