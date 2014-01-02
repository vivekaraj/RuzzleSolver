
public class Tuple<F, S> extends Object {
	private F fst;
	private S snd;
	public Tuple(F fst, S snd) {
		this.fst = fst;
		this.snd = snd;
	}
	public F getFirst() {
		return fst;
	}
	public S getSecond() {
		return snd;
	}
	public Tuple<F,S> setFirst(F fst) {
		this.fst = fst;
		return new Tuple<F,S>(this.fst, this.snd);
	}
	public Tuple<F,S> setSecond(S snd) {
		this.snd = snd;
		return new Tuple<F,S>(this.fst, this.snd);

	}
	public Tuple<F,S> setFirstSecond(F fst, S snd) {
		this.fst = fst;
		this.snd = snd;
		return new Tuple<F,S>(this.fst, this.snd);
	}	
}
