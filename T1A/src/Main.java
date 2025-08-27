public class Main {
    public static void main(String[] args) throws Exception {
        int TAM_MEM = 1024;
        int TAM_PG = 16;

        Memory mem = new Memory(TAM_MEM, TAM_PG);
        GM gm = new PagingMemoryManager(mem);
        ProgramRepository repo = new ProgramRepository();
        ProcessManager gp = new ProcessManager(gm, repo.build());
        Console console = new Console(gp, gm);

        System.out.println(
                "SO pronto. Comandos: new <prog>, rm <pid>, ps, dump <pid>, dumpM <i> <f>, exec <pid>, traceOn, traceOff, exit");
        console.loop(System.in);
    }
}
