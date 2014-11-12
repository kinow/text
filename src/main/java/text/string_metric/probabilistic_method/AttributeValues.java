package text.string_metric.probabilistic_method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang.ObjectUtils;

public class AttributeValues<T> implements Iterable<T> {

	private final TreeSet<Entry<T>> values = new TreeSet<Entry<T>>();

    private int size = 0;
    
    public synchronized Entry<T> get(T value) {
        for (Entry<T> tEntry : values) {
            if (ObjectUtils.equals(tEntry.value, value)) {
                return tEntry;
            }
        }
        Entry<T> newEntry = new Entry<T>(value);
        values.add(newEntry);
        return newEntry;
    }

    public void merge(AttributeValues<T> other) {
        // Prevent concurrent modifications in case of self merge.
        TreeSet<Entry<T>> valuesToMerge = other == this ? new TreeSet<Entry<T>>(other.values) : other.values;
        if (other == this) {
            values.clear(); // Prevent growth of occurrence count in case of self merge.
        }
        for (Entry<T> value : valuesToMerge) {
            Entry<T> valueEntry = get(value.value);
            valueEntry.add(value.occurrence);
            values.add(valueEntry); // Forces reorder of occurrences in tree.
        }
    }

    public T mostCommon() {
        if (values.isEmpty()) {
            return null;
        }
        merge(this); // Merge on itself forces re-order of elements in set.
        return values.last().value;
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeValues)) {
            return false;
        }
        AttributeValues<T> that = (AttributeValues<T>) o;
        if (!values.equals(that.values)) {
            for (Entry<T> value : values) {
                AttributeValues<T>.Entry<T> entry = that.get(value.value);
                if (entry.occurrence != value.occurrence) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }
    
	public Iterator<T> iterator() {
		final Iterator<Entry<T>> iterator = values.iterator();
        return new Iterator<T>() {

            public boolean hasNext() {
                return iterator.hasNext();
            }

            public T next() {
                return iterator.next().value;
            }

            public void remove() {
                iterator.remove();
            }
        };
	}
	
	public List<T> asList() {
        List<T> list = new ArrayList<T>(size);
        for (Entry<T> value : values) {
            for (int i = 0; i < value.occurrence; i++) {
                list.add(value.value);
            }
        }
        return list;
    }

    public boolean hasMultipleValues() {
        Iterator<Entry<T>> iterator = values.iterator();
        boolean asOneElement = iterator.hasNext();
        boolean asMoreElements = false;
        if (iterator.hasNext()) {
            asMoreElements = iterator.hasNext();
        }
        return asOneElement && asMoreElements;
    }

    public int size() {
        return size;
    }

    public class Entry<E> implements Comparable<Entry<E>> {

        private final E value;

        private int occurrence = 0;

        public Entry(E value) {
            this.value = value;
        }

        public void add(int occurrence) {
            updateOccurrence(occurrence);
        }

        private void updateOccurrence(int occurrence) {
            this.occurrence += occurrence;
            size += occurrence;
        }

        public void increment() {
            updateOccurrence(1);
        }

        public int compareTo(Entry<E> tEntry) {
            if (ObjectUtils.equals(value, tEntry.value)) {
                return 0;
            }
            return occurrence - tEntry.occurrence;
        }

        public int getOccurrence() {
            return occurrence;
        }
    }

}
