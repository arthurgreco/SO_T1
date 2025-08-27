public class Memory {
    private final int tamMem, tamPg, nroFrames;
    private final int[] mem;

    public Memory(int tamMem, int tamPg) {
        if (tamMem <= 0 || tamPg <= 0 || tamMem % tamPg != 0)
            throw new IllegalArgumentException("tamMem deve ser múltiplo de tamPg e >0");
        this.tamMem = tamMem;
        this.tamPg = tamPg;
        this.nroFrames = tamMem / tamPg;
        this.mem = new int[tamMem];
    }

    public int tamPg() {
        return tamPg;
    }

    public int nroFrames() {
        return nroFrames;
    }

    public int tamMem() {
        return tamMem;
    }

    public int readFisico(int ea) {
        check(ea);
        return mem[ea];
    }

    public void writeFisico(int ea, int v) {
        check(ea);
        mem[ea] = v;
    }

    private void check(int ea) {
        if (ea < 0 || ea >= tamMem)
            throw new MemoryAccessException("Endereço físico inválido " + ea);
    }

    public FrameRange rangeFrame(int f) {
        int ini = f * tamPg, fim = ini + tamPg - 1;
        return new FrameRange(f, ini, fim);
    }
}
