package xyz.theprogramsrc.supercoreapi.global.objects;

public class Pair<L,R> {

    private final L l;
    private final R r;

    /**
     * Initializer
     * @param left the object in the left side of the pair
     * @param right the object in the right side of the pair
     */
    public Pair(L left, R right){
        this.l = left;
        this.r = right;
    }

    /**
     * Gets the object in the left side of the pair
     * @return the object in the left side
     * @deprecated As of v4.12.0. Replaced by {@link #getLeft()}
     *
     * <i>NOTE: This will be removed in the release v4.13.0 or higher</i>
     */
    public L getA() {
        return l;
    }

    /**
     * Gets the object in the right side of the pair
     * @return the object in the right side
     * @deprecated As of v4.12.0. Replaced by {@link #getRight()}
     *
     * <i>NOTE: This will be removed in the release v4.13.0 or higher</i>
     */
    public R getB() {
        return r;
    }

    /**
     * Gets the object in the left side of the pair
     * @return the object in the left side
     */
    public L getLeft() {
        return l;
    }

    /**
     * Gets the object in the right side of the pair
     * @return the object in the right side
     */
    public R getRight() {
        return r;
    }
}
