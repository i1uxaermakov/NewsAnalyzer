package hyo.betelgeuse.backend;


/*
Word occurence item holds the count, the number of times the word appeared,
and value, the String word.
 */
public class WordOccurrenceItem {
    String value;
    int count;

    public WordOccurrenceItem(String value, int count) {
        this.value = value;
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "WordOccurrenceItem{" +
                "value='" + value + '\'' +
                ", count=" + count +
                '}';
    }
}
