package com.yhamano.co4a5;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.yhamano.co4a5.music.Chord;
import com.yhamano.co4a5.music.Circle;
import com.yhamano.co4a5.music.Degree;
import com.yhamano.co4a5.music.Note;

public class PrintChords {

	public static void main(String args[]) {

		File dstFile = null;
		try {
			dstFile = new File(args[0]);
		} catch (Exception e){
			e.printStackTrace();
			System.err.println("args[0]: dstenation file path.");
			return;
		}

        Circle circleOf4ths = new Circle(Circle.FOURTH);
		Chord c_C = new Chord("C", Note.C, new Degree[]{Degree.P1st, Degree.M3rd, Degree.P5th});
		BufferedImage dstImg = circleOf4ths.draw(c_C);
		try {
			ImageIO.write(dstImg, "png", dstFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
