package javaTools;

public class Candidate implements Comparable<Object> {

	private String name;
	private Integer occurrence;
	private int index;

	public Candidate(String name, Integer occurrence, int index) {
		this.name = name;
		this.occurrence = occurrence;
		this.index = index;
	}

	@Override
	public int compareTo(Object o) {
		if (o.getClass().equals(Candidate.class)) {

			Candidate candidateItem = (Candidate) o;

			// reversed order comparator flag for occurrence
			int compareOccurrence = -this.occurrence.compareTo(candidateItem.getOccurrence());

			// if both items have same occurrence value, we sort on item name
			if (compareOccurrence == 0)
				return this.name.compareTo(candidateItem.getName());
			return compareOccurrence;
		}
		return -1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(Integer occurence) {
		this.occurrence = occurence;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String toString() {
		return "Candidate [name=" + name + ", occurrence=" + occurrence + ", index=" + index + "]";
	}
}
