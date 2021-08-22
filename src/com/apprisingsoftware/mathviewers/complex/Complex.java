package com.apprisingsoftware.mathviewers.complex;

public class Complex {

	double real, imag;

	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}
	public Complex add(Complex other) {
		return new Complex(real + other.real, imag + other.imag);
	}

	public Complex multiply(Complex other) {
		return new Complex(real * other.real - imag * other.imag, real * other.imag + imag * other.real);
	}
	public Complex divide(Complex other) {
		double a = real, b = imag, c = other.real, d = other.imag;
		double den = c*c + d*d;
		return new Complex((a*c+b*d)/den, (b*c-a*d)/den);
	}
	public Complex exp() {
		return new Complex(Math.exp(real) * Math.cos(imag), Math.exp(real) * Math.sin(imag));
	}
	public Complex exponentiate(Complex base) {
		double a = base.real, b = base.imag, c = real, d = imag;
		double q = c * Math.atan2(b, a) + 0.5*d*Math.log(a*a + b*b);
		double r = Math.pow(a*a + b*b, c/2) * Math.exp(-d * Math.atan2(b, a));
		Complex rv = new Complex(r * Math.cos(q), r * Math.sin(q));
		return rv;
	}
	public Complex logarithm(Complex base) {
		double a = real, b = imag, c = base.real, d = base.imag;
		Complex num = new Complex(Math.log(Math.sqrt(a*a+b*b)), Math.atan2(b, a));
		Complex den = new Complex(Math.log(Math.sqrt(c*c+d*d)), Math.atan2(d, c));
		Complex rv = num.divide(den);
//		System.out.println("log base " + base + " of " + this);
//		System.out.println("equals " + rv);
		return rv;
	}

	@Override public String toString() {
		return real + "+" + imag + "i";
	}

}
