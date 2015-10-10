package com.yhamano.co4a5.music;


public class Chord {

	String name;
	Note root;
	Degree notes[];
	public Chord(String name, Note root, Degree notes[]) {
		this.name = name;
		this.root = root;
		this.notes = notes;
	}

}
