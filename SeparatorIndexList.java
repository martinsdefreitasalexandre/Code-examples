package pattern;

import java.util.Arrays;

/**
 * @author Alexdkk (Alexandre Martins de Freitas), 23/10/1994, PT
 * 
 * The following algorithm increases the find free index by 95%+ if used as 5000 separatorCount for 500000 valuesCount for instance
 * which makes a search a max of 100+5000 instead of up to 500000
 * Also saves minimum value available to speed up even further
 Data class to be replaced by the index class sucha as a Player/Non Player Character Class for instance
**/
public class SeparatorIndexList {

	private static class Data {
		private int index;
		
		public void setIndex(int index) {
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}
	}
	
	private int minUsedIndex;
	private Data[] values;
	private int[] separator;
	
	private int separatorCount;
	
	
	public SeparatorIndexList(int valuesCount, int separatorCount) {
		this.separatorCount = separatorCount;
		separator = new int[valuesCount / separatorCount];
		Arrays.fill(separator, separatorCount);
	}
	
	public void add(Data data) {
		int minSeparator = minUsedIndex / separatorCount;
		if (separator[minSeparator] > 0) {
			for (int index = minUsedIndex; minUsedIndex < values.length; index++) {
				if (values[index] == null) {
					separator[minSeparator]--;
					values[index] = data;
					minUsedIndex = index;
					data.setIndex(index);
					break;
				}
			}
		} else {
			label: for (int separatorIndex = minSeparator+1; separatorIndex < separator.length; separatorIndex++) {
				if (separator[separatorIndex] > 0) {
					for (int index = separatorIndex * separatorCount; index < (separatorIndex + 1) * separatorCount; separatorIndex++) {
						if (values[index] == null) {
							separator[separatorIndex]--;
							values[index] = data;
							minUsedIndex = index;
							data.setIndex(index);
							break label;
						}
					}
				}
				
			}
		}
	}
	
	public void remove(Data data) {
		if (minUsedIndex > data.getIndex())
			minUsedIndex = data.getIndex();
		values[data.getIndex()] = null;
		separator[data.getIndex() / separatorCount]++;
		data.setIndex(0);
	}
	
	public boolean contains(Data value) {
		return values[value.getIndex()] == value;
	}
}
