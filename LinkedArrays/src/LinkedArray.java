import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class LinkedArray<E> implements List<E> {

    private class ArrayLink<E> {
        private transient Object[] data;
        private ArrayLink<E> next;
        private ArrayLink<E> prev;

        public ArrayLink() {
            this(10);
        }

        public ArrayLink(int module_size) {
            data = new Object[module_size];
            next = prev = null;
        }
    }
    private int module_size;
    private int num_modules;
    private int modulo;

    private ArrayLink<E> front;
    private ArrayLink<E> tail;


    public LinkedArray(int mSize) {
        this.module_size = mSize;
        this.modulo = 0;
        this.num_modules = 0;
        front = tail = new ArrayLink<E>(mSize);
    }

    public LinkedArray() {
        this(10);
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
            if (comp.equals(obj)) return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedArrayIterator<E>(this);
    }

    @SuppressWarnings("unchecked")
    class LinkedArrayIterator<E> implements Iterator<E> {

        private LinkedArray<E>.ArrayLink<E> currModule;
        private int index;
        private int currIndex;
        private int size;
        private int module_size;

        public LinkedArrayIterator(LinkedArray<E> currModule) {
            this.currModule = currModule.front;
            this.index = this.currIndex = 0;
            this.size = currModule.size();
            this.module_size = currModule.module_size;

        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            if (currIndex >= module_size) {
                currModule = currModule.next;
                currIndex = 0;
            }
            if (currModule == null) return (E) null;
            index++;
            return (E) currModule.data[currIndex++];
        }
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        List.super.forEach(action);
    }

    @Override
    public Object[] toArray() {
        Object[] data = new Object[this.size()];
        int index = 0;
        for (Object o : this) {
            data[index++] = o;
        }
        return data;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) this.toArray();

    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return List.super.toArray(generator);
    }


    @Override
    public boolean add(E e) {
        try {
            ArrayLink<E> curr = this.front;
            for (int module = 0; module < num_modules; module++) {
                curr = curr.next;
            }
            if (modulo < module_size) {
                curr.data[modulo + 1] = e;
                modulo++;
            } else {
                curr.next = new ArrayLink<>(module_size);
                curr = curr.next;
                curr.data[0] = e;
                modulo = 0;
            }

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
