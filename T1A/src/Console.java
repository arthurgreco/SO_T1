import java.io.InputStream;
import java.util.Scanner;

public class Console {
    private final ProcessManager gp;
    private final GM gm;

    public Console(ProcessManager gp, GM gm) {
        this.gp = gp;
        this.gm = gm;
    }

    public void loop(InputStream in) {
        Scanner sc = new Scanner(in);
        System.out.println(helpText());
        while (true) {
            System.out.print("> ");
            if (!sc.hasNextLine()) break;
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String cmd = parts[0];

            try {
                switch (cmd) {
                    case "help":
                        System.out.println(helpText());
                        break;
                    case "exit":
                        System.out.println("bye!");
                        return;
                    case "new":
                        if (parts.length < 2) {
                            System.out.println("Uso: new <prog>");
                            break;
                        }
                        gp.criaProcesso(parts[1]);
                        break;
                    case "rm":
                        if (parts.length < 2) {
                            System.out.println("Uso: rm <pid>");
                            break;
                        }
                        gp.rm(Integer.parseInt(parts[1]));
                        break;
                    case "ps":
                        gp.prontos().forEach(p -> System.out.println(p.toString()));
                        break;
                    case "setDelta":
                        if (parts.length < 2) { System.out.println("Uso: setDelta <n>"); break; }
                        gp.setQuantum(Integer.parseInt(parts[1]));
                        System.out.println("Delta/quantum ajustado.");
                        break;
                    case "execRR":
                        int d = -1;
                        if (parts.length >= 2) d = Integer.parseInt(parts[1]);
                        gp.execRR(d);
                        break;
                    case "exec":
                        if (parts.length < 2) { System.out.println("Uso: exec <pid>"); break; }
                        gp.exec(Integer.parseInt(parts[1]));
                        break;
                    default:
                        System.out.println("Comando desconhecido. Digite 'help'.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private String helpText() {
        return String.join("\n",
            "Comandos:",
            "  new <prog>      - cria processo a partir do programa do repositório",
            "  rm <pid>        - remove processo",
            "  ps              - lista prontos/rodando",
            "  setDelta <n>    - define quantum (fatia) em instruções",
            "  execRR <delta>  - roda round-robin com preempção (usa delta atual se omitido)",
            "  exec <pid>      - executa processo sem preempção até terminar",
            "  help            - mostra este texto",
            "  exit            - sai"
        );
    }
}
