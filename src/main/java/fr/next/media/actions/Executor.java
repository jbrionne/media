package fr.next.media.actions;

public class Executor {

	public static double plus(double i, double j) {
		return i + j;
	}
	
	public static long plus(long i, long j) {
		return i + j;
	}
	
	public static double minus(double i, double j) {
		return i - j;
	}
	
	public static long minus(long i, long j) {
		return i - j;
	}
	
	public static double mult(double i, double j) {
		return i * j;
	}
	
	public static long mult(long i, long j) {
		return i * j;
	}
	
	public static double div(double i, double j) {
		return i / j;
	}
	
	public static double div(long i, long j) {
		return i / (double) j;
	}
	
	public static void forLoop(Iterable iter, RunnableWithArgs run) {
		for(Object o : iter) {
			run.run(o);
		}
	}
	
	public static void forLoop(int init, int max, int inc, Runnable run) {
		for(int i = init; i < max; i = i + inc) {
			run.run();
		}
	}
	
	public static void ifBlock(boolean cond, Runnable ifTrue, Runnable ifFalse) {
		if(cond) {
			ifTrue.run();
		} else {
			ifFalse.run();
		}
	}
	
}
