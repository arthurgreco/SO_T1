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
            "SO pronto!\nComandos:\n" +
            " new <prog>\n rm <pid>\n ps\n setDelta <n>\n execRR <delta>\n exec <pid>\n help\n exit"
        );
        console.loop(System.in);
    }
}
