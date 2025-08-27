public class FrameRange {
    public final int frame, ini, fim;

    public FrameRange(int f, int i, int fi) {
        frame = f;
        ini = i;
        fim = fi;
    }

    @Override
    public String toString() {
        return "frame=" + frame + " [" + ini + "," + fim + "]";
    }
}
