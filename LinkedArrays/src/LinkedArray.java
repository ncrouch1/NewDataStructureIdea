import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class LinkedArray<E> implements List<E> {
    private int module_size;
    private int num_modules;
    private int modulo;
    private transient Object[] data;
    private LinkedArray<E> nextArray;
    private LinkedArray<E> lastArray;


    public LinkedArray(int mSize) {
        this.module_size = mSize;
        this.modulo = 0;
        this.num_modules = 0;
        this.data = new Object[mSize];
        this.nextArray = null;
        this.lastArray = null;
    }

    @Override
    public int size() {
        return num_modules * module_size + modulo;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        E obj = (E) o;
        Iterator<E> itr = this.iterator();
        while (itr.hasNext()) {
            E comp = itr.next();
            if (comp == obj) return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedArrayIterator<E>(this);
    }
    @SuppressWarnings("unchecked")
    static class LinkedArrayIterator<E> implements Iterator<E> {

        private LinkedArray<E> currModule;
        private int index;
        private int size;
        private int module_size;

        public LinkedArrayIterator(LinkedArray<E> currModule) {
            this.currModule = currModule;
            this.index = 0;
            this.size = currModule.size();
            this.module_size = currModule.module_size;

        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            if (index >= module_size) {
                currModule = currModule.nextArray;
            }
            if (currModule == null) return (E) null;
            return (E) currModule.data[index++ % module_size];
        }
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        List.super.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return List.super.toArray(generator);
    }


    @Override
    public boolean add(E e) {
        try {
            LinkedArray<E> curr = this;
            for (int module = 0; module < num_modules; module++) {
                curr = curr.nextArray;
            }
            curr.data[modulo + 1] = e;
            modulo++;
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!this.contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E item : c) {
            if (!this.add(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return List.super.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        List.super.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        List.super.sort(c);
    }

    @Override
    public void clear() {
        this.num_modules = 0;
        this.modulo = 0;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public Spliterator<E> spliterator() {
        return List.super.spliterator();
    }

    @Override
    public Stream<E> stream() {
        return List.super.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return List.super.parallelStream();
    }
}
