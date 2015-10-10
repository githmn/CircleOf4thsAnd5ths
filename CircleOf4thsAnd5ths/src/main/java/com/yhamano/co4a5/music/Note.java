package com.yhamano.co4a5.music;

public enum Note {

	C, Df, D, Ef, E, F, Gf, G, Af, A, Bf, B;

	public String toString() {
		return this.name().replaceAll("f", "♭");
	};

}
