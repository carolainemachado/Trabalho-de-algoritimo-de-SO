import java.util.Scanner;
import java.util.Random;
public class trab_alg_escalonamento {
    static int MAXIMO_TEMPO_EXECUCAO = 65535;
    static int n_processos = 3;
    int[] id = new int[n_processos];
    public static void main(String[] args) {

        int[] tempo_execucao = new int[n_processos];
        int[] tempo_espera = new int[n_processos];
        int[] tempo_restante = new int[n_processos];
        int[] tempo_chegada = new int[n_processos];
        int[] prioridade = new int[n_processos];

        Scanner teclado = new Scanner (System.in);

        popular_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);

        imprime_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);

        //Escolher algoritmo
        int algoritimo;

        while(true) {
            System.out.println ( "---- Escolha sua opção: ----"
                    + "\n1- FCFS"
                    + "\n2- SJF Preemptivo"
                    + "\n3- SJF Nao Preemptivo"
                    + "\n4- Prioridade não preemptivo"
                    + "\n5- Prioridade preemptivo"
                    + "\n6- Round Robb"
                    + "\n7- Imprime conteudo Inicial dos processos"
                    + "\n8- Retribui valores iniciais"
                    + "\n0- Sair" );

            algoritimo =  teclado.nextInt();

            if (algoritimo == 1) { //FCFS
                FCFS(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada);
            }
            else if (algoritimo == 2) { //SJF PREEMPTIVO
                SJF(true, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada);
            }
            else if (algoritimo == 3) { //SJF NÃO PREEMPTIVO
                SJF(false, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada);

            }
            else if (algoritimo == 4) { //PRIORIDADE NAO PREEMPTIVO
                PRIORIDADE(false, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
            }
            else if (algoritimo == 5) { //PRIORIDADE PREEMPTIVO
                PRIORIDADE(true, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
            }
            else if (algoritimo == 6) { //ROUND ROBIN
                RR(tempo_execucao, tempo_espera, tempo_restante);
            }
            else if (algoritimo == 7) { //IMPRIME CONTEÚDO INICIAL DOS PROCESSOS
                imprime_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
            }
            else if (algoritimo == 8) { //REATRIBUI VALORES INICIAIS
                popular_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
                imprime_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
            }
            else if (algoritimo == 0) {
                break;
            }
        }
    }

    public static void popular_processos(int[] tempo_execucao, int[] tempo_espera, int[] tempo_restante, int[] tempo_chegada, int[] prioridade){
        Random random = new Random();
        Scanner teclado = new Scanner (System.in);
        int aleatorio;

        System.out.println ( "---- Será aleatório: ----"
                        + "\n1- SIM"
                        + "\n2- NAO");
        System.out.println ( "-------------------------");
        aleatorio =  teclado.nextInt();

        for (int i = 0; i < n_processos; i++) {
            //Popular Processos Aleatorio
            if (aleatorio == 1){
                tempo_execucao[i] = random.nextInt(10)+1;
                tempo_chegada[i] = random.nextInt(10)+1;
                prioridade[i] = random.nextInt(10)+1;
            }
            //Popular Processos Manual
            else {
                System.out.println("Digite o tempo de execução do processo["+i+"]:  ");
                tempo_execucao[i] = teclado.nextInt();
                System.out.println("Digite o tempo de chegada do processo["+i+"]:  ");
                tempo_chegada[i] = teclado.nextInt();
                System.out.println("Digite a prioridade do processo["+i+"]:  ");
                prioridade[i] = teclado.nextInt();
            }
            tempo_restante[i] = tempo_execucao[i];
        }
    }

    public static void imprime_processos(int[] tempo_execucao, int[] tempo_espera, int[] tempo_restante, int[] tempo_chegada, int[] prioridade){
        //Imprime lista de processos
        for (int i = 0; i < n_processos; i++) {
            System.out.println("Process["+i+"]: tempo_execucao="+ tempo_execucao[i] + " tempo_restante="+tempo_restante[i] + " tempo_chegada=" + tempo_chegada[i] + " prioridade=" + prioridade[i]);
        }
    }
    public static void imprime_stats (int[] espera) {
        int[] tempo_espera = espera.clone();
        //Calcula e imprime estatisticas
        float tempo_total = 0;
        for (int i = 0; i < n_processos; i++) {
            System.out.println("Process["+i+"]: tempo_espera="+ tempo_espera[i]);
            tempo_total = tempo_espera[i] + tempo_total;
        }
        System.out.println("Tempo medio de espera: "+tempo_total/n_processos);
    }

    public static void FCFS(int[] execucao, int[] espera, int[] restante, int[] chegada){
        int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();
        int[] tempo_chegada = chegada.clone();

        int processo_em_execucao;
        processo_em_execucao = 0;
        for (int tempo = 1; tempo<= 1000; tempo++){
            try {
                if (tempo_restante[processo_em_execucao] == tempo_execucao[processo_em_execucao])
                    tempo_espera[processo_em_execucao] = tempo -1;

                if (tempo_restante[processo_em_execucao]!=1){

                    if (processo_em_execucao != (n_processos)) {
                        System.out.println("tempo["+tempo+"]: processo["+processo_em_execucao+"] restante="+(tempo_restante[processo_em_execucao]-1));
                        tempo_restante[processo_em_execucao]--;
                    }
                    else
                        break;
                }
                else {
                    System.out.println("tempo["+tempo+"]: processo["+processo_em_execucao+"] restante="+(tempo_restante[processo_em_execucao]-1));
                    tempo_restante[processo_em_execucao]--;
                    if ((processo_em_execucao+1) != (n_processos))
                        processo_em_execucao++;
                    else
                        break;
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        imprime_stats(tempo_espera);
    }

    public static void SJF(boolean preemptivo, int[] execucao, int[] espera, int[] restante, int[] chegada){
        int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();
        int[] tempo_chegada = chegada.clone();

        int menor_tempo_restante = MAXIMO_TEMPO_EXECUCAO;
        int processo_em_execucao = -1;
        int proc_terminados = 0;

        //linha do tempo
        for (int tempo = 1; tempo<= 1000; tempo++) {

            // escolha de qual processo irá executar
            // se preemptivo, sempre entrar no for, se não preemptivo, testa se tem algum processo em execução
            if ((preemptivo) || ((!preemptivo) && (processo_em_execucao == -1))) {
                //varre a lista de processos para ver qual processo já chegou e tem o menor tempo de execucao
                for (int proc=0; proc<n_processos; proc++) {
                    //se o processo ainda não começou sua execução (tempo_restante[proc] != 0) e o tempo de chegada for menor ou igual ao instante de tempo atual entra no IF para ver qual é o menor tempo de execução
                    if ((tempo_restante[proc] != 0) && (tempo_chegada[proc] <= tempo)) {
                        // testa para saber se o tempo de execução é menor do que o menor tempo já registrado
                        if (tempo_restante[proc] < menor_tempo_restante) {
                            menor_tempo_restante = tempo_restante[proc];
                            processo_em_execucao = proc;
                        }
                    }
                }
            }

            //se a saída do laço anterior resultador em processo_em_execução = -1, significa que nenhum processo está pronto
            if (processo_em_execucao == -1)
                System.out.println("tempo["+tempo+"]: nenhum processo está pronto");
                //neste caso algum processo foi escolhido e iniciará sua execução até o fim
            else {

                //registra o tempo de espera do processo escolhido (somente na primeira passada)
                if (tempo_restante[processo_em_execucao] == tempo_execucao[processo_em_execucao])
                    tempo_espera[processo_em_execucao] = tempo - tempo_chegada[processo_em_execucao];

                //decrementa o tempo restante de execução do processo
                tempo_restante[processo_em_execucao]--;

                //imprime em tela as informações do processo em execução
                System.out.println("tempo["+tempo+"]: processo["+processo_em_execucao+"] restante="+(tempo_restante[processo_em_execucao]));

                // se já executou todo o tempo necessário, então seta as variáveis de controle para os valores iniciais, assim forçará a entrar no laço de escolha de processo para executar
                if (tempo_restante[processo_em_execucao] == 0) {
                    processo_em_execucao = -1;
                    menor_tempo_restante = MAXIMO_TEMPO_EXECUCAO;
                    proc_terminados++;
                    //se o número de processos terminador for igual ao número de processos total, termina a aplicação
                    if (proc_terminados == n_processos)
                        break;
                }
            }
        }

        imprime_stats(tempo_espera);
    }

    //TODO
    public static void PRIORIDADE(boolean preemptivo, int[] execucao, int[] espera, int[] restante, int[] chegada, int[] prioridade){
        int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();
        int[] tempo_chegada = chegada.clone();
        int[] _prioridade = prioridade.clone();

        int maior_prioridade = 0;
        int menor_tempo_restante = MAXIMO_TEMPO_EXECUCAO;
        int processo_em_execucao = -1;
        int proc_terminados = 0;

        //linha do tempo
        for (int tempo = 1; tempo<= 1000; tempo++) {

            // escolha de qual processo irá executar
            // se preemptivo, sempre entrar no for, se não preemptivo, testa se tem algum processo em execução
            if ((preemptivo) || ((!preemptivo) && (processo_em_execucao == -1))) {
                //varre a lista de processos para ver qual processo já chegou e tem o menor tempo de execucao
                for (int proc=0; proc<n_processos; proc++) {
                    //se o processo ainda não começou sua execução (tempo_restante[proc] != 0) e o tempo de chegada for menor ou igual ao instante de tempo atual entra no IF para ver qual é o menor tempo de execução
                    if ((tempo_restante[proc] != 0) && (tempo_chegada[proc] <= tempo)) {
                        // testa para saber se o tempo de execução é menor do que o menor tempo já registrado
                        if (_prioridade[proc] > maior_prioridade) {
                            maior_prioridade = _prioridade[proc];
                            processo_em_execucao = proc;
                        }
                    }
                }
            }

            //se a saída do laço anterior resultador em processo_em_execução = -1, significa que nenhum processo está pronto
            if (processo_em_execucao == -1)
                System.out.println("tempo["+tempo+"]: nenhum processo está pronto");
                //neste caso algum processo foi escolhido e iniciará sua execução até o fim
            else {

                //registra o tempo de espera do processo escolhido (somente na primeira passada)
                if (tempo_restante[processo_em_execucao] == tempo_execucao[processo_em_execucao])
                    tempo_espera[processo_em_execucao] = tempo - tempo_chegada[processo_em_execucao];

                //decrementa o tempo restante de execução do processo
                tempo_restante[processo_em_execucao]--;

                //imprime em tela as informações do processo em execução
                System.out.println("tempo["+tempo+"]: processo["+processo_em_execucao+"] restante="+(tempo_restante[processo_em_execucao]));

                // se já executou todo o tempo necessário, então seta as variáveis de controle para os valores iniciais, assim forçará a entrar no laço de escolha de processo para executar
                if (tempo_restante[processo_em_execucao] == 0) {
                    processo_em_execucao = -1;
                    maior_prioridade = 0;
                    proc_terminados++;
                    //se o número de processos terminador for igual ao número de processos total, termina a aplicação
                    if (proc_terminados == n_processos)
                        break;
                }
            }
        }

        imprime_stats(tempo_espera);

    }

    //TODO
    public static void RR(int[] execucao, int[] espera, int[] restante){
        int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();

        int menor_tempo_restante = MAXIMO_TEMPO_EXECUCAO;
        int processo_em_execucao = 0;
        int proc_terminados = 0;
        int time_sliceCount = 1;
        Scanner teclado = new Scanner(System.in);

        System.out.println("Escolha o time slice: ");
        int time_slice =  teclado.nextInt();

        //linha do tempo
        for (int tempo = 1; tempo<= 1000; tempo++) {

            System.out.println("tempo["+tempo+"]: processo["+processo_em_execucao+"] restante="+(tempo_restante[processo_em_execucao]));
            tempo_restante[processo_em_execucao]--;

            if (tempo_restante[processo_em_execucao] == 0) {
                proc_terminados++;
                time_sliceCount = 1;
                if (proc_terminados == n_processos)
                    break;
                if (processo_em_execucao == (n_processos - 1)) {
                    processo_em_execucao = 0;
                    while (tempo_restante[processo_em_execucao] == 0) {
                        if (processo_em_execucao == (n_processos - 1))
                            break;
                        else
                            processo_em_execucao++;
                    }
                } else {
                    processo_em_execucao++;
                    while (tempo_restante[processo_em_execucao] == 0) {
                        if (processo_em_execucao == (n_processos - 1))
                            break;
                        else
                            processo_em_execucao++;
                    }
                }
            }
            else {
                if (time_sliceCount == time_slice) {
                    time_sliceCount = 1;
                    if (processo_em_execucao == (n_processos - 1)) {
                        processo_em_execucao = 0;
                        while (tempo_restante[processo_em_execucao] == 0) {
                            if (processo_em_execucao == (n_processos - 1))
                                break;
                            else
                                processo_em_execucao++;
                        }
                    } else {
                        if (proc_terminados != (n_processos-1)) {
                            processo_em_execucao++;
                            while (tempo_restante[processo_em_execucao] == 0) {
                                if (processo_em_execucao == (n_processos - 1))
                                    break;
                                else
                                    processo_em_execucao++;
                            }
                        }
                    }
                } else {
                    time_sliceCount++;
                }
            }
        }
    }
}

