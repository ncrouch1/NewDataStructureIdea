import java.util.Iterator;
import java.util.LinkedList;

public class DynamicCache<E> {

    private LinkedList<Object[]> linker;
    private int num_links, module_size, modulo;


    public DynamicCache() {
        this(10);
    }

    public DynamicCache(int msize) {
        this.linker = new LinkedList<>();
        this.module_size = msize;
        this.modulo = 0;
        this.num_links = 0;
        this.linker.add(createLink());
    }

    public Object[] createLink() {
        return new Object[module_size];
    }

    public int size() {
        return num_links * module_size + modulo;
    }

    @SuppressWarnings("unchecked")
    public E[] getLink(int link) {
        if (link > num_links) {
            return null;
        }
        return (E[]) linker.get(link);
    }

    public boolean addLink(Object[] link) {
        num_links++;
        return linker.add(link);
    }

    public void addLink(int index, Object[] link) {
        num_links++;
        linker.add(index, link);
    }

    public boolean removeLink(Object[] link) {
        num_links--;
        return linker.remove(link);
    }

    @SuppressWarnings("unchecked")
    public E[] removeLink(int index) {
        num_links--;
        return (E[]) linker.remove(index);
    }

    @SuppressWarnings("unchecked")
    public E[] setLink(int index, Object[] link) {
        return (E[]) linker.set(index, link);
    }

    public void clear() {
        linker.clear();
        num_links = 0;
        modulo = 0;
    }

    public void clearAndResetModuleSize(int module_size) {
        this.clear();
        this.module_size = module_size;
    }

    @SuppressWarnings("unchecked")
    public boolean add(E item) {
        try {
            if (modulo == module_size) {
                addLink(createLink());
                modulo = 0;
            }
            E[] currLink = (E[] ) linker.getLast();
            currLink[modulo++] = item;
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public E get(int index) {
        Iterator<E> DRALItr = this.iterator();
        int curr = 0;
        while (DRALItr.hasNext() && curr < index - 1 ) {
            curr++;
            DRALItr.next();
        }
        if (DRALItr.hasNext()) {
            return DRALItr.next();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if (index < 0 || index > this.size())
            throw new IndexOutOfBoundsException("No such element exists");
        int link = index / num_links;
        int position = index % num_links;

        E[] lastLink = (E[]) linker.getLast();
        E[] currLink = getLink(link);
        E item = currLink[position];
        currLink[position] = lastLink[modulo--];
        return item;
    }
    public Iterator<E> iterator() {
        return new DynamicCacheIterator<E>(this);
    }

    private static class DynamicCacheIterator<E> implements Iterator<E> {

        private Iterator<Object[]> linkerIterator;
        private Object[] currLink;
        private int index, size, currLinkIndex;

        public DynamicCacheIterator(DynamicCache<E> list) {
            linkerIterator = list.linker.iterator();
            this.index = 0;
            int currLinkIndex = 0;
            this.size = list.size();
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            if (!hasNext()) return null;
            if (currLinkIndex == currLink.length) {
                if (!linkerIterator.hasNext()) return null;
                currLink = linkerIterator.next();
                currLinkIndex = 0;
            }
            index++;
            return (E) currLink[currLinkIndex++];
        }
    }
}