import java.util.BitSet;
import java.util.Objects;

public class PagingMemoryManager implements GM {
    private final Memory mem;
    private final BitSet livre;

    public PagingMemoryManager(Memory mem) {
        this.mem = mem;
        this.livre = new BitSet(mem.nroFrames());
        this.livre.set(0, mem.nroFrames(), true);
    }

    @Override
    public int[] aloca(int n) {
        int paginas = (int) Math.ceil(n / (double) mem.tamPg());
        if (livre.cardinality() < paginas)
            return null;
        int[] tab = new int[paginas];
        int a = 0;
        for (int f = livre.nextSetBit(0); f >= 0 && a < paginas; f = livre.nextSetBit(f + 1)) {
            tab[a++] = f;
            livre.clear(f);
        }
        return tab;
    }

    @Override
    public void desaloca(int[] t) {
        if (t == null)
            return;
        for (int f : t) {
            livre.set(f);
            FrameRange r = mem.rangeFrame(f);
            for (int ea = r.ini; ea <= r.fim; ea++)
                mem.writeFisico(ea, 0);
        }
    }

    @Override
    public void carregaPrograma(int[] prog, int[] tab) {
        Objects.requireNonNull(prog);
        for (int l = 0; l < prog.length; l++)
            mem.writeFisico(traduz(tab, l), prog[l]);
    }

    @Override
    public int read(int[] t, int l) {
        return mem.readFisico(traduz(t, l));
    }

    @Override
    public void write(int[] t, int l, int v) {
        mem.writeFisico(traduz(t, l), v);
    }

    @Override
    public int traduz(int[] t, int l) {
        int p = l / mem.tamPg();
        int d = l % mem.tamPg();

        if (p < 0 || p >= t.length) {
            throw new MemoryAccessException(
                    "Endereço lógico inválido: " + l + " (página " + p + " não mapeada)");
        }

        int f = t[p];
        return mem.rangeFrame(f).ini + d;
    }
}
