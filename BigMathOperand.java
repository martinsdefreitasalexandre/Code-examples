import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author Alexdkk (Alexandre Martins de Freitas), 23/10/1994, PT
 * Alternative to biginteger using bytes as digit positions instead of String
 * Performance faster or slower depending in amount of calls.
 * Created for test reasons
**/
public class BigMathOperand {
	

	private static final byte LOWEST = 0;
	private static final byte NEXT_ADD = 1, MAXIMUM = 10;

	
	private static void add(byte[] variable, byte[] constant) {
		if (variable.length <= Byte.MAX_VALUE)
			byteAdd(variable, constant);
		else if (variable.length <= Short.MAX_VALUE)
			shortAdd(variable, constant);
		else
			intAdd(variable, constant);
	}
	
	
	private static void byteAdd(byte[] variable, byte[] constant) {
		for (byte index = 0; index < variable.length; index++) {
			if (variable[index] == LOWEST) {
				if (constant[index] == LOWEST)
					continue;
				variable[index] = constant[index];
				continue;
			}
			byte digitIndex = (byte) (variable[index] + constant[index]);
			if (digitIndex >= MAXIMUM) {
				digitIndex -= MAXIMUM;
				variable[index] = digitIndex;
				for (byte nextIndex = (byte) (index + NEXT_ADD); nextIndex < variable.length; nextIndex++) {
					byte nextDigitIndex = (byte) (variable[nextIndex] + 1);
					boolean finish = false;
					if (nextDigitIndex == MAXIMUM)  
						nextDigitIndex -= MAXIMUM;
					else 
						finish = true;
					variable[nextIndex] = nextDigitIndex;
					if (finish)
						break;					
				}
				continue;
				
			}
			variable[index] = digitIndex;
		}
	}
	
	private static void shortAdd(byte[] variable, byte[] constant) {
		for (short index = 0; index < variable.length; index++) {
			if (variable[index] == LOWEST) {
				if (constant[index] == LOWEST)
					continue;
				variable[index] = constant[index];
				continue;
			}
			byte digitIndex = (byte) (variable[index] + constant[index]);
			if (digitIndex >= MAXIMUM) {
				digitIndex -= MAXIMUM;
				variable[index] = digitIndex;
				for (short nextIndex = (short) (index + NEXT_ADD); nextIndex < variable.length; nextIndex++) {
					byte nextDigitIndex = (byte) (variable[nextIndex] + 1);
					boolean finish = false;
					if (nextDigitIndex == MAXIMUM)  
						nextDigitIndex -= MAXIMUM;
					else 
						finish = true;
					variable[nextIndex] = nextDigitIndex;
					if (finish)
						break;					
				}
				continue;
				
			}
			variable[index] = digitIndex;
		}
	}
	
	private static void intAdd(byte[] variable, byte[] constant) {
		for (int index = 0; index < variable.length; index++) {
			if (variable[index] == LOWEST) {
				if (constant[index] == LOWEST)
					continue;
				variable[index] = constant[index];
				continue;
			}
			byte digitIndex = (byte) (variable[index] + constant[index]);
			if (digitIndex >= MAXIMUM) {
				digitIndex -= MAXIMUM;
				variable[index] = digitIndex;
				for (int nextIndex = (index + NEXT_ADD); nextIndex < variable.length; nextIndex++) {
					byte nextDigitIndex = (byte) (variable[nextIndex] + 1);
					boolean finish = false;
					if (nextDigitIndex == MAXIMUM)  
						nextDigitIndex -= MAXIMUM;
					else 
						finish = true;
					variable[nextIndex] = nextDigitIndex;
					if (finish)
						break;					
				}
				continue;
				
			}
			variable[index] = digitIndex;
		}
	}
	
	
	//todo mult. loop till value the same and addby itself every time
	
	
	public static void main(String[] main) {
		int loops = 1000000; //1m loop
		//initialize to ensure speed the same
		BigInteger var1 = BigInteger.ZERO;
		BigInteger cons1 = BigInteger.valueOf(1);
		for (int i = 0; i < loops; i++)
			var1.add(cons1);
		
		var1 = BigInteger.ZERO;
		cons1 = BigInteger.valueOf(2);
		
		byte[] var2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		byte[] cons2 = {2, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		for (int i = 0; i < loops; i++)
			add(var2, cons2);
		
		var2 = new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		//benchmark
		long startTime1 = System.nanoTime();
		for (int i = 0; i < loops; i++)
			var1 = var1.add(cons1);
		long totalTime1 = System.nanoTime()-startTime1;
		
		long startTime2 = System.nanoTime();
		for (int i = 0; i < loops; i++)
			add(var2, cons2);
		long totalTime2 = System.nanoTime()-startTime2;
		
		double timeDiff = (double)totalTime1 / (double)totalTime2;
	
		System.out.println("New code is "+timeDiff+"x times faster."+", "+totalTime1+", "+totalTime2);
		
		System.out.println(var1+", "+ Arrays.toString(var2));
		
		
	}
	
}
